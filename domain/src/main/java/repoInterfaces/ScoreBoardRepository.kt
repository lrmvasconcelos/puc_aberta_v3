package repoInterfaces

interface ScoreBoardRepository {

    fun saveLevel(level: Int)

    fun getLevelInt(): Int

}