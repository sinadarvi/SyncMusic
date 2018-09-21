package io.github.sinadarvi.syncmusic

import ak.sh.ay.musicwave.MusicWave
import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.e
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.transaction
import androidx.lifecycle.ViewModelProviders
import com.github.angads25.filepicker.controller.DialogSelectionListener
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.github.sinadarvi.nsd.NsdListener
import io.github.sinadarvi.nsd.NsdType
import kotlinx.android.synthetic.main.main_activity.*
import io.github.sinadarvi.syncmusic.ui.equaliser.EqualiserFragment


class MainActivity : AppCompatActivity(), NsdListener {


    private var currentFabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
    private val MY_PERMISSIONS_REQUEST_RECORD_AUDIO = 9976
    //set fabState to showMenuDrawer at runTime
    private var fabState = FabState.ShowMenuDrawer
    private var state = State.Nothing
    private lateinit var viewModel: MainViewModel
    private lateinit var savedInstanceState: Bundle


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setSupportActionBar(bottom_app_bar)
        savedInstanceState?.let { this.savedInstanceState = it }

        viewModel = ViewModelProviders.of(this)[MainViewModel::class.java]
        viewModel.addObserver(this)

        viewModel.addNsdHelper(io.github.sinadarvi.nsd.NsdHelper(this, this))
        viewModel.nsdHelper.isLogEnabled = true


