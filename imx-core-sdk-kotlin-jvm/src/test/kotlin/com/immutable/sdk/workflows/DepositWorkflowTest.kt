package com.immutable.sdk.workflows

import com.immutable.sdk.*
import com.immutable.sdk.api.DepositsApi
import com.immutable.sdk.api.EncodingApi
import com.immutable.sdk.api.UsersApi
import com.immutable.sdk.api.model.*
import com.immutable.sdk.contracts.Core_sol_Core
import com.immutable.sdk.contracts.IERC20_sol_IERC20
import com.immutable.sdk.contracts.IERC721_sol_IERC721
import com.immutable.sdk.contracts.Registration_sol_Registration
import com.immutable.sdk.extensions.hexToByteArray
import com.immutable.sdk.model.*
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.openapitools.client.infrastructure.ClientException
import org.web3j.abi.datatypes.Bool
import org.web3j.abi.datatypes.Function
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.RemoteFunctionCall
import org.web3j.protocol.core.Request
import org.web3j.protocol.core.methods.response.EthGetTransactionCount
import org.web3j.protocol.core.methods.response.EthSendTransaction
import org.web3j.protocol.core.methods.response.TransactionReceipt
import org.web3j.protocol.http.HttpService
import org.web3j.tx.TransactionManager
import org.web3j.tx.gas.DefaultGasProvider
import java.io.IOException
import java.math.BigInteger
import java.util.concurrent.CompletableFuture

private const val ADDRESS = "0xa76e3eeb2f7143165618ab8feaabcd395b6faaaa"
private const val AMOUNT = "0.0001"
private const val AMOUNT_WEI = "100000000000000"
private const val TOKEN_ADDRESS = "0x6ee5c0826ba5523c9f0eee40da69befa30b3d912"
private const val TOKEN_ID = "1"
private const val DECIMAL = 6
private const val VAULT_ID = 987_654
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
private const val ERC20_CONTRACT_ADDRESS = "0x4527BE8f31E2ebFbEF4fCADDb5a17447B27d2000"
private const val ERC721_CONTRACT_ADDRESS = "0x6C21EC8DE44AE44D0992ec3e2d9f1aBb6207D111"
private const val ENCODED_FUNCTION_CALL = "000111000554abc00000000a123def000000"
private const val TRANSACTION_HASH = "0xTransactionHash"
private val NONCE = BigInteger.ONE

class DepositWorkflowTest {
    @MockK
    private lateinit var depositsApi: DepositsApi

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
    private lateinit var erc20Contract: IERC20_sol_IERC20

    @MockK
    private lateinit var erc721Contract: IERC721_sol_IERC721

    @MockK
    private lateinit var function: Function

    @MockK
    private lateinit var txRemoteFunctionCall: RemoteFunctionCall<TransactionReceipt>

    @MockK
    private lateinit var isApprovedRemoteFunctionCall: RemoteFunctionCall<Boolean>

    @MockK
    private lateinit var ethSendTransaction: EthSendTransaction

    @MockK
    private lateinit var gasProvider: DefaultGasProvider

    private lateinit var addressFuture: CompletableFuture<String>
    private lateinit var signedTransactionFuture: CompletableFuture<String>

    @Suppress("LongMethod")
    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        addressFuture = CompletableFuture<String>()
        every { signer.getAddress() } returns addressFuture
        signedTransactionFuture = CompletableFuture<String>()
        every { signer.sendTransaction(any()) } returns signedTransactionFuture

        every { depositsApi.getSignableDeposit(any()) } returns GetSignableDepositResponse(
            amount = AMOUNT,
            assetId = ASSET_ID,
            nonce = 596_252_354,
            starkKey = STARK_KEY,
            vaultId = VAULT_ID
        )
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
        every { ethSendTransaction.transactionHash } returns TRANSACTION_HASH
        every { ethSendTransaction.error } returns null
        val transactionCountRequest = mockk<Request<Any, EthGetTransactionCount>>()
        every { web3j.ethGetTransactionCount(any(), any()) } returns transactionCountRequest
        val count = mockk<EthGetTransactionCount>()
        every { transactionCountRequest.sendAsync() } returns CompletableFuture.completedFuture(count)
        every { count.transactionCount } returns NONCE

