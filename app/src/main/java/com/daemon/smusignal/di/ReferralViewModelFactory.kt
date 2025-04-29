package com.daemon.smusignal.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.daemon.smusignal.data.remote.ReferralService
import com.daemon.smusignal.data.repository.AuthRepository
import com.daemon.smusignal.data.remote.TokenManager
import com.daemon.smusignal.data.repository.ReferralRepository
import com.daemon.smusignal.ui.user.AuthViewModel
import com.daemon.smusignal.ui.user.ReferralViewModel

class ReferralViewModelFactory(
    private val referralService: ReferralService,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReferralViewModel::class.java)) {
            val repository = ReferralRepository(referralService)
            return ReferralViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}