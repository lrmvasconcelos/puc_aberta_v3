package interactor

import repoInterfaces.ScoreBoardRepository

class GetUserScoreInteractor(private val repository: ScoreBoardRepository) {
    operator fun invoke() = repository.getLevelInt()
}