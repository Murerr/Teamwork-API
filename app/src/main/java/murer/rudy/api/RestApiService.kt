package murer.rudy.api


import android.net.wifi.hotspot2.pps.Credential
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import android.net.wifi.hotspot2.pps.Credential.UserCredential
import retrofit2.http.POST




interface RestApiService {
    companion object {
        val instance: RestApiService by lazy {
            val httpClient = OkHttpClient.Builder().build()

            Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    //.baseUrl("https://api.teamwork.com/")
                    .baseUrl( "https://authenticate.teamwork.com/")
                    .client(httpClient)
                    .build()
                    .create(RestApiService::class.java)
        }

    }

    /*@GET("authenticate.json") // authentification
    fun getUserInfo(Username: String, Password: String): Call<UserData>*/

    /*@GET("authenticate.json") // authentification
    fun getUserInfo(@Header("Authorization") basicAuth: String, @Body userCred: String): Call<UserData>*/

    //"Authorization", "Basic dHdwX3MwWmdHWGVYdlowQVVjVTFIRm5GNXVMWWRlQWU6NzQxN1J1ZHlN"

    @GET("authenticate.json") // authentification
    fun getUserInfo(@Header("Authorization") basicAuth: String): Call<UserData>

    /*@GET("authenticate.json") // authentification
    fun getUserInfo(@Header("Authorization") basicAuth: BasicAuth): Call<UserData>*/

}

data class BasicAuth(var username: String, var password: String)
data class UserData(var STATUS: String, var account: AccountData)
data class AccountData(var firstname: String?, var URL: String?, var lastname:String?, var userId:String?, var id:String?)
