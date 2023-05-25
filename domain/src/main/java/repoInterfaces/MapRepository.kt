package repoInterfaces

import models.MarkLocation

interface MapRepository {
    fun getCommonLocations(): List<MarkLocation>
    fun updateCommonLocations(markers: List<MarkLocation>)
    fun getIceiLocations(): List<MarkLocation>
    fun updateIceiLocations(markers: List<MarkLocation>)
    fun getAllLocations(): List<MarkLocation>

}