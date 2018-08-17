package sinadarvi.github.io.syncmusic

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_menu_fragment.*
import kotlinx.android.synthetic.main.bottom_menu_fragment.view.*

class BottomMenuDrawerFragment : BottomSheetDialogFragment() {

    private lateinit var context: FragmentActivity
    private lateinit var callback: OnMenuItemClickListener

    interface OnMenuItemClickListener {
        fun onMenuItemSelected(itemId: Int)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.bottom_menu_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        menu_view.setNavigationItemSelectedListener {
            callback.onMenuItemSelected(it.itemId)
            dismiss()
            true
        }
        close_menu.setOnClickListener { dismiss() }
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