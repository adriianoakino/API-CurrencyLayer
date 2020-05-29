package com.adriianoakino.btg.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import com.adriianoakino.btg.R
import com.adriianoakino.btg.databinding.FragmentConversorBinding
import com.adriianoakino.btg.ui.viewmodels.ConversorViewModel
import com.adriianoakino.btg.utils.*
import kotlinx.android.synthetic.main.fragment_conversor.*
import org.koin.android.viewmodel.ext.android.sharedViewModel


class ConversorFragment : Fragment() {

    private lateinit var fragmentConversorBinding: FragmentConversorBinding

    private val viewModel: ConversorViewModel by sharedViewModel<ConversorViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentConversorBinding = FragmentConversorBinding.inflate(inflater, container, false)
        return fragmentConversorBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        fragmentConversorBinding.lifecycleOwner = this

        fragmentConversorBinding.buttonCoinOrigem.text = viewModel.getCurrencyOrigem().value ?: "Selecione uma opção"
        fragmentConversorBinding.buttonCoinDestination.text = viewModel.getCurrencyDestination().value ?: "Selecione uma opção"
        fragmentConversorBinding.textviewCoinOrigem.text = viewModel.getCurrencyOrigem().value
        fragmentConversorBinding.textviewCoinDestiny.text = viewModel.getCurrencyDestination().value

        fragmentConversorBinding.buttonCoinOrigem.setOnClickListener(){
            viewModel.setNavigationId(ID_ORIGEM)
            NavHostFragment.findNavController(this).navigate(R.id.action_conversorFragment_to_listCoinsFragment)
        }

        fragmentConversorBinding.buttonCoinDestination.setOnClickListener() {
            viewModel.setNavigationId(ID_DESTINATION)
            NavHostFragment.findNavController(this).navigate(R.id.action_conversorFragment_to_listCoinsFragment)
        }

        fragmentConversorBinding.edittextValorConverter.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                callConverter()
                true
            }
            false
        }

    }


    private fun converter(origem: String, destiny: String, ammount: String) {

        viewModel.converterPrice(origem, destiny, ammount).observe(viewLifecycleOwner, Observer { resource ->
            resource.data?.let {
                it?.quotes?.map { result ->  fragmentConversorBinding.textviewValue.text = result.value }
            }
            resource.error?.let { showError(it) }
        })
    }


    private fun callConverter() {
        if(textview_coin_destiny.text.isNotEmpty() && textview_coin_origem.text.isNotEmpty() && edittext_valor_converter.text.isNotEmpty()) {
            converter (
                fragmentConversorBinding.textviewCoinOrigem.text.toString(),
                fragmentConversorBinding.textviewCoinDestiny.text.toString(),
                fragmentConversorBinding.edittextValorConverter.text.toString()
            )
        }
    }

    object Utils {

        fun hideSoftKeyBoard(context: Context, view: View) {
            try {
                val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm?.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            } catch (e: Exception) {
                // TODO: handle exception
                e.printStackTrace()
            }

        }
    }

    override fun onStart() {
        super.onStart()
        if(!verifyNetwork()) showError(NO_NETWORK)
    }

    override fun onResume() {
        super.onResume()
        if(!verifyNetwork()) showError(NO_NETWORK)
    }



}