        every { txRemoteFunctionCall.encodeFunctionCall() } returns ENCODED_FUNCTION_CALL
        every { isApprovedRemoteFunctionCall.encodeFunctionCall() } returns ENCODED_FUNCTION_CALL

        mockkStatic(Registration_sol_Registration::class)
        every {
            Registration_sol_Registration.load(any(), any(), any<TransactionManager>(), any())
        } returns registrationContract
        every {
            registrationContract.registerAndDepositNft(any(), any(), any(), any(), any(), any())
        } returns txRemoteFunctionCall
        every { registrationContract.contractAddress } returns REGISTRATION_CONTRACT_ADDRESS

        mockkStatic(Core_sol_Core::class)
        every {
            Core_sol_Core.load(any(), any(), any<TransactionManager>(), any())
        } returns coreContract
        every {
            coreContract.registerAndDepositEth(any(), any(), any(), any(), any())
        } returns txRemoteFunctionCall
        every { coreContract.depositEth(any(), any(), any()) } returns txRemoteFunctionCall
        every {
            coreContract.registerAndDepositERC20(any(), any(), any(), any(), any(), any())
        } returns txRemoteFunctionCall
        every { coreContract.depositERC20(any(), any(), any(), any()) } returns txRemoteFunctionCall
        every { coreContract.depositNft(any(), any(), any(), any()) } returns txRemoteFunctionCall
        every { coreContract.registerUser(any(), any(), any()) } returns txRemoteFunctionCall
        every { coreContract.contractAddress } returns CORE_CONTRACT_ADDRESS

        mockkStatic(IERC20_sol_IERC20::class)
        every {
            IERC20_sol_IERC20.load(any(), any(), any<TransactionManager>(), any())
        } returns erc20Contract
        every { erc20Contract.approve(any(), any()) } returns txRemoteFunctionCall
        every { erc20Contract.contractAddress } returns ERC20_CONTRACT_ADDRESS

        mockkStatic(IERC721_sol_IERC721::class)
        every {
            IERC721_sol_IERC721.load(any(), any(), any<TransactionManager>(), any())
        } returns erc721Contract
        every { erc721Contract.isApprovedForAll(any(), any()) } returns isApprovedRemoteFunctionCall
        every { erc721Contract.setApprovalForAll(any(), any()) } returns txRemoteFunctionCall
        every { erc721Contract.contractAddress } returns ERC721_CONTRACT_ADDRESS

        every { gasProvider.gasPrice } returns DefaultGasProvider.GAS_PRICE
        every { gasProvider.getGasLimit(any()) } returns DefaultGasProvider.GAS_LIMIT
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    private fun createDepositFuture(token: AssetModel) = deposit(
        ImmutableXBase.Sandbox, NODE_URL, token, signer, depositsApi, usersApi, encodingApi, gasProvider
    )

    private fun <T> remoteFunctionCall(value: T) = RemoteFunctionCall(function) { value }

    @Test
    fun testDepositEthSuccess_registerAndDeposit_registeredOffChainUser() {
        every { registrationContract.isRegistered(any()) } throws Throwable(USER_UNREGISTERED)

        addressFuture.complete(ADDRESS)
        signedTransactionFuture.complete(TRANSACTION_HASH)

        val token = EthAsset(AMOUNT)
        testFuture(
            future = createDepositFuture(token),
            expectedResult = TRANSACTION_HASH,
            expectedError = null
        )

        verify {
            depositsApi.getSignableDeposit(
                GetSignableDepositRequest(AMOUNT_WEI, token.toSignableToken(), ADDRESS)
            )
        }
        verify {
            encodingApi.encodeAsset(
                EncodeAssetType.Asset.value,
                EncodeAssetRequest(
                    EncodeAssetRequestToken(type = EncodeAssetRequestToken.Type.eTH, data = null)
                )
            )
        }
        verify { usersApi.getUsers(ADDRESS) }
        verify { registrationContract.isRegistered(STARK_KEY_BIG_INT) }
        verify { usersApi.getSignableRegistration(GetSignableRegistrationRequest(ADDRESS, STARK_KEY)) }
        verify {
            coreContract.registerAndDepositEth(
                ADDRESS,
                STARK_KEY_BIG_INT,
                OPERATOR_SIGNATURE.hexToByteArray(),
                ASSET_TYPE.toBigInteger(),
                VAULT_ID.toBigInteger()
            )
        }
        verify { signer.sendTransaction(any()) }
    }

