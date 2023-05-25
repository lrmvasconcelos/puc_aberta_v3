package interactor

import repoInterfaces.ScoreBoardRepository

class SetUserScoreInteractor(private val repository: ScoreBoardRepository) {
    operator fun invoke(marks: List<String>) = repository.saveNewMark(marks)
}