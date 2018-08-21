package io.github.sinadarvi.syncmusic.nsd

import android.net.nsd.NsdServiceInfo

internal class NsdListenerResolve(private val mNsdHelper: NsdHelper) : android.net.nsd.NsdManager.ResolveListener {

    private val ERROR_SOURCE = "android.net.nsd.NsdManager.ResolveListener"

    override fun onResolveFailed(serviceInfo: NsdServiceInfo, errorCode: Int) {
        mNsdHelper.logError(String.format("Failed to resolve service -> %s",
                serviceInfo.serviceName), errorCode, ERROR_SOURCE)
    }

    override fun onServiceResolved(serviceInfo: NsdServiceInfo) {
        mNsdHelper.logMsg(String.format("Service resolved -> %s, %s/%s, port %d, %s",
                serviceInfo.serviceName, serviceInfo.host.hostName,
                serviceInfo.host.hostAddress, serviceInfo.port, serviceInfo.serviceType))
        mNsdHelper.onNsdServiceResolved(serviceInfo)
    }
}