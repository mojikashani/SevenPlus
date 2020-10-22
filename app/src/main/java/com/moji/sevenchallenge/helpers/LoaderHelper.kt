package com.moji.sevenchallenge.helpers

import android.os.Handler
import android.view.View
import com.moji.sevenchallenge.extensions.hideWithDelay
import com.moji.sevenchallenge.extensions.postDelayed
import com.moji.sevenchallenge.extensions.showWithDelay

class LoaderHelper(private val handler: Handler, private val loader: View){
    private var counter = 0
    private var isLoaderBeingShown = false

    fun showLoader(){
        counter++
        if(counter == 1){
            isLoaderBeingShown = true
            loader.showWithDelay()
        }
    }

    fun hideLoader(){
        counter--
        if(counter == 0) {
            handler.postDelayed(200) {
                isLoaderBeingShown = false
                loader.hideWithDelay(handler)
            }
        }

    }
}