package murer.rudy.api.task

import android.content.Context
import murer.rudy.api.*


interface TaskFragmentModel {

    companion object {
        fun newInstance(activityContext: Context):
                TaskFragmentModel =
            TaskFragmentModelImpl(activityContext, RestApiService.instance)
    }

    // add functions here
    fun getBasicAuth(): String
    fun getTasks(basicAuth: String): List<Item>
    fun getURLInPreferences(): String

}
// add data class here

class TaskFragmentModelImpl(
    // add Managers here
    private val activityContext: Context,
    private val restApiService: RestApiService

) : TaskFragmentModel {

    override fun getBasicAuth(): String {
        val sharedPref = activityContext.getSharedPreferences("user", Context.MODE_PRIVATE)
        return sharedPref.getString("user", null) ?: ""
    }

    override fun getURLInPreferences(): String {
        val sharedPref = activityContext.getSharedPreferences("url", Context.MODE_PRIVATE)
        return sharedPref.getString("url", null) ?: ""
    }

    override fun getTasks(basicAuth: String): List<Item> {
        var listOfTasks = emptyList<Item>()
        val response = restApiService.getTasks(basicAuth).safeExecute()
        if (response.isSuccessful) {
            response.body()!!.let { it ->
                if (it.STATUS == "OK") {
                    listOfTasks = response.body()!!.todoItems.map {
                        Item(
                            type = ItemType.Task,
                            task = Task(
                                id = it.id,
                                avatar = it.avatar,
                                content = it.content,
                                createdOn = it.createdOn,
                                projectName = it.projectName

                            )
                        )
                    }
                }
            }
            return listOfTasks
        }
        return emptyList()
    }

}