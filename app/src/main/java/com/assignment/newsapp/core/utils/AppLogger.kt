package com.assignment.newsapp.core.utils

import android.util.Log

class AppLogger {
    enum class LLevel {
        Info,
        Error
    }

    companion object {
        fun log(
            tag: String = "NewsApp",
            level: LLevel = LLevel.Info,
            msg: String
        ) {
            when (level) {
                LLevel.Info -> Log.i(
                    tag,
                    msg
                )

                LLevel.Error -> Log.e(
                    tag,
                    msg
                )
            }
        }
    }
}