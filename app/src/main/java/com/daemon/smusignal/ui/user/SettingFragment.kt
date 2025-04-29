package com.daemon.smusignal.ui.user

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.daemon.smusignal.databinding.FragmentSettingBinding

class SettingFragment : Fragment() {
    private lateinit var navController: NavController
    private var _binding: FragmentSettingBinding? = null
    private val binding: FragmentSettingBinding
        get() = requireNotNull(_binding){"FragmentSettingBinding -> null"}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        setClickListeners()
    }

    private fun setClickListeners() {
        binding.btnBack.setOnClickListener {
            navController.popBackStack()
        }
        binding.btnTermsOfUse.setOnClickListener {
            openUrl("https://makeus-challenge.notion.site/1bcb57f4596b803785d1c1870fd58088")
        }
        binding.btnPrivacyPolicy.setOnClickListener {
            openUrl("https://makeus-challenge.notion.site/1bcb57f4596b8006b1a3c4cdf165d5e1")
        }
    }

    private fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}