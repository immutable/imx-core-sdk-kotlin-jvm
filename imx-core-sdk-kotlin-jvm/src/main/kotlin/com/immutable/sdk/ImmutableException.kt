package com.immutable.sdk

/**
 * Exception thrown during SDK operations
 *
 * @property cause the exception being wrapped. If it came from a [java.util.concurrent.CompletableFuture]
 * you will need to get the cause again to find the root exception as it will be wrapped by a
 * [java.util.concurrent.CompletionException]
 * @property message categorises the type of exception
 * @property type categorises the type of exception
 */
class ImmutableException private constructor(
    val type: ImmutableExceptionType,
    message: String? = null,
    cause: Throwable? = null
) : Exception(message, cause) {
    companion object {
        /**
         * Exception for when a response is missing required data.
         *
         * @param callName used to identify the step of the workflow that failed e.g. Signable order
         * @param cause the root cause of the error so the consumer can further diagnose
         */
        internal fun invalidResponse(callName: String, cause: Throwable? = null) =
            ImmutableException(
                ImmutableExceptionType.InvalidResponse,
                "Invalid response: $callName",
                cause
            )

        /**
         * Exception for when a workflow fails due to an api call failing.
         *
         * @param callName used to identify the step of the workflow that failed e.g. Signable order
         * @param cause the root cause of the error so the consumer can further diagnose
         */
        internal fun apiError(callName: String, cause: Throwable? = null) =
            ImmutableException(ImmutableExceptionType.ApiError, "Api call failed: $callName", cause)

        /**
         * Exception for when the issue is due to SDK logic.
         *
         * @param message user-facing description of the error
         */
        internal fun clientError(message: String) =
            ImmutableException(ImmutableExceptionType.ClientError, "SDK error: $message")

        /**
         * Exception for when a request has been invalidated before sending.
         *
         * @param message user-facing description of the error
         */
        internal fun invalidRequest(message: String) =
            ImmutableException(ImmutableExceptionType.InvalidRequest, "Invalid request: $message")

        /**
         * Exception for when a workflow fails due to a smart contract transaction failing.
         *
         * @param callName used to identify the step of the workflow that failed e.g. Deposit ERC721
         * @param cause the root cause of the error so the consumer can further diagnose
         */
        internal fun contractError(callName: String, cause: Throwable? = null) =
            ImmutableException(
                ImmutableExceptionType.ContractError,
                "Contract transaction failed: $callName",
                cause
            )
    }
}

/**
 * Category of exception to provide more information and allow specific handling of each type
 */
enum class ImmutableExceptionType {
    /**
     * Exception for when a request has been invalidated before sending
     */
    InvalidRequest,

    /**
     * Exception for when a request has been invalidated before sending
     */
    InvalidResponse,

    /**
     * Exception for when a workflow fails due to an api call failing.
     */
    ApiError,

    /**
     * Exception for when the issue is due to SDK logic
     */
    ClientError,

    /**
     * Exception for when a workflow fails due to a smart contract transaction failing.
     */
    ContractError
}
