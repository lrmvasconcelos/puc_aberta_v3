package interactor

import models.MarkLocation
import repoInterfaces.MapRepository

class UpdateIceiLocationsInteractor(
    private val repository: MapRepository
) {
    operator fun invoke(marks: List<MarkLocation>) = repository.updateIceiLocations(marks)
}