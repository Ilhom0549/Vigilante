package com.crazylegend.vigilante.home

import android.os.Bundle
import android.view.View
import com.crazylegend.kotlinextensions.fragments.longToast
import com.crazylegend.kotlinextensions.views.setOnClickListenerCooldown
import com.crazylegend.viewbinding.viewBinding
import com.crazylegend.vigilante.R
import com.crazylegend.vigilante.abstracts.AbstractFragment
import com.crazylegend.vigilante.databinding.FragmentHomeBinding
import com.crazylegend.vigilante.di.providers.PermissionProvider
import com.crazylegend.vigilante.utils.isVigilanteRunning
import com.crazylegend.vigilante.utils.startVigilante
import com.crazylegend.vigilante.utils.stopVigilante
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Created by crazy on 10/14/20 to long live and prosper !
 */
@AndroidEntryPoint
class HomeFragment : AbstractFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    @Inject
    lateinit var permissionProvider: PermissionProvider

    override val binding by viewBinding(FragmentHomeBinding::bind)

    private val isServiceEnabled get() = permissionProvider.isAccessibilityEnabled

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.controlSwitch.setOnClickListenerCooldown {
            dispatchLogic()
        }
    }

    private fun dispatchLogic() {
        if (isServiceEnabled) disableTheService() else enableTheService()
    }

    private fun disableTheService() {
        if (permissionProvider.isVigilanteRunning() && permissionProvider.isAccessibilityEnabled) {
            permissionProvider.askForAccessibilityPermissions()
            if (requireContext().isVigilanteRunning()) {
                requireContext().stopVigilante()
            }
            longToast(R.string.disable_the_service)
        }
    }

    private fun enableTheService() {
        if (!permissionProvider.hasAccessibilityPermission()) {
            permissionProvider.askForAccessibilityPermissions()
            longToast(R.string.enable_the_service)
        } else {
            requireContext().startVigilante()
        }
    }

    private val statusImage get() = if (permissionProvider.isAccessibilityEnabled) R.drawable.ic_check else R.drawable.ic_closed

    override fun onResume() {
        super.onResume()
        binding.controlSwitch.setImageResource(statusImage)
    }
}