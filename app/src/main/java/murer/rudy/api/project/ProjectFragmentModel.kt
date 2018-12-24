package murer.rudy.api.project

import android.content.Context
import murer.rudy.api.*


interface ProjectFragmentModel {

    companion object {
        fun newInstance(activityContext: Context):
                ProjectFragmentModel = ProjectFragmentModelImpl(
            activityContext,
            RestApiService.instance
        )
    }

    // add functions here
    fun getBasicAuth():String
    fun getProjectInfos(basicAuth:String): List<Item>
    fun getURLInPreferences(): String

}
// add data class here

class ProjectFragmentModelImpl(
    // add Managers here
    private val activityContext: Context,
    private val restApiService: RestApiService

) : ProjectFragmentModel {

    override fun getBasicAuth():String {
        val sharedPref = activityContext.getSharedPreferences("user", Context.MODE_PRIVATE)
        return sharedPref.getString("user", null) ?:""
    }
    override fun getURLInPreferences(): String {
        val sharedPref = activityContext.getSharedPreferences("url", Context.MODE_PRIVATE)
        return sharedPref.getString("url", null) ?:""
    }

    override fun getProjectInfos(basicAuth: String):List<Item> {
        var listOfProject = emptyList<Item>()
        val response = restApiService.getProjectsInfo(basicAuth).safeExecute()
        if (response.isSuccessful) {
            response.body()!!.let { it ->
                if (it.STATUS == "OK") {
                    listOfProject = response.body()!!.projects.map {
                        Item(
                            type = ItemType.Project,
                            project = Project(
                                description = it.description,
                                id = it.id,
                                name = it.name,
                                logo = it.logo,
                                starred = it.starred
                            )
                        )


                    }
                }
            }
            return listOfProject
        }
        return emptyList()
    }

}