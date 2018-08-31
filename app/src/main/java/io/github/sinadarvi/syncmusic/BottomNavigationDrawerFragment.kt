package io.github.sinadarvi.syncmusic

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_nav_fragment.*

class BottomNavigationDrawerFragment : BottomSheetDialogFragment() {



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.bottom_nav_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        close_nav.setOnClickListener { dismiss() }
        nav_view.setNavigationItemSelectedListener {
            (activity as MainActivity).onNavItemSelected(it.itemId)
            dismiss()
            true
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        try {
        }catch (e: ClassCastException){
            throw ClassCastException(context.toString() + " must implement OnImageClickListener")
        }
    }

    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
        (activity as MainActivity).onNavigationDismissed()
    }

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog = BottomSheetDialog(requireContext(), theme)
}