package com.map.matrimonytest.utils

import android.content.Context
import android.widget.Toast


class Helper {
    companion object {
        fun showToast(context: Context,message: String) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}