        fab.setOnClickListener {
            //in this stage we look that what should we show to use, in what state are we
            if (fabState == FabState.ShowMenuDrawer) {
                val bottomNavDrawerFragment = BottomMenuDrawerFragment()
                if (viewModel.menuDrawerState == Drawer.Unlocked) {
                    viewModel.menuDrawerState = Drawer.Locked
                    bottomNavDrawerFragment.show(supportFragmentManager, bottomNavDrawerFragment.tag)
                }
            } else if (fabState == FabState.Close) {
                //change fabState so next time show menu drawer
                fabState = FabState.ShowMenuDrawer
                //change ui to prepare for next step
                fab.hide(addVisibilityChanged)
                bottom_app_bar.setNavigationIcon(R.drawable.ic_menu_white_24dp)

                //check what state we are so we can decide what should we do
                if (state == State.Client)
                    viewModel.nsdHelper.stopDiscovery()
                else if (state == State.Server)
                    viewModel.nsdHelper.unregisterService()
                state = State.Nothing
            }
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO), MY_PERMISSIONS_REQUEST_RECORD_AUDIO)
        } else {
            if (savedInstanceState == null) {
                supportFragmentManager.transaction {
                    replace(R.id.container, EqualiserFragment.newInstance(), "EqualiserFragment")
                }
            }
        }

    }


    //region menu item click listener
    fun onMenuItemSelected(itemId: Int){
        //we are now in MenuDrawer so next time should we pick 'Close'
        fabState = FabState.Close
        bottom_app_bar.navigationIcon = null
        fab.hide(addVisibilityChanged)
        when (itemId) {
            R.id.server -> {
                viewModel.nsdHelper.registerService("SyncMusic", NsdType.HTTP)
                state = State.Server
            }
            R.id.client -> {
                viewModel.nsdHelper.startDiscovery(NsdType.HTTP)
                state = State.Client
            }
        }
    }
    //endregion

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_RECORD_AUDIO -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] === PackageManager.PERMISSION_GRANTED) {
                    supportFragmentManager.transaction {
                        replace(R.id.container, EqualiserFragment.newInstance(), "EqualiserFragment")
                    }
                } else {
                    Toast.makeText(this@MainActivity, "Allow Permission from settings", Toast.LENGTH_LONG).show()
                }
                return
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.bottomappbar_menu_primary, menu)
        return true
    }

    //region fab customization
    private val addVisibilityChanged: FloatingActionButton.OnVisibilityChangedListener = object : FloatingActionButton.OnVisibilityChangedListener() {

        override fun onHidden(fab: FloatingActionButton?) {
            super.onHidden(fab)
            //select what fab should be look like each time
            bottom_app_bar.toggleFabAlignment()
            bottom_app_bar.replaceMenu(
                    if (currentFabAlignmentMode == BottomAppBar.FAB_ALIGNMENT_MODE_CENTER)
                        R.menu.bottomappbar_menu_secondary
                    else
                        R.menu.bottomappbar_menu_primary
            )
            fab?.setImageResource(
                    if (currentFabAlignmentMode == BottomAppBar.FAB_ALIGNMENT_MODE_CENTER)
                        R.drawable.ic_clear_white_24dp
                    else
                        R.drawable.ic_audiotrack_white_24dp
            )
            fab?.show()
        }
    }


    private fun BottomAppBar.toggleFabAlignment() {
        currentFabAlignmentMode = fabAlignmentMode
        fabAlignmentMode = currentFabAlignmentMode.xor(1)
    }
    //endregion

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.play -> {
                //pick the file with file picker
                viewModel.giveMeFilePicker(this, DialogSelectionListener {
                    Toast.makeText(this, "address: ${it[0]}", Toast.LENGTH_SHORT).show()
                    //TODO: watch out that the file is music file!

                    //start a music file
                    viewModel.startMusic(this, it[0], false)
                })
            }
            android.R.id.home -> {
                val bottomNavDrawerFragment = BottomNavigationDrawerFragment()
                //if drawer opening, don't open it again
                if (viewModel.navigationDrawerState == Drawer.Unlocked) {
                    viewModel.navigationDrawerState = Drawer.Locked
                    bottomNavDrawerFragment.show(supportFragmentManager, bottomNavDrawerFragment.tag)
                }
            }
            R.id.setting -> {
                viewModel.togglePlayingState()
            }
            else -> e("AAAA", "itemID: ${item?.title}")

        }
        return true
    }

    //region NSD Interfaces
    override fun onNsdRegistered(registeredService: io.github.sinadarvi.nsd.NsdService) {
        Toast.makeText(this, "device registered", Toast.LENGTH_SHORT).show()
    }

    override fun onNsdDiscoveryFinished() {
        Toast.makeText(this, "discovery finished", Toast.LENGTH_SHORT).show()
    }

    override fun onNsdServiceFound(foundService: io.github.sinadarvi.nsd.NsdService) {
        Toast.makeText(this, "service found", Toast.LENGTH_SHORT).show()
    }

    override fun onNsdServiceResolved(resolvedService: io.github.sinadarvi.nsd.NsdService) {
        e("AAAAA service resolved", resolvedService.hostIp + " : " + resolvedService.host
                + " : " + resolvedService.hostName)
        Toast.makeText(this, "ip: ${resolvedService.hostIp}", Toast.LENGTH_SHORT).show()
    }

    override fun onNsdServiceLost(lostService: io.github.sinadarvi.nsd.NsdService) {
        Toast.makeText(this, "device lost", Toast.LENGTH_SHORT).show()
        //Todo : should get back from state of searching, we can make animation for searching before device showing up
    }

    override fun onNsdError(errorMessage: String, errorCode: Int, errorSource: String) {
        Toast.makeText(this, "error: $errorCode , errorMessage: $errorMessage", Toast.LENGTH_SHORT).show()
    }
    //endregion


    //region nav item click listener
    fun onNavItemSelected(itemId: Int){
        Toast.makeText(this, "This item still unavailable right now" +
                ", will be enable in next update", Toast.LENGTH_LONG).show()
    }
    //endregion

    //region attachMusicWave to view Model for equalizer fragment
    fun attachMusicWave(musicWave: MusicWave){
        viewModel.takeThisMusicWave(musicWave)
    }
    //endregion

    //region menu dismissed
    fun onMenuDismissed(){
        viewModel.menuDrawerState = Drawer.Unlocked
    }
    //endregion

    //region navigation dismissed
    fun onNavigationDismissed() {
        viewModel.navigationDrawerState = Drawer.Unlocked
    }
    //endregion

}
