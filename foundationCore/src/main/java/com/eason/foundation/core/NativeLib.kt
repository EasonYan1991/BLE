package com.eason.foundation.core

class NativeLib {

    /**
     * A native method that is implemented by the 'core' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object {
        // Used to load the 'core' library on application startup.
        init {
            System.loadLibrary("core")
        }
    }
}