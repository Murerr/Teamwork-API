package murer.rudy.api

import android.content.Context


interface HomeFragmentModel {

    companion object {
        fun newInstance(activityContext: Context):
                HomeFragmentModelImpl = HomeFragmentModelImpl(activityContext,RestApiService.instance)
    }

    // add functions here
    fun getUserData(basicAuth:String):AccountData?
}
// add data class here

class HomeFragmentModelImpl(
    // add Managers here
    private val activityContext: Context,
    private val restApiService: RestApiService

) : HomeFragmentModel {

    override fun getUserData(basicAuth: String): AccountData? {
        val response = restApiService.getUserInfo(basicAuth).safeExecute()
        if (response.isSuccessful) {
            response.body()!!.let {
                val status = it.STATUS
                if (status == "OK") {return it.account}
            }
        }
        return null
    }


}