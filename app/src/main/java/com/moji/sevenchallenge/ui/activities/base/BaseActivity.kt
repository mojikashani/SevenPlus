package com.moji.sevenchallenge.ui.activities.base

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.moji.sevenchallenge.R
import com.moji.sevenchallenge.helpers.LoaderHelper
import com.moji.sevenchallenge.views.base.RequestView

abstract class BaseActivity : AppCompatActivity(), RequestView {

    private var isStartingActivity = false


    protected val handler: Handler by lazy{
        Handler(Looper.getMainLooper())
    }

    protected var loaderHelper: LoaderHelper? = null

    @Suppress("SameParameterValue")
    protected fun startActivity(intent: Intent, transitionType: TransitionType) {
        if (isStartingActivity) return
        isStartingActivity = true
        startActivity(intent)
        when (transitionType) {
            TransitionType.SLIDE -> overridePendingTransition(R.anim.activity_open_slide_in, R.anim.activity_open_slide_out)
            TransitionType.POP_UP -> overridePendingTransition(R.anim.activity_open_slide_up, R.anim.activity_open_slide_down)
            TransitionType.FADE -> overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out)
        }
    }


    protected fun startActivityForResult(intent: Intent, requestCode: Int, transitionType: TransitionType) {
        if (isStartingActivity) return
        isStartingActivity = true
        startActivityForResult(intent, requestCode)
        overridePendingTransition(R.anim.activity_open_slide_in, R.anim.activity_open_slide_out)
        when (transitionType) {
            TransitionType.SLIDE -> overridePendingTransition(R.anim.activity_open_slide_in, R.anim.activity_open_slide_out)
            TransitionType.POP_UP -> overridePendingTransition(R.anim.activity_open_slide_up, R.anim.activity_open_slide_down)
            TransitionType.FADE -> overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out)
        }
    }

    override fun onResume() {
        super.onResume()
        isStartingActivity = false
    }

    @Suppress("SameParameterValue")
    protected fun finish(transitionType: TransitionType) {
        finish()
        when (transitionType) {
            TransitionType.SLIDE -> overridePendingTransition(R.anim.activity_close_slide_in, R.anim.activity_close_slide_out)
            TransitionType.POP_UP -> overridePendingTransition(R.anim.activity_close_slide_up, R.anim.activity_close_slide_down)
            TransitionType.FADE -> overridePendingTransition(R.anim.activity_fade_out, R.anim.activity_fade_in)
        }
    }

    override fun onError(message: String?) {
        if (message != null) {
            showErrorSnackBar(message)
        }
    }

    override fun onNoNetworkError() {
        showErrorSnackBar(getString(R.string.error_no_internet_connection))
    }

    override fun onAuthorizationError(e: Throwable) {
        // navigate to login screen
    }

    protected open fun showSuccessSnackBar(message: String) {
        val snackBar = Snackbar
            .make(findViewById(android.R.id.content), message, Snackbar.LENGTH_INDEFINITE)
            .setDuration(8000)
            .setAction(getString(R.string.ok)) {
            }

        snackBar.setActionTextColor(ContextCompat.getColor(this, R.color.successGreenDark))

        val sbView = snackBar.view
        sbView.setBackgroundColor(ContextCompat.getColor(this, R.color.successGreen))
        val textView = sbView.findViewById(R.id.snackbar_text) as TextView
        textView.setTextColor(ContextCompat.getColor(this, R.color.white))
        textView.maxLines = 5
        snackBar.show()
    }

    protected open fun showErrorSnackBar(error: String) {
        showErrorSnackBar(error, false) {}
    }

    @Suppress("SameParameterValue")
    protected open fun showErrorSnackBar(error: String, hasTryAgain: Boolean, listener: () -> Unit) {
        val snackBar = Snackbar
            .make(findViewById(android.R.id.content), error, Snackbar.LENGTH_INDEFINITE)

        if (hasTryAgain) {
            snackBar.setAction(getString(R.string.try_again)) { listener() }
        } else {
            snackBar.duration = 8000
            snackBar.setAction(getString(R.string.ok)) { listener() }
        }
        snackBar.setActionTextColor(ContextCompat.getColor(this, R.color.DarkRed))

        val sbView = snackBar.view
        sbView.setBackgroundColor(ContextCompat.getColor(this, R.color.Red))
        val textView = sbView.findViewById(R.id.snackbar_text) as TextView
        textView.setTextColor(ContextCompat.getColor(this, R.color.white))
        textView.maxLines = 5
        snackBar.show()
    }

    override fun showLoading(message: String) {
        loaderHelper?.showLoader()
    }

    override fun hideLoading() {
        loaderHelper?.hideLoader()
    }

}
enum class TransitionType{SLIDE, POP_UP, FADE}
