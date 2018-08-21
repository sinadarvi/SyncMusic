package io.github.sinadarvi.syncmusic.ui.equaliser

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.sinadarvi.syncmusic.R

class EqualiserFragment : Fragment() {


    companion object {
        fun newInstance() = EqualiserFragment()
    }

    private lateinit var viewModel: EqualiserViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.equaliser_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(EqualiserViewModel::class.java)
        // TODO: Use the ViewModel
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

}
