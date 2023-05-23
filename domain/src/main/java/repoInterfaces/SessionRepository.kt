package repoInterfaces

interface SessionRepository {

    fun isLogged():Boolean

    fun setLogged()

    fun  validateLocationsAtCache()

}