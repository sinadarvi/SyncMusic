package io.github.sinadarvi.nsd

import android.os.CountDownTimer

internal class DiscoveryTimer(private val mOnTimeoutListener: OnTimeoutListener, seconds: Long) {

    internal interface OnTimeoutListener {
        fun onNsdDiscoveryTimeout()
    }

    private var mTimer: CountDownTimer = createTimer(seconds)

    private fun createTimer(seconds: Long): CountDownTimer {
        return object : CountDownTimer(1000 * seconds, 1000 * seconds) {
            override fun onTick(millisUntilFinished: Long) {}

            override fun onFinish() {
                mOnTimeoutListener.onNsdDiscoveryTimeout()
            }
        }
    }

    fun start() {
        mTimer.start()
    }

    fun cancel() {
        mTimer.cancel()
    }

    fun reset() {
        mTimer.cancel()
        mTimer.start()
    }

    fun timeout(seconds: Long) {
        mTimer.cancel()
        mTimer = createTimer(seconds)
    }
}
