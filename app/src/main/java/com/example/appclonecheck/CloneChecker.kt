package com.example.appclonecheck
import android.content.Context

class CloneChecker {
    fun isAppGenuine(context: Context): Boolean {
        val path = context.filesDir.path
        return when {
            context.packageName != myPackage() -> {
                false
            }
            path.contains(DUAL_APP_ID_999) -> {
                false
            }
            else -> {
                val count = getDotCount(path)
                count <= APP_PACKAGE_DOT_COUNT
            }
        }
    }

    private fun getDotCount(path: String): Int {
        var count = 0
        for (element in path) {
            if (count > APP_PACKAGE_DOT_COUNT) {
                break
            }
            if (element == DOT) {
                count++
            }
        }
        return count
    }

    companion object {
        // for clone detection
        private const val APP_PACKAGE_DOT_COUNT = 2 // number of dots present in package name
        private const val DUAL_APP_ID_999 = "999"
        private const val PACKAGE_NAME_START = "com"
        private const val PACKAGE_NAME_MIDDLE = "example"
        private const val PACKAGE_NAME_END = "app_clonecheck"
        private const val DOT = '.'

        fun myPackage(): String {
            return "$PACKAGE_NAME_START.$PACKAGE_NAME_MIDDLE.${PACKAGE_NAME_END.replace("_", "")}"
        }
    }
}