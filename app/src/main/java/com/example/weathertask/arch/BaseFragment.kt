package com.example.weathertask.arch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.weathertask.R

@Suppress("UNCHECKED_CAST")
abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    private lateinit var viewBinding: ViewBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = constructViewBinding()
        (viewBinding as? VB)?.let { init(it) }
        return viewBinding.root
    }

    fun getViewBinding(): VB = viewBinding as VB

    fun navigate(fragment: Fragment, tag: String) {
        val ft = parentFragmentManager.beginTransaction()
        ft.replace(R.id.fragment_layout, fragment)
        ft.addToBackStack(tag)
        ft.commit()
    }

    fun showShortToast(
        @StringRes messageRes: Int? = null,
        message: String? = null
    ) {
        Toast.makeText(
            requireContext(),
            message ?: messageRes?.let { getString(it) },
            Toast.LENGTH_SHORT
        ).show()
    }

    abstract fun constructViewBinding(): VB
    abstract fun init(viewBinding: VB)
}