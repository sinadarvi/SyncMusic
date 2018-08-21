package io.github.sinadarvi.syncmusic.nsd

interface NsdListener {
    /**
     * Called when service has been successfully registered
     *
     * @param registeredService Registered service.
     */
    fun onNsdRegistered(registeredService: NsdService)

    /**
     * Called when discovery has been stopped or finished due to timeout.
     */
    fun onNsdDiscoveryFinished()

    /**
     * Called when service has been discovered.
     *
     * @param foundService Discovered service (not resolved yet).
     */
    fun onNsdServiceFound(foundService: NsdService)

    /**
     * Called when service has been resolved.
     *
     * @param resolvedService Resolved service.
     */
    fun onNsdServiceResolved(resolvedService: NsdService)

    /**
     * Called when connection with service has been lost.
     *
     * @param lostService Lost service.
     */
    fun onNsdServiceLost(lostService: NsdService)

    /**
     * Called when error occurred during NSD process (registration, discover or resolve).
     *
     * @param errorMessage Readable error message.
     * @param errorCode    Error code.
     * @param errorSource  Class/listener which caused the error.
     */
    fun onNsdError(errorMessage: String, errorCode: Int, errorSource: String)
}
