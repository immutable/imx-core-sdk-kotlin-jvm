package com.immutable.sdk.workflows

import com.immutable.sdk.*
import com.immutable.sdk.api.EncodingApi
import com.immutable.sdk.api.UsersApi
import com.immutable.sdk.api.model.*
import com.immutable.sdk.contracts.Core_sol_Core
import com.immutable.sdk.contracts.Registration_sol_Registration
import com.immutable.sdk.extensions.hexToByteArray
import com.immutable.sdk.model.AssetModel
import com.immutable.sdk.model.EthAsset
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.openapitools.client.infrastructure.ClientException
import org.web3j.abi.datatypes.Function
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.RemoteFunctionCall
import org.web3j.protocol.core.Request
import org.web3j.protocol.core.Response
import org.web3j.protocol.core.methods.response.EthGetTransactionCount
import org.web3j.protocol.core.methods.response.EthSendTransaction
import org.web3j.protocol.core.methods.response.TransactionReceipt
import org.web3j.protocol.http.HttpService
import org.web3j.tx.TransactionManager
import java.io.IOException
import java.math.BigInteger
import java.util.concurrent.CompletableFuture

private const val ADDRESS = "0xa76e3eeb2f7143165618ab8feaabcd395b6faaaa"
private const val AMOUNT = "0.0001"
private const val ASSET_ID = "5461"
private const val ASSET_TYPE = "712"
private const val STARK_KEY = "0x06588251eea34f39848302f991b8bc7098e2bb5fd2eba120255f91e971a23485"
private val STARK_KEY_BIG_INT =
    BigInteger("2870259069112366000714388987675960825159149244184363600690019399428626068613")
private const val OPERATOR_SIGNATURE =
    "0x5a263fad6f17f23e7c7ea833d058f3656d3fe464baf13f6f5ccba9a2466ba2ce4c4a250231bcac" +
        "7beb165aec4c9b049b4ba40ad8dd287dc79b92b1ffcf20cdcf11"
private const val PAYLOAD_HASH = "registerPayloadHash"
private const val NODE_URL = "https://eth-goerli.g.alchemy.com/v2/apiKey"
private const val CORE_CONTRACT_ADDRESS = "0x4527BE8f31E2ebFbEF4fCADDb5a17447B27d2123"
private const val REGISTRATION_CONTRACT_ADDRESS = "0x6C21EC8DE44AE44D0992ec3e2d9f1aBb6207Dabc"
private const val ENCODED_FUNCTION_CALL = "000111000554abc00000000a123def000000"
private const val SIGNED_TRANSACTION = "0xSignedTransaction"
private const val TRANSACTION_HASH = "0x28ca2abed368dca3f304fbb2be6db1f56cdcb138cdd3a68f86dcbb40ddda7545"
private val NONCE = BigInteger.ONE

class WithdrawalWorkflowTest {
    @MockK
    private lateinit var usersApi: UsersApi

    @MockK
    private lateinit var encodingApi: EncodingApi

    @MockK
    private lateinit var signer: Signer

    @MockK
    private lateinit var web3j: Web3j

    @MockK
    private lateinit var coreContract: Core_sol_Core

    @MockK
    private lateinit var registrationContract: Registration_sol_Registration

    @MockK
    private lateinit var function: Function

    @MockK
    private lateinit var txRemoteFunctionCall: RemoteFunctionCall<TransactionReceipt>

    @MockK
    private lateinit var ethSendRequest: Request<Any, EthSendTransaction>

    @MockK
    private lateinit var ethSendTransaction: EthSendTransaction

    private lateinit var addressFuture: CompletableFuture<String>
    private lateinit var signedTransactionFuture: CompletableFuture<String>

    @Suppress("LongMethod")
    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        addressFuture = CompletableFuture<String>()
        every { signer.getAddress() } returns addressFuture
        signedTransactionFuture = CompletableFuture<String>()
        every { signer.signTransaction(any()) } returns signedTransactionFuture

        every { encodingApi.encodeAsset(any(), any()) } returns EncodeAssetResponse(ASSET_ID, ASSET_TYPE)
        every { usersApi.getUsers(any()) } returns GetUsersApiResponse(arrayListOf(STARK_KEY))
        every { usersApi.getSignableRegistration(any()) } returns GetSignableRegistrationResponse(
            OPERATOR_SIGNATURE, PAYLOAD_HASH
        )

        mockkObject(ImmutableConfig)
        every { ImmutableConfig.getRegistrationContractAddress(any()) } returns REGISTRATION_CONTRACT_ADDRESS
        every { ImmutableConfig.getCoreContractAddress(any()) } returns CORE_CONTRACT_ADDRESS

        mockkStatic(Web3j::class)
        every { Web3j.build(any<HttpService>()) } returns web3j
        every { web3j.ethSendRawTransaction(any()) } returns ethSendRequest
        every { ethSendRequest.send() } returns ethSendTransaction
        every { ethSendTransaction.transactionHash } returns TRANSACTION_HASH
        every { ethSendTransaction.error } returns null
        val transactionCountRequest = mockk<Request<Any, EthGetTransactionCount>>()
        every { web3j.ethGetTransactionCount(any(), any()) } returns transactionCountRequest
        val count = mockk<EthGetTransactionCount>()
        every { transactionCountRequest.sendAsync() } returns CompletableFuture.completedFuture(count)
        every { count.transactionCount } returns NONCE

        every { txRemoteFunctionCall.encodeFunctionCall() } returns ENCODED_FUNCTION_CALL

        mockkStatic(Registration_sol_Registration::class)
        every {
            Registration_sol_Registration.load(any(), any(), any<TransactionManager>(), any())
        } returns registrationContract
        every {
            registrationContract.registerAndWithdraw(any(), any(), any(), any())
        } returns txRemoteFunctionCall
        every { registrationContract.contractAddress } returns REGISTRATION_CONTRACT_ADDRESS

