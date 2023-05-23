package interactor

import repoInterfaces.SessionRepository

class SaveUsersLocationInteractor(private val repository: SessionRepository) {

    operator fun invoke() = repository.validateLocationsAtCache()
}