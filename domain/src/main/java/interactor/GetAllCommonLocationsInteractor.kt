package interactor

import models.MarkLocation
import repoInterfaces.MapRepository

class GetAllCommonLocationsInteractor(
    private val repository: MapRepository
) {
    operator fun invoke(): List<MarkLocation> = repository.getAllCommonLocations()
}