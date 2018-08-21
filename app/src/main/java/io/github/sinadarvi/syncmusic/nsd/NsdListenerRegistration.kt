package io.github.sinadarvi.syncmusic.nsd

import android.net.nsd.NsdManager
import android.net.nsd.NsdServiceInfo

class NsdListenerRegistration(private val mNsdHelper: NsdHelper) : NsdManager.RegistrationListener {

    private val ERROR_SOURCE = "android.net.nsd.NsdManager.RegistrationListener"

    override fun onRegistrationFailed(serviceInfo: NsdServiceInfo, errorCode: Int) {
        mNsdHelper.logError("Service registration failed!", errorCode, ERROR_SOURCE)
    }

    override fun onUnregistrationFailed(serviceInfo: NsdServiceInfo, errorCode: Int) {
        mNsdHelper.logError("Service unregistration failed!", errorCode, ERROR_SOURCE)
    }

    override fun onServiceRegistered(serviceInfo: NsdServiceInfo) {
        mNsdHelper.logMsg("Registered -> ${serviceInfo.serviceName}")
        mNsdHelper.onRegistered(serviceInfo.serviceName)
    }

    override fun onServiceUnregistered(serviceInfo: NsdServiceInfo) {
        mNsdHelper.logMsg("Unregistered -> ${serviceInfo.serviceName}")
    }
}
