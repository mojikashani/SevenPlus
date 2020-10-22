package com.moji.sevenchallenge.extensions

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Handler
import android.view.View


//*************** Context
val Context.isNetworkAvailable : Boolean
    get() {
        if(mockNetworkAvailabilityToFalse) return false
        val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

        return when {
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

var mockNetworkAvailabilityToFalse = false

//*************** View
fun View.showWithDelay(){
    if(visibility == View.VISIBLE) return
    alpha = 0f
    visibility = View.VISIBLE
    animate().alpha(1f).setStartDelay(500).setDuration(200).start()
}

fun View.hideWithDelay(handler: Handler, duration: Long = 200L){
    if(visibility == View.GONE) return
    animate().alpha(0f).setDuration(duration).start()
    handler.postDelayed(duration){
        visibility = View.GONE
    }
}

//*************** Handler
fun Handler.postDelayed(delayMillis: Long, task: () -> Unit){
    this.postDelayed({task()}
        , delayMillis)
}