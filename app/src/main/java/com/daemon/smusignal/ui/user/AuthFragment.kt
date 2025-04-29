package com.daemon.smusignal.ui.user

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.daemon.smusignal.R
import com.daemon.smusignal.databinding.FragmentAuthBinding
import com.daemon.smusignal.di.AuthViewModelFactory
import com.daemon.smusignal.data.remote.RetrofitClient
import com.daemon.smusignal.data.remote.TokenManager

class AuthFragment : Fragment() {
    private lateinit var navController: NavController
    private var _binding: FragmentAuthBinding? = null
    private val binding: FragmentAuthBinding
        get() = requireNotNull(_binding){"FragmentLoginBinding -> null"}

    private val viewModel: AuthViewModel by activityViewModels {
        AuthViewModelFactory(
            RetrofitClient.authService,
            TokenManager(requireContext().getSharedPreferences("prefs", Context.MODE_PRIVATE))
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        fun updateInputState(input: View, button: View, isActive: Boolean) {
            if (isActive) {
                input.setBackgroundResource(R.drawable.bg_et_auth_active)
                button.isEnabled = true
                button.isSelected = true
            } else {
                input.setBackgroundResource(R.drawable.bg_et_auth_default)
                button.isEnabled = false
                button.isSelected = false
            }
        }

        // etAuthStdNum에 포커스가 갔을 때 상태 변경
        binding.etAuthStdNum.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                updateInputState(binding.etAuthStdNum, binding.btnAuthCodeSend, true)
                updateInputState(binding.etAuthCode, binding.btnConfirm, false)
            }
        }

        // etAuthCode에 포커스가 갔을 때 상태 변경
        binding.etAuthCode.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                updateInputState(binding.etAuthCode, binding.btnConfirm, true)
                updateInputState(binding.etAuthStdNum, binding.btnAuthCodeSend, false)
            }
        }

        // etAuthStdNum 변경 시 상태 변경
        binding.etAuthStdNum.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (!s.isNullOrEmpty()) {
                    updateInputState(binding.etAuthStdNum, binding.btnAuthCodeSend, true)
                    updateInputState(binding.etAuthCode, binding.btnConfirm, false)
                } else {
                    updateInputState(binding.etAuthStdNum, binding.btnAuthCodeSend, false)
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // etAuthCode 변경 시 상태 변경
        binding.etAuthCode.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (!s.isNullOrEmpty()) {
                    updateInputState(binding.etAuthCode, binding.btnConfirm, true)
                    updateInputState(binding.etAuthStdNum, binding.btnAuthCodeSend, false)
                } else {
                    updateInputState(binding.etAuthCode, binding.btnConfirm, false)
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        binding.btnAuthCodeSend.setOnClickListener {
            val mail = binding.etAuthStdNum.text.toString().trim()
            if (mail.isEmpty()) {
                Toast.makeText(requireContext(), "학번(메일)을 입력해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            binding.etAuthStdNum.setBackgroundResource(R.drawable.bg_et_auth_default)
            binding.btnAuthCodeSend.isSelected = false
            viewModel.requestMailCode(mail)
        }

        binding.btnConfirm.setOnClickListener {
            val code = binding.etAuthCode.text.toString().trim()
            if (code.isEmpty()) {
                Toast.makeText(requireContext(), "인증코드를 입력해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            binding.etAuthCode.setBackgroundResource(R.drawable.bg_et_auth_default)
            binding.btnConfirm.isSelected = false
            viewModel.verifyMailCode(code)
        }

        viewModel.stateRequestMailCode.observe(viewLifecycleOwner) { success ->
            if (success) {
                binding.tvStdNum.setTextColor(ContextCompat.getColor(requireContext(), R.color.TB))
                binding.etAuthCode.requestFocus()
            } else {
                binding.tvStdNum.setTextColor(ContextCompat.getColor(requireContext(), R.color.R400))
            }
        }

        viewModel.messageRequestMailCode.observe(viewLifecycleOwner) { message ->
            binding.tvStdNum.visibility = View.VISIBLE
            binding.tvStdNum.text = message
        }

        viewModel.stateVerifyMailCode.observe(viewLifecycleOwner) { success ->
            if (success) {
                binding.tvAuthCode.setTextColor(ContextCompat.getColor(requireContext(), R.color.TB))
                // 온보딩 화면 이동
                navController.navigate(R.id.action_navigation_auth_to_home_fragment)
            } else {
                binding.tvAuthCode.setTextColor(ContextCompat.getColor(requireContext(), R.color.R400))
            }
        }

        viewModel.messageVerifyMailCode.observe(viewLifecycleOwner) { message ->
            binding.tvAuthCode.visibility = View.VISIBLE
            binding.tvAuthCode.text = message
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}