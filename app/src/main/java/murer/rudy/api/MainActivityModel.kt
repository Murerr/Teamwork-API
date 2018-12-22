package murer.rudy.api

interface MainActivityModel {


    companion object {
        fun newInstance(mainActivity: MainActivity): MainActivityModelImpl =
            MainActivityModelImpl(
                RestApiService.instance
            )

    }

    // method

}
data class item(val id: Int?, val name: String?)

class MainActivityModelImpl(
    private val restApiService: RestApiService

) : MainActivityModel {

}
