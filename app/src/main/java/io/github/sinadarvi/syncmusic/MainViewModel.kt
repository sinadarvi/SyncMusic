package io.github.sinadarvi.syncmusic

import ak.sh.ay.musicwave.MusicWave
import android.content.Context
import android.media.MediaPlayer
import android.media.audiofx.Visualizer
import android.net.Uri
import android.util.Log
import android.util.Log.e
import androidx.lifecycle.*
import com.github.angads25.filepicker.controller.DialogSelectionListener
import com.github.angads25.filepicker.model.DialogConfigs
import com.github.angads25.filepicker.model.DialogProperties
import com.github.angads25.filepicker.view.FilePickerDialog
import io.github.sinadarvi.syncmusic.nsd.NsdHelper
import java.io.File
import io.github.sinadarvi.syncmusic.R.id.musicWave



class MainViewModel : ViewModel(), LifecycleObserver {

    private var mediaPlayer: MediaPlayer? = null
    private var visualizer: Visualizer? = null
    private val properties = DialogProperties()
    lateinit var nsdHelper: NsdHelper
    var menuDrawerState = Drawer.Unlocked
    var navigationDrawerState = Drawer.Unlocked
    private var musicWave: MusicWave? = null


    fun addObserver(lifecycleOwner: LifecycleOwner){
        lifecycleOwner.lifecycle.addObserver(this)
    }

    fun addNsdHelper(nsdHelper: NsdHelper){
        this.nsdHelper = nsdHelper
    }

    fun takeThisMusicWave(musicWave: MusicWave){
        this.musicWave = musicWave
    }


    fun giveMeFilePicker(context: Context, callback: DialogSelectionListener) {
        properties.selection_mode = DialogConfigs.SINGLE_MODE
        properties.selection_type = DialogConfigs.FILE_SELECT
        properties.root = File("/mnt/sdcard")
        properties.error_dir = File(DialogConfigs.DEFAULT_DIR)
        properties.offset = File(DialogConfigs.DEFAULT_DIR)
        properties.extensions = null

        val dialog = FilePickerDialog(context, properties)
        dialog.setTitle("Select a File")

        dialog.setDialogSelectionListener(callback)
        //files is the array of the paths of files selected by the Application User.
        dialog.show()
    }

    fun startMusic(context: Context, path: String, looping: Boolean) {
        mediaPlayer?.let {
            if (it.isPlaying) {
                e("SyncMusic", "media player is playing, stop before running new one")
                it.stop()
//                it.release()
            }
        }
        mediaPlayer = MediaPlayer.create(context, Uri.parse(path))
        mediaPlayer?.isLooping = looping
        mediaPlayer?.start()

        //prepare visualizer
        visualizer = Visualizer((mediaPlayer as MediaPlayer).audioSessionId)
        visualizer?.captureSize = Visualizer.getCaptureSizeRange()[1]
        visualizer?.setDataCaptureListener(
                object : Visualizer.OnDataCaptureListener {
                    override fun onWaveFormDataCapture(visualizer: Visualizer,
                                                       bytes: ByteArray, samplingRate: Int) {
                        musicWave?.updateVisualizer(bytes)
                    }

                    override fun onFftDataCapture(visualizer: Visualizer,
                                                  bytes: ByteArray, samplingRate: Int) {
                    }
                }, Visualizer.getMaxCaptureRate() / 2, true, false)
        visualizer?.enabled = true
    }

    fun togglePlayingState(){
        mediaPlayer?.let {
            if(it.isPlaying)
                it.pause()
            else
                it.start()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreated() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStoped() {
        nsdHelper.stopDiscovery()
        nsdHelper.unregisterService()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(){
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.stop()
            }
//            it.release()
        }
    }
}