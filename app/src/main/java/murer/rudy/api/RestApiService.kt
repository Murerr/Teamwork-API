package murer.rudy.api


import murer.rudy.api.HomeFragment.Companion.BASEURL
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header



interface RestApiService {
    companion object {
        val httpClient = OkHttpClient.Builder().build()
        val login: RestApiService by lazy {

            Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    //.baseUrl("https://api.teamwork.com/")
                    .baseUrl( "https://api.teamwork.com/")
                    .client(httpClient)
                    .build()
                    .create(RestApiService::class.java)
        }
        val instance: RestApiService by lazy {
            Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASEURL)
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

    @GET("projects.json") // authentification
    fun getProjectsInfo(@Header("Authorization") basicAuth: String): Call<Projects>

}

data class UserData(var STATUS: String, var account: AccountData)
data class AccountData(var firstname: String?, var URL: String, var lastname:String?, var userId:String?, var id:String?)

data class Projects(var STATUS:String,var projects: List<ProjectsFields>)
data class ProjectsFields(var id: String, var name: String, var description: String, var logo:String)
