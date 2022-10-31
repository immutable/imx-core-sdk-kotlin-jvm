package com.immutable.sdk.api.model

data class UnsignedMintRequest(
    /* minting contract */
    val contractAddress: String,
    /* Users to mint to */
    val users: List<MintUser>,
    /* Global contract-level royalty fees */
    val royalties: List<MintFee>? = null
)
