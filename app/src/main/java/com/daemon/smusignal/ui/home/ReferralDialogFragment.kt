package com.daemon.smusignal.ui.home

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.daemon.smusignal.data.remote.RetrofitClient
import com.daemon.smusignal.databinding.FragmentReferralDialogBinding
import com.daemon.smusignal.di.ReferralViewModelFactory
import com.daemon.smusignal.ui.user.ReferralViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.daemon.smusignal.util.showToast

class ReferralDialogFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentReferralDialogBinding? = null
    private val binding: FragmentReferralDialogBinding
        get() = requireNotNull(_binding) { "FragmentReferralDialogBinding -> null" }

    private val viewModel: ReferralViewModel by activityViewModels {
        ReferralViewModelFactory(
            RetrofitClient.referralService
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReferralDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setClickListeners()

        setReferralInputWatcher()

        viewModel.getMyReferralCode()

        viewModel.referralCode.observe(viewLifecycleOwner) { code ->
            binding.tvReferralMyBody.text = code
        }

        viewModel.message.observe(viewLifecycleOwner) { msg ->
            binding.tvReferralInput.visibility = View.VISIBLE
            binding.tvReferralInput.text = msg
        }
    }

    private fun setClickListeners() {
        binding.btnReferralMy.setOnClickListener {
            val code = binding.tvReferralMyBody.text.toString()
            copyToClipboard(code)
            showToast("추천인 코드가 복사되었습니다")
        }
        binding.btnReferralConfirm.setOnClickListener {
            val code = binding.etReferralInput.text.toString().trim()
            if (code.isEmpty()) {
                showToast("추천인 코드를 입력하세요")
                return@setOnClickListener
            }
            viewModel.postReferralCode(code)
        }
    }

    private fun setReferralInputWatcher() {
        binding.etReferralInput.apply {
            setOnClickListener { isSelected = true }

            setOnFocusChangeListener { _, hasFocus ->
                isSelected = if (hasFocus) true else text.isNotEmpty()
            }

            addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    isSelected = !s.isNullOrEmpty()
                }
            })
        }
    }

    private fun copyToClipboard(text: String) {
        val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("referral_code", text)
        clipboard.setPrimaryClip(clip)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}