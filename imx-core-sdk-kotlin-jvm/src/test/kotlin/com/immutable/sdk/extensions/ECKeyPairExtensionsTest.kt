package com.immutable.sdk.extensions

import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.web3j.crypto.ECKeyPair
import java.math.BigInteger

class ECKeyPairExtensionsTest {
    @Test
    fun testGetUncompressedPublicKey() {
        val priKey = BigInteger("64921332213866445295402452406359669616940831814648692629855451985861889755924")
        val pubKey = BigInteger(
            "9901532862291060574053445876038647650509149806815782609589844942518406773458626646324" +
                "507388973112731900700287178564981928810919490275135830477941243800767"
        )
        val keyPair = ECKeyPair(priKey, pubKey)
        assertEquals(
            "0x04bd0daf39994ee40477d9ab5a29956d52f0869a329aede770bdd913a560d1086175d" +
                "0d39dc1150be883d0e80517801583dd6cbffea87c47f52a58dece873e6cbf",
            keyPair.getUncompressedPublicKey()
        )
    }
}
