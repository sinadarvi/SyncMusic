package sinadarvi.github.io.syncmusic

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import androidx.lifecycle.*
import com.github.angads25.filepicker.controller.DialogSelectionListener
import com.github.angads25.filepicker.model.DialogConfigs
import com.github.angads25.filepicker.model.DialogProperties
import com.github.angads25.filepicker.view.FilePickerDialog
import java.io.File

class MainViewModel(lifecycleOwner: LifecycleOwner) : ViewModel(), LifecycleObserver {

    private var mediaPlayer: MediaPlayer? = null
    private val properties = DialogProperties()

    init {
        lifecycleOwner.lifecycle.addObserver(this)
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
                Log.e("SyncMusic", "media player is playing, stop before running new one")
                it.stop()
                it.release()
            }
        }
        mediaPlayer = MediaPlayer.create(context, Uri.parse(path))
        mediaPlayer?.isLooping = looping
        mediaPlayer?.start()
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
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.stop()
            }
            it.release()
        }
    }
}