package models

import com.google.gson.annotations.SerializedName

data class MarkLocation(
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("longitude")
    val longitude: Double,
    @SerializedName("titulo")
    val markTitle: String? = null,
    @SerializedName("tipo")
    val locationType: LocationType,
    @SerializedName("id")
    val id: Long,
    @SerializedName("mostrarQrCode")
    var showQrCode: Boolean = false,
    @SerializedName("snippet")
    val snippet: String? = null
)

enum class LocationType(type: String) {
    @SerializedName("RECEPTIVO")
    RECEPTIVO("RECEPTIVO"),

    @SerializedName("FEIRA_CURSOS")
    FEIRA_CURSOS("FEIRA_CURSOS"),

    @SerializedName("INSTITUTOS")
    INSTITUTOS("INSTITUTOS"),

    @SerializedName("AUDITORIOS")
    AUDITORIOS("AUDITORIOS"),

    @SerializedName("CANTINA")
    CANTINA("CANTINACANTINA")

}
