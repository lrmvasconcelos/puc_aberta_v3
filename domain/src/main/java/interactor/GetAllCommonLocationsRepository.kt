package interactor

import models.MarkLocation
import repoInterfaces.MapRepository

class GetAllCommonLocationsRepository(
    private val repository: MapRepository
) {
    operator fun invoke(): List<MarkLocation> = repository.getAllCommonLocations()
}