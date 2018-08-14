package sinadarvi.github.io.syncmusic.ui.main

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.fragment.app.FragmentActivity
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.main_fragment.*
import sinadarvi.github.io.syncmusic.R
import sinadarvi.github.io.syncmusic.R.menu.bottomappbar_menu_primary
import sinadarvi.github.io.syncmusic.R.menu.bottomappbar_menu_secondary

class MainFragment : Fragment() {


    companion object {
        fun newInstance() = MainFragment()
    }

    private var currentFabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER

    private lateinit var viewModel: MainViewModel
    private lateinit var myContext: FragmentActivity
    private lateinit var bar: BottomAppBar
    private lateinit var fab: FloatingActionButton
    private var state = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.main_fragment, container, false)
        fab = view.findViewById(R.id.fab)
        bar = view.findViewById(R.id.bottom_app_bar)
        bar.replaceMenu(R.menu.bottomappbar_menu_primary)
        fab.setOnClickListener {
            if (state) {
                val bottomNavDrawerFragment = BottomNavigationDrawerFragment()
                bottomNavDrawerFragment.show(myContext.supportFragmentManager, bottomNavDrawerFragment.tag)
            } else {
                state = true
                fab.hide(addVisibilityChanged)
                bar.setNavigationIcon(R.drawable.ic_menu_white_24dp)
            }
        }

        return view
    }

    val addVisibilityChanged: FloatingActionButton.OnVisibilityChangedListener = object : FloatingActionButton.OnVisibilityChangedListener() {
        override fun onShown(fab: FloatingActionButton?) {
            super.onShown(fab)
        }

        override fun onHidden(fab: FloatingActionButton?) {
            super.onHidden(fab)
            bar.toggleFabAlignment()
            bottom_app_bar.replaceMenu(
                    if (currentFabAlignmentMode == BottomAppBar.FAB_ALIGNMENT_MODE_CENTER) bottomappbar_menu_secondary
                    else bottomappbar_menu_primary
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        myContext = context as FragmentActivity
    }

    fun onBottomSheetDialogItemSelect(itemId: Int) {
        state = false
        bar.navigationIcon = null
        fab.hide(addVisibilityChanged)
        when (itemId) {
            R.id.server -> {
            }
            R.id.client -> {
            }
        }
    }

}
