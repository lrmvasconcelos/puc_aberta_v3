package interactor

import models.MarkLocation
import repoInterfaces.MapRepository

class GetIceiLocationInteractor(
    private val repository: MapRepository
) {
    operator fun invoke(): List<MarkLocation> = repository.getIceiLocations()
}