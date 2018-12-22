package murer.rudy.api

import android.content.Context


interface ProjectFragmentModel {

    companion object {
        fun newInstance(activityContext: Context):
                HomeFragmentModelImpl = HomeFragmentModelImpl(activityContext, RestApiService.login)
    }

    // add functions here

}
// add data class here

class ProjectFragmentModelImpl(
    // add Managers here
    private val activityContext: Context,
    private val restApiService: RestApiService

) : ProjectFragmentModel {

}