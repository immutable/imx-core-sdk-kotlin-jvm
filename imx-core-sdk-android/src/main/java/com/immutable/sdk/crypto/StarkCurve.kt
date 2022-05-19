package com.immutable.sdk.crypto

import com.immutable.sdk.utils.Constants
import org.bouncycastle.crypto.params.ECDomainParameters
import org.bouncycastle.crypto.params.ECPrivateKeyParameters
import org.bouncycastle.math.ec.ECCurve
import org.bouncycastle.math.ec.ECPoint
import org.web3j.crypto.ECKeyPair
import java.math.BigInteger

// Curve parameters
private val p = BigInteger(
    "800000000000011000000000000000000000000000000000000000000000001",
    Constants.HEX_RADIX
)
private val A = BigInteger.ONE
private val B = BigInteger(
    "06f21413efbe40de150e596d72f7a8c5609ad26c15c915c1f4cdfcb99cee9e89",
    Constants.HEX_RADIX
)
private val N = BigInteger(
    "0800000000000010ffffffffffffffffb781126dcae7b2321e66a241adc64d2f",
    Constants.HEX_RADIX
)
private val H = BigInteger.ONE
private val Gx =
    BigInteger("874739451078007766457464989774322083649278607533249481151382481072868806602")
private val Gy =
    BigInteger("152666792071518830868575557812948353041420400780739481342941381225525861407")

/**
 * The Stark-friendly elliptic curve
 *
 * @link https://docs.starkware.co/starkex-v3/crypto/stark-curve
 */
internal object StarkCurve {
    private val curve: ECCurve
    private val pointG: ECPoint

    init {
        val configure = ECCurve.Fp(p, A, B, N, H).configure()
        configure.setCoordinateSystem(ECCurve.COORD_AFFINE)
        curve = configure.create()
        pointG = curve.createPoint(Gx, Gy)
    }

    /**
     * @return public Key from given [privateKey]
     */
    private fun generatePublicKeyFromPrivateKey(privateKey: BigInteger?): BigInteger {
        return BigInteger(pointG.multiply(privateKey).getEncoded(true))
    }

    /**
     * @return ECPrivateKeyParameters with respect to G point
     */
    fun createPrivateKeyParams(privateKey: BigInteger?): ECPrivateKeyParameters {
        return ECPrivateKeyParameters(
            privateKey,
            ECDomainParameters(
                curve,
                curve.createPoint(pointG.xCoord.toBigInteger(), pointG.yCoord.toBigInteger()),
                N
            )
        )
    }

    fun getKeyPair(privateKey: String): ECKeyPair {
        val pubKey = generatePublicKeyFromPrivateKey(
            BigInteger(privateKey, Constants.HEX_RADIX)
        )
        return ECKeyPair(
            BigInteger(privateKey, Constants.HEX_RADIX),
            pubKey
        )
    }
}
