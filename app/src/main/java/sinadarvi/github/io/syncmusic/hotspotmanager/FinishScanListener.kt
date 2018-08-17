package sinadarvi.github.io.syncmusic.hotspotmanager

interface FinishScanListener {
    /**
     * Interface called when the scan method finishes. Network operations should not execute on UI thread
     * @param clients
     */
    fun onFinishScan(clients: ArrayList<ClientScanResult>)
}