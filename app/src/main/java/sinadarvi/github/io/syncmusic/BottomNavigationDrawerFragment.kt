package sinadarvi.github.io.syncmusic

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.bottom_nav_fragment.view.*

class BottomNavigationDrawerFragment : BottomSheetDialogFragment() {

    private lateinit var navItemListener: OnNavItemClickListener

    interface OnNavItemClickListener{
        fun onNavItemSelected(itemId: Int)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.bottom_nav_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.close_nav.setOnClickListener { dismiss() }
        view.nav_view.setNavigationItemSelectedListener {
            navItemListener.onNavItemSelected(it.itemId)
            dismiss()
            true
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        try {
            navItemListener = context as OnNavItemClickListener
        }catch (e: ClassCastException){
            throw ClassCastException(context.toString() + " must implement OnImageClickListener")
        }
    }

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog = BottomSheetDialog(requireContext(), theme)
}