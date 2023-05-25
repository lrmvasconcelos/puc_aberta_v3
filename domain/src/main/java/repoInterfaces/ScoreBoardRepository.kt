package repoInterfaces

interface ScoreBoardRepository {

    fun saveNewMark(marksChecked: List<String>)

    fun getQrReaderTypes(): List<String>

}