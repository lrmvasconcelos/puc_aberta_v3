package interactor

import repoInterfaces.SessionRepository

class SetSessionInteractor(private val repository: SessionRepository) {
    operator fun invoke() = repository.setLogged()
}