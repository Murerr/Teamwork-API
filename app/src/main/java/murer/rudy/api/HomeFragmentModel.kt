package murer.rudy.api

import android.content.Context


interface HomeFragmentModel {

    companion object {
        fun newInstance(activityContext: Context):
                HomeFragmentModelImpl = HomeFragmentModelImpl(activityContext,RestApiService.login)
    }

    // add functions here
    fun getUserData(basicAuth: String): AccountData?
    fun setUserDataInPrefences(basicAuth: String)

    fun setURLInPreferences(url: String)
    fun getURLInPreferences():String?



}
// add data class here

class HomeFragmentModelImpl(
    // add Managers here
    private val activityContext: Context,
    private val restApiService: RestApiService

) : HomeFragmentModel {
    override fun setUserDataInPrefences(basicAuth: String) {
        val sharedPref = activityContext.getSharedPreferences("user",Context.MODE_PRIVATE) ?: return
        sharedPref.edit().putString("user",basicAuth).apply()
    }

    override fun setURLInPreferences(url: String) {
        val sharedPref = activityContext.getSharedPreferences("url",Context.MODE_PRIVATE) ?: return
        sharedPref.edit().putString("url", url).apply()
    }

    override fun getURLInPreferences(): String? {
        val sharedPref = activityContext.getSharedPreferences("url", Context.MODE_PRIVATE)
        return sharedPref.getString("url", null)
    }

    override fun getUserData(basicAuth: String): AccountData? {
        val response = restApiService.getUserInfo(basicAuth).safeExecute()
        if (response.isSuccessful) {
            response.body()!!.let {
                val status = it.STATUS
                if (status == "OK") {
                    return it.account
                }
            }
        }
        return null
    }


}