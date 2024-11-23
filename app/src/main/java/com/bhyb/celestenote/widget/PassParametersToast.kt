package com.bhyb.celestenote.widget

import android.content.Context
import android.widget.Toast

object PassParametersToast {

    fun show(context: Context, message: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(context, message, duration).show()
    }

    fun show(context: Context, resId: Int, duration: Int = Toast.LENGTH_SHORT) {
        show(context, context.getString(resId), duration)
    }
}