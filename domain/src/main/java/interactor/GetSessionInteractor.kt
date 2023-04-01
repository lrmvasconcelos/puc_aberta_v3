package interactor

import repoInterfaces.SessionRepository

class GetSessionInteractor(private val repository: SessionRepository) {
    operator fun invoke() = repository.isLogged()
}