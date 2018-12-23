package murer.rudy.api

import android.content.Context
import murer.rudy.api.HomeFragment.Companion.BASICAUTH


interface ProjectFragmentModel {

    companion object {
        fun newInstance(activityContext: Context):
                ProjectFragmentModel = ProjectFragmentModelImpl(activityContext, RestApiService.instance)
    }

    // add functions here
    fun setBasicAuth()
    fun getBasicAuth():String
    fun getProjectInfos(basicAuth:String):List<ProjectsFields>

}
// add data class here

class ProjectFragmentModelImpl(
    // add Managers here
    private val activityContext: Context,
    private val restApiService: RestApiService

) : ProjectFragmentModel {

    override fun setBasicAuth() {
            val sharedPref = activityContext.getSharedPreferences("user", Context.MODE_PRIVATE)
            BASICAUTH = sharedPref.getString("url", null) ?:""
    }

    override fun getBasicAuth():String {
        val sharedPref = activityContext.getSharedPreferences("user", Context.MODE_PRIVATE)
        return sharedPref.getString("url", null) ?:""
    }

    override fun getProjectInfos(basicAuth: String):List<ProjectsFields> {
        var listOfProject = emptyList<ProjectsFields>()
        val response = restApiService.getProjectsInfo(basicAuth).safeExecute()
        if (response.isSuccessful) {
            response.body()!!.let { it ->
                if (it.STATUS == "OK") {
                    listOfProject = response.body()!!.projects.map {
                        ProjectsFields(
                            description = it.description,
                            id = it.id,
                            name = it.name,
                            logo = it.logo,
                            starred = it.starred

                        )
                    }
                }
            }
            return listOfProject
        }
        return emptyList()
    }

}