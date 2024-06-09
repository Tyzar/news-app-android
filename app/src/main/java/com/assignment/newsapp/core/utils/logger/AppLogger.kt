package com.assignment.newsapp.core.utils.logger

import android.util.Log
import com.assignment.newsapp.BuildConfig

class AppLogger {
    enum class LLevel {
        Info,
        Error
    }

    companion object {
        fun log(
            tag: String = "NewsAppDebug",
            level: LLevel = LLevel.Info,
            msg: String
        ) {
            if (!BuildConfig.DEBUG) {
                return
            }

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