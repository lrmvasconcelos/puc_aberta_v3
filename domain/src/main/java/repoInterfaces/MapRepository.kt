package repoInterfaces

import models.MarkLocation

interface MapRepository {
    fun getAllCommonLocations(): List<MarkLocation>
    fun getIceiLocations(): List<MarkLocation>

}