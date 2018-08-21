package io.github.sinadarvi.syncmusic.nsd

import android.net.nsd.NsdManager
import android.net.nsd.NsdServiceInfo

internal class NsdListenerResolve(private val mNsdHelper: NsdHelper) : NsdManager.ResolveListener {

    private val ERROR_SOURCE = "android.net.nsd.NsdManager.ResolveListener"

    override fun onResolveFailed(serviceInfo: NsdServiceInfo, errorCode: Int) {
        mNsdHelper.logError("Failed to resolve service -> ${serviceInfo.serviceName}"
                , errorCode, ERROR_SOURCE)
    }

    override fun onServiceResolved(serviceInfo: NsdServiceInfo) {
        mNsdHelper.logMsg("Service resolved -> ${serviceInfo.serviceName}" +
                ", ${serviceInfo.host.hostName}/${serviceInfo.host.hostAddress}" +
                ", port ${serviceInfo.port}, ${serviceInfo.serviceType}")
        mNsdHelper.onNsdServiceResolved(serviceInfo)
    }
}