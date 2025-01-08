package com.example.plantapp.domain.auht.repository.tips

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.plantapp.presentation.DailyTipsViewModel
import androidx.lifecycle.observe

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DailyTipsFragment : Fragment() {

    private lateinit var viewModel: DailyTipsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inițializează ViewModel
        viewModel = ViewModelProvider(this)[DailyTipsViewModel::class.java]

        // Creăm un layout LinearLayout dinamic
        val layout = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
        }

        // Adăugăm un TextView pentru sfatul zilei
        val textViewDailyTip = TextView(requireContext()).apply {
            text = "Press the button to see the tip of the day!"
            textSize = 18f
            gravity = Gravity.CENTER
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(16, 16, 16, 16)
            }
        }

        // Adăugăm un Button pentru a genera sfatul zilei
        val buttonGetTip = Button(requireContext()).apply {
            text = "Get the tip of the day"
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(16, 16, 16, 16)
            }
        }

        // Adăugăm TextView și Button în layout
        layout.addView(textViewDailyTip)
        layout.addView(buttonGetTip)

        // Setăm comportamentul butonului
        buttonGetTip.setOnClickListener {
            viewModel.fetchDailyTip()
        }

        // Colectăm StateFlow-ul din ViewModel
        lifecycleScope.launch {
            return@launch viewModel.dailyTip.collect { tip ->
                textViewDailyTip.text = tip ?: "No advice available!"
            }
        }

        return layout
    }
}
