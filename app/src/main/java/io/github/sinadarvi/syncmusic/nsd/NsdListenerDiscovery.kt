package io.github.sinadarvi.syncmusic.nsd

import android.net.nsd.NsdServiceInfo

internal class NsdListenerDiscovery(private val mNsdHelper: NsdHelper) : android.net.nsd.NsdManager.DiscoveryListener {

    private val ERROR_SOURCE = "android.net.nsd.NsdHelper.DiscoveryListener"

    override fun onStartDiscoveryFailed(serviceType: String, errorCode: Int) {
        mNsdHelper.logError("Starting service discovery failed!", errorCode, ERROR_SOURCE)
        mNsdHelper.stopDiscovery()
    }

    override fun onStopDiscoveryFailed(serviceType: String, errorCode: Int) {
        mNsdHelper.logError("Stopping service discovery failed!", errorCode, ERROR_SOURCE)
        mNsdHelper.stopDiscovery()
    }

    override fun onDiscoveryStarted(serviceType: String) {
        mNsdHelper.logMsg("Service discovery started.")
    }

    override fun onDiscoveryStopped(serviceType: String) {
        mNsdHelper.logMsg("Service discovery stopped.")
    }

    override fun onServiceFound(serviceInfo: NsdServiceInfo) {
        if (serviceInfo.serviceType == mNsdHelper.discoveryServiceType) {
            if (serviceInfo.serviceName != mNsdHelper.registeredServiceInfo.serviceName) {
                if (mNsdHelper.discoveryServiceName == null || serviceInfo.serviceName.toLowerCase() == mNsdHelper.discoveryServiceName?.toLowerCase()) {
                    mNsdHelper.logMsg("Service found -> ${serviceInfo.serviceName}")
                    mNsdHelper.onNsdServiceFound(serviceInfo)
                }
            }
        }
    }

    override fun onServiceLost(serviceInfo: NsdServiceInfo) {
        mNsdHelper.logMsg("Service lost -> ${serviceInfo.serviceName}")
        mNsdHelper.onNsdServiceLost(serviceInfo)
    }
}