        mockkStatic(Core_sol_Core::class)
        every {
            Core_sol_Core.load(any(), any(), any<TransactionManager>(), any())
        } returns coreContract
        every { coreContract.withdraw(any(), any()) } returns txRemoteFunctionCall
        every { coreContract.contractAddress } returns CORE_CONTRACT_ADDRESS
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    private fun createCompleteWithdrawalFuture(token: AssetModel) = completeWithdrawal(
        ImmutableXBase.Sandbox,
        NODE_URL,
        token,
        signer,
        STARK_KEY,
        usersApi,
        encodingApi
    )

    private fun <T> remoteFunctionCall(value: T) = RemoteFunctionCall(function) { value }

    @Test
    fun testWithdrawEthSuccess_registerAndWithdraw_registeredOffChainUser() {
        every { registrationContract.isRegistered(any()) } throws Throwable(USER_UNREGISTERED)

        addressFuture.complete(ADDRESS)
        signedTransactionFuture.complete(SIGNED_TRANSACTION)

        val token = EthAsset(AMOUNT)
        testFuture(
            future = createCompleteWithdrawalFuture(token),
            expectedResult = TRANSACTION_HASH,
            expectedError = null
        )

        verify { signer.getAddress() }
        verify {
            encodingApi.encodeAsset(
                ENCODE_ASSET_TYPE,
                EncodeAssetRequest(
                    EncodeAssetRequestToken(type = EncodeAssetRequestToken.Type.eTH, data = null)
                )
            )
        }
        verify { usersApi.getUsers(ADDRESS) }
        verify { registrationContract.isRegistered(STARK_KEY_BIG_INT) }
        verify { usersApi.getSignableRegistration(GetSignableRegistrationRequest(ADDRESS, STARK_KEY)) }
        verify {
            registrationContract.registerAndWithdraw(
                ADDRESS,
                STARK_KEY_BIG_INT,
                OPERATOR_SIGNATURE.hexToByteArray(),
                ASSET_TYPE.toBigInteger()
            )
        }
        verify { signer.signTransaction(any()) }
        verify { web3j.ethSendRawTransaction(any()) }
    }

    @Test
    fun testWithdrawEthSuccess_registerAndWithdraw_NotRegisteredOffChain() {
        every { usersApi.getUsers(any()) } throws ClientException()

        addressFuture.complete(ADDRESS)
        signedTransactionFuture.complete(SIGNED_TRANSACTION)

        testFuture(
            future = createCompleteWithdrawalFuture(EthAsset(AMOUNT)),
            expectedResult = null,
            expectedError = ImmutableException.apiError("")
        )
    }

    @Test
    fun testWithdrawEthSuccess_withdraw() {
        every { registrationContract.isRegistered(any()) } returns remoteFunctionCall(true)

        addressFuture.complete(ADDRESS)
        signedTransactionFuture.complete(SIGNED_TRANSACTION)

        val token = EthAsset(AMOUNT)
        testFuture(
            future = createCompleteWithdrawalFuture(token),
            expectedResult = TRANSACTION_HASH,
            expectedError = null
        )

        verify {
            encodingApi.encodeAsset(
                ENCODE_ASSET_TYPE,
                EncodeAssetRequest(
                    EncodeAssetRequestToken(type = EncodeAssetRequestToken.Type.eTH, data = null)
                )
            )
        }
        verify { usersApi.getUsers(ADDRESS) }
        verify { registrationContract.isRegistered(STARK_KEY_BIG_INT) }
        verify(exactly = 0) { usersApi.getSignableRegistration(any()) }
        verify {
            coreContract.withdraw(
                STARK_KEY_BIG_INT,
                ASSET_TYPE.toBigInteger()
            )
        }
        verify { signer.signTransaction(any()) }
        verify { web3j.ethSendRawTransaction(any()) }
    }

    @Test
    fun testWithdraw_getAddressError() {
        addressFuture.completeExceptionally(TestException())

        testFuture(
            future = createCompleteWithdrawalFuture(EthAsset(AMOUNT)),
            expectedResult = null,
            expectedError = TestException()
        )
    }

    @Test
    fun testWithdraw_encodeAssetError() {
        every { encodingApi.encodeAsset(any(), any()) } throws ClientException()

        addressFuture.complete(ADDRESS)
        signedTransactionFuture.complete(SIGNED_TRANSACTION)

        testFuture(
            future = createCompleteWithdrawalFuture(EthAsset(AMOUNT)),
            expectedResult = null,
            expectedError = ImmutableException.apiError("")
        )
    }

    @Test
    fun testWithdraw_sendTransactionError() {
        every { registrationContract.isRegistered(any()) } returns remoteFunctionCall(true)
        every { ethSendRequest.send() } throws IOException()

        addressFuture.complete(ADDRESS)
        signedTransactionFuture.complete(SIGNED_TRANSACTION)

        testFuture(
            future = createCompleteWithdrawalFuture(EthAsset(AMOUNT)),
            expectedResult = null,
            expectedError = ImmutableException.contractError("")
        )
    }

    @Test
    fun testWithdraw_sendTransactionError2() {
        every { registrationContract.isRegistered(any()) } returns remoteFunctionCall(true)
        val error = mockk<Response.Error>()
        every { ethSendTransaction.error } returns error
        every { error.message } returns ""

        addressFuture.complete(ADDRESS)
        signedTransactionFuture.complete(SIGNED_TRANSACTION)

        testFuture(
            future = createCompleteWithdrawalFuture(EthAsset(AMOUNT)),
            expectedResult = null,
            expectedError = ImmutableException.contractError("")
        )
    }
}
