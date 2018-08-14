package sinadarvi.github.io.syncmusic

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.bottom_menu_fragment.*

class BottomMenuDrawerFragment : BottomSheetDialogFragment() {

    private lateinit var context: FragmentActivity
    private lateinit var callback: OnMenuItemClickListener

    interface OnMenuItemClickListener {
        fun onItemSelected(itemId: Int)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.bottom_menu_fragment, container, false)
        view.findViewById<NavigationView>(R.id.menu_view).setNavigationItemSelectedListener {
            callback.onItemSelected(it.itemId)
            dismiss()
            true
        }
        view.findViewById<ImageView>(R.id.close_menu).setOnClickListener { dismiss() }
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

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog = BottomSheetDialog(requireContext(), theme)
}