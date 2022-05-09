package com.immutable.sdk

import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.util.*

private const val API_URL = "url"

class ImmutableXSdkTest {

    @MockK
    private lateinit var properties: Properties

    private lateinit var sdk: ImmutableXSdk

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        mockkObject(ImmutableConfig)
        every { ImmutableConfig.getPublicApiUrl(any()) } returns API_URL

        mockkStatic(System::class)
        every { System.getProperties() } returns properties
        every { properties.setProperty(any(), any()) } returns mockk()

        sdk = spyk(ImmutableXSdk)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun testInit() {
        sdk.setBase(ImmutableXBase.Ropsten)

        verify { ImmutableConfig.getPublicApiUrl(ImmutableXBase.Ropsten) }
        verify { properties.setProperty(KEY_BASE_URL, API_URL) }
    }
}
