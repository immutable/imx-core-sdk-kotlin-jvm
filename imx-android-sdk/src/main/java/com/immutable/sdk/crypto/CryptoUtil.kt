package com.immutable.sdk.crypto

import com.immutable.sdk.utils.Constants.HEX_RADIX
import java.math.BigInteger

private const val REGISTER_USER = "1000"
private const val CANCEL_ORDER = "1003"
private val TWO_POW_22_BN = BigInteger("400000", 16)
private val TWO_POW_31_BN = BigInteger("80000000", 16)
private val TWO_POW_63_BN = BigInteger("8000000000000000", 16)

// Can be removed once API returns encoded and serialised message ready to be signed with the Stark keys
@Suppress("TooManyFunctions")
object CryptoUtil {
    fun getCancelOrderMsg(orderId: String): String {
        return Crypto.pedersenHash(arrayOf(packCancelOrderMsg(orderId)))
    }

    @Suppress("MagicNumber")
    private fun packCancelOrderMsg(orderId: String): String {
        var serialized = BigInteger(CANCEL_ORDER)
        serialized = serialized.shl(64).add(BigInteger(orderId))
        return sanitizeHex(serialized.toString(HEX_RADIX))
    }

    internal fun getRegisterUserMsgVerifyEth(
        etherKey: String,
        starkPublicKey: String,
    ): String {
        return Crypto.pedersenHash(
            arrayOf(
                Crypto.pedersenHash(arrayOf(starkPublicKey)),
                packRegisterUserMsgVerifyEth(etherKey),
            )
        )
    }

    @Suppress("MagicNumber")
    private fun packRegisterUserMsgVerifyEth(etherKey: String): String {
        var serialized = BigInteger(REGISTER_USER)
        serialized = serialized
            .shl(160)
            .add(BigInteger(etherKey))
        return sanitizeHex(serialized.toString(16))
    }

    private fun sanitizeHex(hexString: String): String {
        var hex = hexString.replaceFirst("0x", "")
        hex = hex.padStart(length = calcByteLength(hex.length, 2), '0')
        hex = "0x$hex"
        return hex
    }

    private fun calcByteLength(length: Int, byteSize: Int): Int {
        val remainder = length % byteSize
        return if (remainder != 0) ((length - remainder) / byteSize) * byteSize + byteSize else length
    }

    @Suppress("MagicNumber", "LongParameterList")
    fun getLimitOrderMsg(
        tokenSell: String,
        tokenBuy: String,
        vaultSell: String,
        vaultBuy: String,
        amountSell: String,
        amountBuy: String,
        nonce: String,
        expirationTimestamp: String,
    ): String {
        val w3 = formatMessage(
            instruction = "0",
            vaultSell = vaultSell,
            vaultBuy = vaultBuy,
            amountSell = amountSell,
            amountBuy = amountBuy,
            nonce = nonce,
            expirationTimestamp = expirationTimestamp
        )
        return Crypto.pedersenHash(arrayOf(Crypto.pedersenHash(arrayOf(tokenSell, tokenBuy)), w3))
    }

    @Suppress("MagicNumber")
    private fun serializeMessage(message: Message): String {
        with(message) {
            // we bit shift so the message is packed as below (without fees)
            //          instr vault0    vault1        amount0             amount1          nonce  timestamp
            //          +---+---------+---------+-------------------+-------------------+---------+-------+
            // #bits    | 4 |   31    |   31    |        63         |        63         |   31    |  22   |
            //          +---+---------+---------+-------------------+-------------------+---------+-------+
            // label      A      B         C             D                   E              F        G
            // see https://docs.starkware.co/starkex-v3/starkex-deep-dive/message-encodings/signatures
            var serialized = instructionTypeBn
            // shift left by the number of bit required in the message
            serialized = serialized.shl(31).add(vaultSellBn)
            serialized = serialized.shl(31).add(vaultBuyBn)
            serialized = serialized.shl(63).add(amountSellBn)
            serialized = serialized.shl(63).add(amountBuyBn)
            serialized = serialized.shl(31).add(nonceBn)
            serialized = serialized.shl(22).add(expirationTimestampBn)
            return (serialized.toString(HEX_RADIX))
        }
    }

    @Suppress("LongParameterList")
    private fun formatMessage(
        instruction: String,
        vaultSell: String,
        vaultBuy: String,
        amountSell: String,
        amountBuy: String,
        nonce: String,
        expirationTimestamp: String,
    ): String {
        val message = convertBnAndAssertRange(
            instruction,
            vaultSell,
            vaultBuy,
            amountSell,
            amountBuy,
            nonce,
            expirationTimestamp,
        )
        return serializeMessage(message)
    }

    private fun assertInRange(value: BigInteger, max: BigInteger) {
        assert(value >= BigInteger.ZERO && value <= max)
    }

    private data class Message(
        val instructionTypeBn: BigInteger,
        val vaultSellBn: BigInteger,
        val vaultBuyBn: BigInteger,
        val amountSellBn: BigInteger,
        val amountBuyBn: BigInteger,
        val nonceBn: BigInteger,
        val expirationTimestampBn: BigInteger,
        val feeVaultBn: BigInteger,
        val feeLimitBn: BigInteger
    )

    @Suppress("LongParameterList")
    private fun convertBnAndAssertRange(
        instruction: String,
        vault0: String,
        vault1: String,
        amount0: String,
        amount1: String,
        nonce: String,
        expirationTimestamp: String,
        feeVault: String = "0",
        feeLimit: String = "0",
    ): Message {
        val instructionTypeBn = BigInteger(instruction)
        // buy / sell vaults in orders or sender / receiver vaults in transfers
        val vault0Bn = BigInteger(vault0)
        val vault1Bn = BigInteger(vault1)
        val amount0Bn = BigInteger(amount0)
        val amount1Bn = BigInteger(amount1)
        val nonceBn = BigInteger(nonce)
        // in hours since the Unix epoch
        val expirationTimestampBn = BigInteger(expirationTimestamp)
        // fee information for orders / transfers with fees
        val feeVaultBn = BigInteger(feeVault)
        val feeLimitBn = BigInteger(feeLimit)

        // vaults and nonce are of size 31 bits
        assertInRange(vault0Bn, TWO_POW_31_BN)
        assertInRange(vault1Bn, TWO_POW_31_BN)
        assertInRange(feeVaultBn, TWO_POW_31_BN)
        assertInRange(nonceBn, TWO_POW_31_BN)

        // amounts and fee are of size 63 bits
        assertInRange(amount0Bn, TWO_POW_63_BN)
        assertInRange(amount1Bn, TWO_POW_63_BN)
        assertInRange(feeLimitBn, TWO_POW_63_BN)

        // expiration timestamp are of size 22 bits
        assertInRange(expirationTimestampBn, TWO_POW_22_BN)

        return Message(
            instructionTypeBn,
            vault0Bn,
            vault1Bn,
            amount0Bn,
            amount1Bn,
            nonceBn,
            expirationTimestampBn,
            feeVaultBn,
            feeLimitBn
        )
    }
}
