package com.example.weatherapp.features.weatherdetails.dialog

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.DialogFragment
import com.example.weatherapp.databinding.FragmentPolutionDetailsBinding
import com.example.weatherapp.domain.model.PolutionDetails

class PolutionDetailsFragment : DialogFragment() {
    private var polutionDetails: PolutionDetails? = null

    private lateinit var binding: FragmentPolutionDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentPolutionDetailsBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    private fun setUpText() {
        binding.coTitle.text = Html.fromHtml("CO<sub>2</sub>", HtmlCompat.FROM_HTML_MODE_LEGACY)
        binding.coValue.text = polutionDetails?.co.toString()
        binding.noTitle.text = "NO"
        binding.noValue.text = polutionDetails?.no.toString()
        binding.no2Title.text = Html.fromHtml("NO<sub>2</sub>", HtmlCompat.FROM_HTML_MODE_LEGACY)
        binding.no2Value.text = polutionDetails?.no2.toString()
        binding.o3Title.text = Html.fromHtml("O<sub>3</sub>", HtmlCompat.FROM_HTML_MODE_LEGACY)
        binding.o3Value.text = polutionDetails?.o3.toString()
        binding.so2Title.text = Html.fromHtml("SO<sub>2</sub>", HtmlCompat.FROM_HTML_MODE_LEGACY)
        binding.so2Value.text = polutionDetails?.so2.toString()
        binding.pm25Title.text = Html.fromHtml("PM<sub>2.5</sub>", HtmlCompat.FROM_HTML_MODE_LEGACY)
        binding.pm25Value.text = polutionDetails?.pm25.toString()
        binding.pm10Title.text = Html.fromHtml("PM<sub>10</sub>", HtmlCompat.FROM_HTML_MODE_LEGACY)
        binding.pm10Value.text = polutionDetails?.pm10.toString()
        binding.nh3Title.text = Html.fromHtml("NH<sub>3</sub>", HtmlCompat.FROM_HTML_MODE_LEGACY)
        binding.nh3Value.text = polutionDetails?.nh3.toString()
    }

    fun updatePolutionDetails(polutionDetails: PolutionDetails) {
        this.polutionDetails = polutionDetails
        setUpText()
    }
}