    @Test
    fun testDepositEthSuccess_registerAndDeposit_NotRegisteredOffChain() {
        every { usersApi.getUsers(any()) } returns GetUsersApiResponse(arrayListOf())

        addressFuture.complete(ADDRESS)
        signedTransactionFuture.complete(TRANSACTION_HASH)

        testFuture(
            future = createDepositFuture(EthAsset(AMOUNT)),
            expectedResult = null,
            expectedError = ImmutableException.apiError("")
        )
    }

    @Test
    fun testDepositEthSuccess_deposit() {
        every { registrationContract.isRegistered(any()) } returns remoteFunctionCall(true)

        addressFuture.complete(ADDRESS)
        signedTransactionFuture.complete(TRANSACTION_HASH)

        val token = EthAsset(AMOUNT)
        testFuture(
            future = createDepositFuture(token),
            expectedResult = TRANSACTION_HASH,
            expectedError = null
        )

        verify {
            depositsApi.getSignableDeposit(
                GetSignableDepositRequest(AMOUNT_WEI, token.toSignableToken(), ADDRESS)
            )
        }
        verify {
            encodingApi.encodeAsset(
                EncodeAssetType.Asset.value,
                EncodeAssetRequest(
                    EncodeAssetRequestToken(type = EncodeAssetRequestToken.Type.eTH, data = null)
                )
            )
        }
        verify { usersApi.getUsers(ADDRESS) }
        verify { registrationContract.isRegistered(STARK_KEY_BIG_INT) }
        verify(exactly = 0) { usersApi.getSignableRegistration(any()) }
        verify {
            coreContract.depositEth(
                STARK_KEY_BIG_INT,
                ASSET_TYPE.toBigInteger(),
                VAULT_ID.toBigInteger()
            )
        }
        verify { signer.sendTransaction(any()) }
    }

    @Test
    fun testDeposit_getAddressError() {
        addressFuture.completeExceptionally(TestException())

        testFuture(
            future = createDepositFuture(EthAsset(AMOUNT)),
            expectedResult = null,
            expectedError = TestException()
        )
    }

    @Test
    fun testDeposit_getSignableDepositError() {
        every { depositsApi.getSignableDeposit(any()) } throws ClientException()

        addressFuture.complete(ADDRESS)
        signedTransactionFuture.complete(TRANSACTION_HASH)

        testFuture(
            future = createDepositFuture(EthAsset(AMOUNT)),
            expectedResult = null,
            expectedError = ImmutableException.apiError("")
        )
    }

    @Test
    fun testDeposit_encodeAssetError() {
        every { encodingApi.encodeAsset(any(), any()) } throws ClientException()

        addressFuture.complete(ADDRESS)
        signedTransactionFuture.complete(TRANSACTION_HASH)

        testFuture(
            future = createDepositFuture(EthAsset(AMOUNT)),
            expectedResult = null,
            expectedError = ImmutableException.apiError("")
        )
    }

