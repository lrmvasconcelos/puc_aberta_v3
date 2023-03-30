package repoInterfaces

import android.location.Location
import models.MarkLocation

interface MapRepository {
    fun getAllCommonLocations(): List<MarkLocation>
    fun getIceiLocations(): List<MarkLocation>

}