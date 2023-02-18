package me.ruslan1024.kk2048game.ui.screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import me.ruslan1024.kk2048game.R
import me.ruslan1024.kk2048game.databinding.FragmentMenuBinding

class MenuScreen : Fragment(R.layout.fragment_menu) {
    private val binding by viewBinding(FragmentMenuBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        setListeners()

    }

    private fun setListeners() {
        binding.apply {
            btnExit.setOnClickListener { findNavController().popBackStack() }
            btnStartGame.setOnClickListener { findNavController().navigate(R.id.action_menuScreen_to_mainScreen) }
            icProfile.setOnClickListener { findNavController().navigate(R.id.action_menuScreen_to_registerScreen) }
//            icProfile.setOnClickListener { findNavController().navigate(R.id.) }
        }
    }

    private fun init() {

    }
}