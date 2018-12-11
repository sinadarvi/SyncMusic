package io.github.sinadarvi.syncmusic

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_menu_fragment.*

class BottomMenuDrawerFragment : BottomSheetDialogFragment() {

    private lateinit var context: FragmentActivity



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.bottom_menu_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        menu_view.setNavigationItemSelectedListener {
            (activity as MainActivity).onMenuItemSelected(it.itemId)
            dismiss()
            true
        }
        close_menu.setOnClickListener { dismiss() }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        this.context = context as FragmentActivity

        try {
        } catch (e: ClassCastException) {
            throw ClassCastException(context.toString() + " must implement OnImageClickListener")
        }
    }

    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
        (activity as MainActivity).onMenuDismissed()
    }

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog = BottomSheetDialog(requireContext(), theme)
}