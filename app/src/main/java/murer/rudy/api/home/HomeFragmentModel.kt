package murer.rudy.api.home

import android.content.Context
import murer.rudy.api.*


interface HomeFragmentModel {

    companion object {
        fun newInstance(activityContext: Context):
                HomeFragmentModel =
            HomeFragmentModelImpl(activityContext, RestApiService.instance)
    }

    // add functions here
    fun getBasicAuth(): String

    fun getLatestActivities(basicAuth: String): List<Item>
    fun getURLInPreferences(): String

}
// add data class here

class HomeFragmentModelImpl(
    // add Managers here
    private val activityContext: Context,
    private val restApiService: RestApiService

) : HomeFragmentModel {

    override fun getBasicAuth(): String {
        val sharedPref = activityContext.getSharedPreferences("user", Context.MODE_PRIVATE)
        return sharedPref.getString("user", null) ?: ""
    }

    override fun getURLInPreferences(): String {
        val sharedPref = activityContext.getSharedPreferences("url", Context.MODE_PRIVATE)
        return sharedPref.getString("url", null) ?: ""
    }

    override fun getLatestActivities(basicAuth: String): List<Item> {
        var listOfActivities = emptyList<Item>()
        val response = restApiService.getLatestActivities(basicAuth).safeExecute()
        if (response.isSuccessful) {
            response.body()!!.let { it ->
                if (it.STATUS == "OK") {
                    listOfActivities = response.body()!!.activity.map {
                        Item(
                            type = ItemType.RecentActivity,
                            recentActivity = RecentActivity(
                                description = it.description,
                                id = it.id,
                                activitytype = it.activitytype,
                                fromusername = it.fromusername,
                                type = it.type,
                                datetime = it.datetime
                            )
                        )
                    }
                }
            }
            return listOfActivities
        }
        return emptyList()
    }

}