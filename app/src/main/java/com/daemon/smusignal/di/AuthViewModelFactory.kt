package com.daemon.smusignal.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.daemon.smusignal.data.remote.AuthService
import com.daemon.smusignal.data.repository.AuthRepository
import com.daemon.smusignal.data.remote.TokenManager
import com.daemon.smusignal.ui.user.AuthViewModel

class AuthViewModelFactory(
    private val authService: AuthService,
    private val tokenManager: TokenManager
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            val repository = AuthRepository(authService, tokenManager)
            return AuthViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}