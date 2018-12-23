package murer.rudy.api

interface MainActivityModel {

    companion object {
        fun newInstance(mainActivity: MainActivity): MainActivityModelImpl =
            MainActivityModelImpl(
            )

    }

}

class MainActivityModelImpl(

) : MainActivityModel {

}
