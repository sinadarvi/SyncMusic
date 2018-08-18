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
import kotlinx.android.synthetic.main.main_activity.*
import io.github.sinadarvi.syncmusic.ui.equaliser.EqualiserFragment


class MainActivity : AppCompatActivity(), BottomMenuDrawerFragment.OnMenuItemClickListener
        ,BottomNavigationDrawerFragment.OnNavItemClickListener {

    private var currentFabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
    private var state = true
    private lateinit var viewModel: MainViewModel

    override fun onMenuItemSelected(itemId: Int) {
        state = false
        bottom_app_bar.navigationIcon = null
        fab.hide(addVisibilityChanged)
        when (itemId) {
            R.id.server -> {
            }
            R.id.client -> {
            }
        }
    }

    override fun onNavItemSelected(itemId: Int) {
        Toast.makeText(this,"This item still unavailable right now" +
                ", will be enable in next update",Toast.LENGTH_LONG).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setSupportActionBar(bottom_app_bar)

        viewModel = ViewModelProviders.of(this)[MainViewModel::class.java]
        viewModel.addObserver(this)

        fab.setOnClickListener {
            if (state) {
                val bottomNavDrawerFragment = BottomMenuDrawerFragment()
                bottomNavDrawerFragment.show(supportFragmentManager, bottomNavDrawerFragment.tag)
            } else {
                state = true
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

    val addVisibilityChanged: FloatingActionButton.OnVisibilityChangedListener
            = object : FloatingActionButton.OnVisibilityChangedListener() {
        override fun onShown(fab: FloatingActionButton?) {
            super.onShown(fab)
        }

        override fun onHidden(fab: FloatingActionButton?) {
            super.onHidden(fab)
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
                viewModel.giveMeFilePicker(this, DialogSelectionListener {
                    Toast.makeText(this, "address: ${it[0]}", Toast.LENGTH_SHORT).show()
                    viewModel.startMusic(this,it[0],false)
                })
            }
            android.R.id.home -> {
                val bottomNavDrawerFragment = BottomNavigationDrawerFragment()
                bottomNavDrawerFragment.show(supportFragmentManager, bottomNavDrawerFragment.tag)
            }
            R.id.setting -> {
                viewModel.togglePlayingState()
            }
            else -> Log.e("AAAA", "itemID: ${item?.title}")

        }
        return true
    }

}
