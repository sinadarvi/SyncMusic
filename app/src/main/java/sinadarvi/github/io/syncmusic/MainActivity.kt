package sinadarvi.github.io.syncmusic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, mainFragment)
                    .commitNow()
        }
    }

}
