package murer.rudy.api

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo


interface NetworkStatusManager {
    fun hasInternet(): Boolean
}

class NetworkStatusManagerImpl(val context: Context): NetworkStatusManager {
    override fun hasInternet(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnected == true
    }
}