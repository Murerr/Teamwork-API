package murer.rudy.api


import murer.rudy.api.authentication.LoginActivity.Companion.BASEURL
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

    @GET("authenticate.json") // authentification
    fun getUserInfo(@Header("Authorization") basicAuth: String): Call<UserData>

    @GET("projects.json") // authentification
    fun getProjectsInfo(@Header("Authorization") basicAuth: String): Call<ProjectRequest>

    @GET("latestActivity.json") // authentification
    fun getLatestActivities(@Header("Authorization") basicAuth: String): Call<ActivityRequest>

}

data class UserData(var STATUS: String, var account: AccountData)
data class AccountData(var firstname: String?, var URL: String, var lastname:String?, var userId:String?, var id:String?)

enum class ItemType { RecentActivity, Project, Task }
data class Item(val type: ItemType,
                var recentActivity: RecentActivity? = null, var project: Project? = null,  var Task: Task? = null)

data class ProjectRequest(var STATUS:String, var projects: List<Project>)
data class Project(var id: String, var name: String, var description: String, var logo:String, var starred:Boolean)

data class ActivityRequest(var STATUS:String,var activity: List<RecentActivity>)
data class RecentActivity(var id: String, var activitytype: String, var description: String, var extradescription:String, var type:String, var fromusername:String)

data class TaskRequest(var STATUS:String,var activity: List<RecentActivity>)
data class Task(var id: String, var activitytype: String, var description: String, var extradescription:String, var type:String, var fromusername:String)