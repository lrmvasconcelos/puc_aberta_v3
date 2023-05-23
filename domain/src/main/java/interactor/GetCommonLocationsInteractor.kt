package interactor

import models.MarkLocation
import repoInterfaces.MapRepository

class GetCommonLocationsInteractor(
    private val repository: MapRepository
) {
    operator fun invoke(): List<MarkLocation> = repository.getCommonLocations()
}