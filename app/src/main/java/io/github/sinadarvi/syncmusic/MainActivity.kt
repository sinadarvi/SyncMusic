package io.github.sinadarvi.syncmusic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.transaction
import androidx.lifecycle.ViewModelProviders
import com.github.angads25.filepicker.controller.DialogSelectionListener
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.github.sinadarvi.syncmusic.nsd.NsdHelper
import io.github.sinadarvi.syncmusic.nsd.NsdListener
import io.github.sinadarvi.syncmusic.nsd.NsdService
import io.github.sinadarvi.syncmusic.nsd.NsdType
import kotlinx.android.synthetic.main.main_activity.*
import io.github.sinadarvi.syncmusic.ui.equaliser.EqualiserFragment


class MainActivity : AppCompatActivity(), BottomMenuDrawerFragment.OnMenuItemClickListener
        , BottomNavigationDrawerFragment.OnNavItemClickListener
        , NsdListener , BottomMenuDrawerFragment.OnDrawerMenuDismissed
        , BottomNavigationDrawerFragment.OnDrawerNavigationDismissed{

    override fun onNavigationDismissed() {
        viewModel.navigationDrawerState = Drawer.Unlocked
    }

    private var currentFabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
    //set fabState to showMenuDrawer at runTime
    private var fabState = FabState.ShowMenuDrawer
    private lateinit var viewModel: MainViewModel

    override fun onMenuDismissed() {
        // interface that when a menu dismissed
        viewModel.menuDrawerState = Drawer.Unlocked
    }

    override fun onNsdRegistered(registeredService: NsdService?) {
        Toast.makeText(this,"device registered",Toast.LENGTH_SHORT).show()
    }

    override fun onNsdDiscoveryFinished() {
        Toast.makeText(this,"discovery finished",Toast.LENGTH_SHORT).show()
    }

    override fun onNsdServiceFound(foundService: NsdService?) {
        Toast.makeText(this,"service found",Toast.LENGTH_SHORT).show()
    }

    override fun onNsdServiceResolved(resolvedService: NsdService?) {
        Log.e("AAAAA service resolved", resolvedService?.hostIp + " : " + resolvedService?.host
                + " : " + resolvedService?.hostName)
        Toast.makeText(this,"ip: ${resolvedService?.hostIp }",Toast.LENGTH_SHORT).show()
    }

    override fun onNsdServiceLost(lostService: NsdService?) {
        Toast.makeText(this,"device lost",Toast.LENGTH_SHORT).show()
    }

    override fun onNsdError(errorMessage: String?, errorCode: Int, errorSource: String?) {
        Toast.makeText(this,"error: $errorCode , errorMessage: $errorMessage",Toast.LENGTH_SHORT).show()
    }

    override fun onMenuItemSelected(itemId: Int) {
        //we are now in MenuDrawer so next time should we pick 'Close'
        fabState = FabState.Close
        bottom_app_bar.navigationIcon = null
        fab.hide(addVisibilityChanged)
        when (itemId) {
            R.id.server -> {
                viewModel.nsdHelper.registerService("SyncMusic", NsdType.HTTP)
            }
            R.id.client -> {
                viewModel.nsdHelper.startDiscovery(NsdType.HTTP)
            }
        }
    }

    override fun onNavItemSelected(itemId: Int) {
        Toast.makeText(this, "This item still unavailable right now" +
                ", will be enable in next update", Toast.LENGTH_LONG).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setSupportActionBar(bottom_app_bar)

        viewModel = ViewModelProviders.of(this)[MainViewModel::class.java]
        viewModel.addObserver(this)

        viewModel.addNsdHelper(NsdHelper(this,this))
        viewModel.nsdHelper.isLogEnabled =true


        fab.setOnClickListener {
            //in this stage we look that what should we show to use, in what state are we
            if (fabState == FabState.ShowMenuDrawer) {
                val bottomNavDrawerFragment = BottomMenuDrawerFragment()
                if (viewModel.menuDrawerState == Drawer.Unlocked) {
                    viewModel.menuDrawerState = Drawer.Locked
                    bottomNavDrawerFragment.show(supportFragmentManager, bottomNavDrawerFragment.tag)
                }
            } else if (fabState == FabState.Close){
                fabState = FabState.ShowMenuDrawer
                fab.hide(addVisibilityChanged)
                bottom_app_bar.setNavigationIcon(R.drawable.ic_menu_white_24dp)
            }
        }

        if (savedInstanceState == null) {
            supportFragmentManager.transaction {
                replace(R.id.container, EqualiserFragment.newInstance(), "EqualiserFragment")
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.bottomappbar_menu_primary, menu)
        return true
    }

    val addVisibilityChanged: FloatingActionButton.OnVisibilityChangedListener = object : FloatingActionButton.OnVisibilityChangedListener() {
        override fun onShown(fab: FloatingActionButton?) {
            super.onShown(fab)
        }

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
                if(viewModel.navigationDrawerState == Drawer.Unlocked) {
                    viewModel.navigationDrawerState = Drawer.Locked
                    bottomNavDrawerFragment.show(supportFragmentManager, bottomNavDrawerFragment.tag)
                }
            }
            R.id.setting -> {
                viewModel.togglePlayingState()
            }
            else -> Log.e("AAAA", "itemID: ${item?.title}")

        }
        return true
    }

}
