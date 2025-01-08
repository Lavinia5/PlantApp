package com.example.plantapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.plantapp.domain.auht.repository.tips.DailyTipsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class DailyTipsViewModel @Inject constructor(
    private val dailyTipsRepository: DailyTipsRepository
) : ViewModel() {

    private val _dailyTip = MutableStateFlow<String?>(null)
    val dailyTip: StateFlow<String?> = _dailyTip.asStateFlow()

    fun fetchDailyTip() {
        _dailyTip.value = dailyTipsRepository.getDailyTip()
    }
}
