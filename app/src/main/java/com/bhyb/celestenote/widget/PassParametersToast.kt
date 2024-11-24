package com.bhyb.celestenote.widget

import android.content.Context
import android.widget.Toast

object PassParametersToast {

    private var currentToast: Toast? = null

    fun show(context: Context, message: String, duration: Int = Toast.LENGTH_SHORT) {
        cancelCurrentToast()
        currentToast = Toast.makeText(context, message, duration).apply { show() }
    }

    fun show(context: Context, resId: Int, duration: Int = Toast.LENGTH_SHORT) {
        show(context, context.getString(resId), duration)
    }

    private fun cancelCurrentToast() {
        currentToast?.cancel()
    }
}