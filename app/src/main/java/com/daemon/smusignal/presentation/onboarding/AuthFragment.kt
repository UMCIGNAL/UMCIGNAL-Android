package com.daemon.smusignal.presentation.onboarding

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
import com.daemon.smusignal.network.RetrofitClient
import com.daemon.smusignal.network.TokenManager

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

        binding.etAuthStdNum.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (!s.isNullOrEmpty()) {
                    binding.etAuthStdNum.setBackgroundResource(R.drawable.bg_et_auth_active)
                    // 글자가 한 글자라도 입력되면 버튼 활성화 및 selected 상태 적용
                    binding.btnAuth.isClickable = true
                    binding.btnAuth.isSelected = true
                    binding.etAuthCode.setBackgroundResource(R.drawable.bg_et_auth_default)
                    binding.btnConfirm.isSelected = false
                } else {
                    binding.etAuthStdNum.setBackgroundResource(R.drawable.bg_et_auth_default)
                    // 글자가 없으면 버튼 비활성화 및 unselected 상태로 변경
                    binding.btnAuth.isClickable = false
                    binding.btnAuth.isSelected = false
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        binding.etAuthCode.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (!s.isNullOrEmpty()) {
                    binding.etAuthCode.setBackgroundResource(R.drawable.bg_et_auth_active)
                    binding.btnConfirm.isSelected = true
                    binding.etAuthStdNum.setBackgroundResource(R.drawable.bg_et_auth_default)
                    // 글자가 없으면 버튼 비활성화 및 unselected 상태로 변경
                    binding.btnAuth.isClickable = false
                    binding.btnAuth.isSelected = false
                } else {
                    binding.etAuthCode.setBackgroundResource(R.drawable.bg_et_auth_default)
                    binding.btnConfirm.isSelected = false
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        binding.btnAuth.setOnClickListener {
            val mail = binding.etAuthStdNum.text.toString().trim()
            if (mail.isEmpty()) {
                Toast.makeText(requireContext(), "학번(메일)을 입력해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // 버튼 클릭 시 et_auth_std_num의 배경을 default로 재설정
            binding.etAuthStdNum.setBackgroundResource(R.drawable.bg_et_auth_default)
            // btn_auth의 상태를 selected로 변경 (selector에 의해 active drawable이 적용됨)
            binding.btnAuth.isSelected = false

            // API 호출: postMailCode 요청
            viewModel.requestMailCode(mail)
        }

        binding.btnConfirm.setOnClickListener {
            val code = binding.etAuthCode.text.toString().trim()
            if (code.isEmpty()) {
                Toast.makeText(requireContext(), "인증코드를 입력해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // 버튼 클릭 시 et_auth_code의 배경을 default로 재설정
            binding.etAuthCode.setBackgroundResource(R.drawable.bg_et_auth_default)
            // btn_confirm의 상태를 selected로 변경
            binding.btnConfirm.isSelected = true

            // API 호출: verifyMailCode 요청
            viewModel.verifyMailCode(code)
        }

        viewModel.mailCodeResponse.observe(viewLifecycleOwner) { response ->
            binding.tvStdNum.visibility = View.VISIBLE
            binding.tvStdNum.text = "인증코드를 전송했습니다."
            binding.tvStdNum.setTextColor(ContextCompat.getColor(requireContext(), R.color.TB))
        }

        viewModel.mailVerificationResponse.observe(viewLifecycleOwner) { response ->
            Toast.makeText(requireContext(), response.message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}