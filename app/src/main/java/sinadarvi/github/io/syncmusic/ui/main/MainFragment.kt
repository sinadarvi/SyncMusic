package sinadarvi.github.io.syncmusic.ui.main

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import sinadarvi.github.io.syncmusic.R

class MainFragment : Fragment() {


    companion object {
        fun newInstance() = MainFragment()
    }

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
                state = false
            } else {
                bar.setNavigationIcon(R.drawable.ic_menu_white_24dp)
                bar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                bar.replaceMenu(R.menu.bottomappbar_menu_primary)
                fab.setImageResource(R.drawable.ic_audiotrack_white_24dp)
                state = true
            }
        }
        return view
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
        when (itemId) {
            R.id.server -> {
                bar.navigationIcon = null
                bar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
                bar.replaceMenu(R.menu.bottomappbar_menu_secondary)
                fab.setImageResource(R.drawable.ic_clear_white_24dp)
            }
            R.id.client -> {
                bar.navigationIcon = null
                bar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
                bar.replaceMenu(R.menu.bottomappbar_menu_secondary)
                fab.setImageResource(R.drawable.ic_clear_white_24dp)
            }
        }
    }

}
