package models

data class MarkLocation(
    val latitude: Double,
    val longitude: Double,
    val markTitle: String? = null,
    val icon: Int,
    val id: Long,
    val showQrCode: Boolean = false,
    val snippet: String? = null
)
