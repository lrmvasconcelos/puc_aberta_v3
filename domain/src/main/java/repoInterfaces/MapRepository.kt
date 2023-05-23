package repoInterfaces

import models.MarkLocation

interface MapRepository {
    fun getCommonLocations(): List<MarkLocation>
    fun getIceiLocations(): List<MarkLocation>

    fun getAllLocations(): List<MarkLocation>

}