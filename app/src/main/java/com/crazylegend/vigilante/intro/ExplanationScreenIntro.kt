package com.crazylegend.vigilante.intro

import android.os.Bundle
import android.view.View
import com.crazylegend.kotlinextensions.views.setOnClickListenerCooldown
import com.crazylegend.viewbinding.viewBinding
import com.crazylegend.vigilante.R
import com.crazylegend.vigilante.abstracts.AbstractFragment
import com.crazylegend.vigilante.databinding.FragmentIntroExplanationBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by crazy on 11/9/20 to long live and prosper !
 */
@AndroidEntryPoint
class ExplanationScreenIntro : AbstractFragment<FragmentIntroExplanationBinding>(R.layout.fragment_intro_explanation) {
    override val binding by viewBinding(FragmentIntroExplanationBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.next.setOnClickListenerCooldown {
            goToScreen(ExplanationScreenIntroDirections.destinationFinalScreen())
        }
    }
}