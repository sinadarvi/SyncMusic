package sinadarvi.github.io.syncmusic.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.navigation.NavigationView
import sinadarvi.github.io.syncmusic.R

class BottomNavigationDrawerFragment : BottomSheetDialogFragment() {

    private lateinit var context: FragmentActivity
    private lateinit var callback: OnMenuItemClickListener

    interface OnMenuItemClickListener {
        fun onItemSelected(itemId: Int)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_bottomsheet, container, false)
        view.findViewById<NavigationView>(R.id.navigation_view).setNavigationItemSelectedListener {
            callback.onItemSelected(it.itemId)
            dismiss()
            true
        }
        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        this.context = context as FragmentActivity

        try {
            callback = context as OnMenuItemClickListener
        } catch (e: ClassCastException) {
            throw ClassCastException(context.toString() + " must implement OnImageClickListener")
        }
    }
}