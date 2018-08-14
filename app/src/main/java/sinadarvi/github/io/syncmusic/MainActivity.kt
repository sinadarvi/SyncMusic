package sinadarvi.github.io.syncmusic

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.transaction
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.main_fragment.*
import sinadarvi.github.io.syncmusic.ui.main.BottomNavigationDrawerFragment
import sinadarvi.github.io.syncmusic.ui.main.MainFragment

class MainActivity : AppCompatActivity(), BottomNavigationDrawerFragment.OnMenuItemClickListener {

    private lateinit var mainFragment: MainFragment

    override fun onItemSelected(itemId: Int) {
        mainFragment.onBottomSheetDialogItemSelect(itemId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        if (savedInstanceState == null) {
            mainFragment = MainFragment.newInstance()

            supportFragmentManager.transaction {
                replace(R.id.container,mainFragment,"MainFragment")
            }
        }
    }

}
