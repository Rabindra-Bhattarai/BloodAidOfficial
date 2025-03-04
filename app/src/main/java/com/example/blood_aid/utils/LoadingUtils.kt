package com.example.blood_aid.utils

import android.app.Activity
import android.app.AlertDialog
import android.widget.TextView
import com.example.blood_aid.R

class LoadingUtils(val activity: Activity) {
    private var alertDialog: AlertDialog? = null

    fun show() {
        val builder = AlertDialog.Builder(activity)
        val dialogView = activity.layoutInflater.inflate(R.layout.loading, null)
        builder.setView(dialogView)
        builder.setCancelable(false)
        alertDialog = builder.create()
        alertDialog?.show()
    }

    fun dismiss() {
        alertDialog?.dismiss()
    }
}
