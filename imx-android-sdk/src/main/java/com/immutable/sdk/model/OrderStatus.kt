package com.immutable.sdk.model

/**
 * Enum representing the possible Order statuses. If providing to an api use the name value (e.g.
 * OrderStatus.Active.value)
 */
enum class OrderStatus(val value: String) {
    Active("active"),
    Filled("filled"),
    Cancelled("cancelled"),
    Expired("expired"),
    Inactive("inactive")
}
