package interactor

import repoInterfaces.ScoreBoardRepository

class SetUserScoreInteractor(private val repository: ScoreBoardRepository) {
    operator fun invoke(level: Int) = repository.saveLevel(level)
}