    @Test
    fun testDepositErc20Success_registerAndDeposit() {
        every { registrationContract.isRegistered(any()) } throws Throwable(USER_UNREGISTERED)

        addressFuture.complete(ADDRESS)
        signedTransactionFuture.complete(TRANSACTION_HASH)

        val token = Erc20Asset(TOKEN_ADDRESS, DECIMAL, AMOUNT)
        testFuture(
            future = createDepositFuture(token),
            expectedResult = TRANSACTION_HASH,
            expectedError = null
        )

        verify { erc20Contract.approve(any(), any()) }
        verify {
            depositsApi.getSignableDeposit(
                GetSignableDepositRequest(token.formatQuantity(), token.toSignableToken(), ADDRESS)
            )
        }
        verify {
            encodingApi.encodeAsset(
                EncodeAssetType.Asset.value,
                EncodeAssetRequest(
                    EncodeAssetRequestToken(
                        type = EncodeAssetRequestToken.Type.eRC20,
                        data = EncodeAssetTokenData(tokenAddress = TOKEN_ADDRESS)
                    )
                )
            )
        }
        verify { usersApi.getUsers(ADDRESS) }
        verify { registrationContract.isRegistered(STARK_KEY_BIG_INT) }
        verify {
            usersApi.getSignableRegistration(GetSignableRegistrationRequest(ADDRESS, STARK_KEY))
        }
        verify {
            coreContract.registerAndDepositERC20(
                ADDRESS,
                STARK_KEY_BIG_INT,
                OPERATOR_SIGNATURE.hexToByteArray(),
                ASSET_TYPE.toBigInteger(),
                VAULT_ID.toBigInteger(),
                token.formatQuantity().toBigInteger()
            )
        }
        verify { signer.sendTransaction(any()) }
    }

    @Test
    fun testDepositErc20Success_deposit() {
        every { registrationContract.isRegistered(any()) } returns remoteFunctionCall(true)

        addressFuture.complete(ADDRESS)
        signedTransactionFuture.complete(TRANSACTION_HASH)

        val token = Erc20Asset(TOKEN_ADDRESS, DECIMAL, AMOUNT)
        testFuture(
            future = createDepositFuture(token),
            expectedResult = TRANSACTION_HASH,
            expectedError = null
        )

        verify { erc20Contract.approve(any(), any()) }
        verify {
            depositsApi.getSignableDeposit(
                GetSignableDepositRequest(token.formatQuantity(), token.toSignableToken(), ADDRESS)
            )
        }
        verify {
            encodingApi.encodeAsset(
                EncodeAssetType.Asset.value,
                EncodeAssetRequest(
                    EncodeAssetRequestToken(
                        type = EncodeAssetRequestToken.Type.eRC20,
                        data = EncodeAssetTokenData(tokenAddress = TOKEN_ADDRESS)
                    )
                )
            )
        }
        verify { usersApi.getUsers(ADDRESS) }
        verify { registrationContract.isRegistered(STARK_KEY_BIG_INT) }
        verify(exactly = 0) { usersApi.getSignableRegistration(any()) }
        verify {
            coreContract.depositERC20(
                STARK_KEY_BIG_INT,
                ASSET_TYPE.toBigInteger(),
                VAULT_ID.toBigInteger(),
                token.formatQuantity().toBigInteger()
            )
        }
        verify { signer.sendTransaction(any()) }
    }

