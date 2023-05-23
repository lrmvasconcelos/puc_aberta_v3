package interactor

import models.MarkLocation
import repoInterfaces.MapRepository

class GetAllLocationsInteractor(
    private val repository: MapRepository
) {
    operator fun invoke(): List<MarkLocation> = repository.getAllLocations()
}