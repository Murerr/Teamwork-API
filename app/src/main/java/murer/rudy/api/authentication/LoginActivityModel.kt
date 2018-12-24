package murer.rudy.api.authentication

import android.content.Context
import murer.rudy.api.AccountData
import murer.rudy.api.NetworkStatusManagerImpl
import murer.rudy.api.RestApiService
import murer.rudy.api.authentication.LoginActivityModelStatus.*
import murer.rudy.api.safeExecute

enum class LoginActivityModelStatus {
    OK,
    NO_NETWORK,
    FAILED,
    AUTH_FAILED,
}

data class LoginActivityModelResponse(
    val status: LoginActivityModelStatus,
    val errorMessage: String,
    var accountData: AccountData? = null
)

interface LoginActivityModel {
    companion object {
        fun newInstance(activityContext: Context):
                LoginActivityModelImpl =
            LoginActivityModelImpl(
                activityContext,
                RestApiService.login,
                NetworkStatusManagerImpl(activityContext)
            )
    }

    fun login(basicAuth: String): LoginActivityModelResponse
}

class LoginActivityModelImpl(
    private val activityContext: Context,
    private val restApiService: RestApiService,
    private val networkStatusManager: NetworkStatusManagerImpl

) : LoginActivityModel {
    override fun login(basicAuth: String): LoginActivityModelResponse {
        if (!networkStatusManager.hasInternet()) {
            return LoginActivityModelResponse(NO_NETWORK, "You must have an Internet connection to login")
        }
        val response = restApiService.getUserInfo(basicAuth).safeExecute()
        if (response.isSuccessful) {
            response.body()!!.let {
                val status = it.STATUS
                if (status == "OK") {
                    return LoginActivityModelResponse(OK, "Login Successful", it.account)
                }
                return LoginActivityModelResponse(AUTH_FAILED, "Login failed")
            }
        }
        return LoginActivityModelResponse(FAILED, "An Error happened while login-in")
    }
}