    @Test
    fun testDepositErc721Success_registerAndDeposit_notApproved() {
        every { registrationContract.isRegistered(any()) } throws Throwable(USER_UNREGISTERED)
        every { isApprovedRemoteFunctionCall.decodeFunctionResponse(any()) } returns listOf(Bool(false))

        addressFuture.complete(ADDRESS)
        signedTransactionFuture.complete(TRANSACTION_HASH)

        val token = Erc721Asset(TOKEN_ADDRESS, TOKEN_ID)
        testFuture(
            future = createDepositFuture(token),
            expectedResult = TRANSACTION_HASH,
            expectedError = null
        )

        verify {
            depositsApi.getSignableDeposit(
                GetSignableDepositRequest(token.formatQuantity(), token.toSignableToken(), ADDRESS)
            )
        }
        verify {
            encodingApi.encodeAsset(
                EncodeAssetType.Asset.value,
                EncodeAssetRequest(
                    EncodeAssetRequestToken(
                        type = EncodeAssetRequestToken.Type.eRC721,
                        data = EncodeAssetTokenData(tokenAddress = TOKEN_ADDRESS, tokenId = TOKEN_ID)
                    )
                )
            )
        }
        verify { usersApi.getUsers(ADDRESS) }
        verify { registrationContract.isRegistered(STARK_KEY_BIG_INT) }
        verify { erc721Contract.isApprovedForAll(ADDRESS, CORE_CONTRACT_ADDRESS) }
        verify { erc721Contract.setApprovalForAll(CORE_CONTRACT_ADDRESS, true) }
        verify {
            usersApi.getSignableRegistration(
                GetSignableRegistrationRequest(ADDRESS, STARK_KEY)
            )
        }
        verify {
            coreContract.registerUser(
                ADDRESS,
                STARK_KEY_BIG_INT,
                OPERATOR_SIGNATURE.hexToByteArray()
            )
        }
        verify {
            coreContract.depositNft(
                STARK_KEY_BIG_INT,
                ASSET_TYPE.toBigInteger(),
                VAULT_ID.toBigInteger(),
                TOKEN_ID.toBigInteger()
            )
        }
        verify { signer.sendTransaction(any()) }
    }

    @Test
    fun testDepositErc721Success_deposit_alreadyApproved() {
        every { registrationContract.isRegistered(any()) } returns remoteFunctionCall(true)
        every { isApprovedRemoteFunctionCall.decodeFunctionResponse(any()) } returns listOf(Bool(true))

        addressFuture.complete(ADDRESS)
        signedTransactionFuture.complete(TRANSACTION_HASH)

        val token = Erc721Asset(TOKEN_ADDRESS, TOKEN_ID)
        testFuture(
            future = createDepositFuture(token),
            expectedResult = TRANSACTION_HASH,
            expectedError = null
        )

        verify {
            depositsApi.getSignableDeposit(
                GetSignableDepositRequest(token.formatQuantity(), token.toSignableToken(), ADDRESS)
            )
        }
        verify {
            encodingApi.encodeAsset(
                EncodeAssetType.Asset.value,
                EncodeAssetRequest(
                    EncodeAssetRequestToken(
                        type = EncodeAssetRequestToken.Type.eRC721,
                        data = EncodeAssetTokenData(tokenAddress = TOKEN_ADDRESS, tokenId = TOKEN_ID)
                    )
                )
            )
        }
        verify { usersApi.getUsers(ADDRESS) }
        verify { registrationContract.isRegistered(STARK_KEY_BIG_INT) }
        verify { erc721Contract.isApprovedForAll(ADDRESS, CORE_CONTRACT_ADDRESS) }
        verify(exactly = 0) { erc721Contract.setApprovalForAll(CORE_CONTRACT_ADDRESS, true) }
        verify(exactly = 0) { usersApi.getSignableRegistration(any()) }
        verify(exactly = 0) {
            coreContract.registerUser(
                ADDRESS,
                STARK_KEY_BIG_INT,
                OPERATOR_SIGNATURE.hexToByteArray()
            )
        }
        verify {
            coreContract.depositNft(
                STARK_KEY_BIG_INT,
                ASSET_TYPE.toBigInteger(),
                VAULT_ID.toBigInteger(),
                TOKEN_ID.toBigInteger()
            )
        }
        verify { signer.sendTransaction(any()) }
    }

    @Test
    fun testDeposit_sendTransactionError() {
        every { registrationContract.isRegistered(any()) } returns remoteFunctionCall(true)

        addressFuture.complete(ADDRESS)
        signedTransactionFuture.completeExceptionally(IOException())

        val token = Erc721Asset(TOKEN_ADDRESS, TOKEN_ID)
        testFuture(
            future = createDepositFuture(token),
            expectedResult = null,
            expectedError = ImmutableException.contractError("")
        )
    }
}
