package com.daemon.smusignal.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.daemon.smusignal.R
import com.daemon.smusignal.data.remote.RetrofitClient
import com.daemon.smusignal.databinding.FragmentHomeBinding
import com.daemon.smusignal.di.ReferralViewModelFactory
import com.daemon.smusignal.ui.user.ReferralViewModel

class HomeFragment : Fragment() {
    private lateinit var navController: NavController
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = requireNotNull(_binding) { "FragmentHomeBinding -> null" }

    private val viewModel: ReferralViewModel by activityViewModels {
        ReferralViewModelFactory(
            RetrofitClient.referralService
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        setClickListeners()
        viewModel.getMyRerollCount()
        viewModel.rerollCount.observe(viewLifecycleOwner) { count ->
            binding.tvRerollCount.text = count.toString()
        }
    }

    private fun setClickListeners() {
        binding.btnMatchResult.setOnClickListener {
            showToast("매칭 결과")
        }
        binding.btnMyInfo.setOnClickListener {
            showToast("내 매칭 정보 수정")
        }
        binding.btnCrushEdit.setOnClickListener {
            showToast("이상형 재설정")
        }
        binding.btnEvent1.setOnClickListener {
            val bottomSheet = ReferralDialogFragment()
            bottomSheet.show(childFragmentManager, bottomSheet.tag)
        }
        binding.btnSetting.setOnClickListener {
            navController.navigate(R.id.action_navigation_home_to_setting)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}