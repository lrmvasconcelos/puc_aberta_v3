package interactor

import models.MarkLocation
import repoInterfaces.MapRepository

class UpdateCommonLocationsInteractor(
    private val repository: MapRepository
) {
    operator fun invoke(marks: List<MarkLocation>) = repository.updateCommonLocations(marks)
}