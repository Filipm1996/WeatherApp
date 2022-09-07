package com.example.weatherapp.features.polutiondetails

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.weatherapp.databinding.FragmentPolutionDetailsBinding
import com.example.weatherapp.domain.model.PolutionDetails

class PolutionDetailsFragment : DialogFragment() {
    private var polutionDetails: PolutionDetails? = null

    private lateinit var binding : FragmentPolutionDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpText()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    private fun setUpText() {
        binding.coTitle.text = "${HtmlCompat.fromHtml("NO<sub>2</sub>", HtmlCompat.FROM_HTML_MODE_LEGACY)} NO2 - maximum 1-hour concentration"
        binding.coValue.text = polutionDetails?.co.toString()
    }
    companion object {

    }

    fun updatePolutionDetails( polutionDetails: PolutionDetails){
        this.polutionDetails = polutionDetails
    }
}
