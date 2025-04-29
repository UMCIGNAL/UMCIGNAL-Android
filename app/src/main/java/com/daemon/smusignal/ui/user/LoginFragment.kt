package com.daemon.smusignal.ui.user

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.daemon.smusignal.R
import com.daemon.smusignal.data.remote.RetrofitClient
import com.daemon.smusignal.data.remote.TokenManager
import com.daemon.smusignal.databinding.FragmentLoginBinding
import com.daemon.smusignal.di.AuthViewModelFactory

class LoginFragment : Fragment() {
    private lateinit var navController: NavController
    private var _binding: FragmentLoginBinding? = null
    private val binding: FragmentLoginBinding
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
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        viewModel.checkSignUp()

        binding.btnLogin.setOnClickListener {
            viewModel.messageCheckSignUp.observe(viewLifecycleOwner) { isCompleted ->
                when (isCompleted) {
                    true  -> navController.navigate(R.id.action_navigation_login_to_home_fragment)
                    false -> navController.navigate(R.id.action_navigation_login_to_auth_fragment)
                    null  -> {  }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}