package sinadarvi.github.io.syncmusic.ui.equaliser

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import sinadarvi.github.io.syncmusic.R

class EqualiserFragment : Fragment() {


    companion object {
        fun newInstance() = EqualiserFragment()
    }

    private lateinit var viewModel: EqualiserViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.main_fragment, container, false)

        return view
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
