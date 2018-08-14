package sinadarvi.github.io.syncmusic

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.bottom_nav_fragment.*

class BottomNavigationDrawerFragment: BottomSheetDialogFragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.bottom_nav_fragment,container,false)
        view.findViewById<ImageView>(R.id.close_nav).setOnClickListener { dismiss() }

        view.findViewById<NavigationView>(R.id.nav_view).setNavigationItemSelectedListener {
            dismiss()
            true
        }
        return view
    }

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog = BottomSheetDialog(requireContext(), theme)
}