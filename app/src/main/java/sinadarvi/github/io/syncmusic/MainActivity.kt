package sinadarvi.github.io.syncmusic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.transaction
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.main_activity.*
import sinadarvi.github.io.syncmusic.ui.main.BottomNavigationDrawerFragment
import sinadarvi.github.io.syncmusic.ui.main.MainFragment

class MainActivity : AppCompatActivity(), BottomNavigationDrawerFragment.OnMenuItemClickListener {

    private lateinit var mainFragment: MainFragment

    private var currentFabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
    private var state = true

    override fun onItemSelected(itemId: Int) {
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setSupportActionBar(bottom_app_bar)
//        bottom_app_bar.replaceMenu(R.menu.bottomappbar_menu_primary)
        fab.setOnClickListener {
            if (state) {
                val bottomNavDrawerFragment = BottomNavigationDrawerFragment()
                bottomNavDrawerFragment.show(supportFragmentManager, bottomNavDrawerFragment.tag)
            } else {
                state = true
                fab.hide(addVisibilityChanged)
                bottom_app_bar.setNavigationIcon(R.drawable.ic_menu_white_24dp)
            }
        }


        if (savedInstanceState == null) {
            mainFragment = MainFragment.newInstance()

            supportFragmentManager.transaction {
                replace(R.id.container,mainFragment,"MainFragment")
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.bottomappbar_menu_primary,menu)
        return true
    }

    val addVisibilityChanged: FloatingActionButton.OnVisibilityChangedListener = object : FloatingActionButton.OnVisibilityChangedListener() {
        override fun onShown(fab: FloatingActionButton?) {
            super.onShown(fab)
        }

        override fun onHidden(fab: FloatingActionButton?) {
            super.onHidden(fab)
            bottom_app_bar.toggleFabAlignment()
            bottom_app_bar.replaceMenu(
                    if (currentFabAlignmentMode == BottomAppBar.FAB_ALIGNMENT_MODE_CENTER) R.menu.bottomappbar_menu_secondary
                    else R.menu.bottomappbar_menu_primary
            )
            fab?.setImageResource(
                    if (currentFabAlignmentMode == BottomAppBar.FAB_ALIGNMENT_MODE_CENTER) R.drawable.ic_clear_white_24dp
                    else R.drawable.ic_audiotrack_white_24dp
            )
            fab?.show()
        }
    }

    private fun BottomAppBar.toggleFabAlignment() {
        currentFabAlignmentMode = fabAlignmentMode
        fabAlignmentMode = currentFabAlignmentMode.xor(1)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        Log.e("AAAA","clicked")
        when (item?.itemId){
            R.id.play -> {

                Log.e("AAAA","clicked on ply")
                val snackbar = Snackbar.make(
                        snackbar_coordinatorLayout,
                        "FAB Clicked",
                        Snackbar.LENGTH_LONG
                ).setAction("UNDO") {  }
                // Changing message text color
                snackbar.setActionTextColor(ContextCompat.getColor(this, R.color.snackbar))

                val snackbarView = snackbar.view
                val params = snackbarView.layoutParams as CoordinatorLayout.LayoutParams

                params.setMargins(
                        params.leftMargin + 0,
                        params.topMargin,
                        params.rightMargin + 0,
                        params.bottomMargin + 350
                )

                snackbarView.layoutParams = params
                snackbar.show()
            }
        }
        return true
    }

}
