package com.bhyoo.testapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bhyoo.testapp.repository.StepRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StepViewModel @Inject constructor(
    private val stepRepository: StepRepository
) : ViewModel() {

    fun getTodayStepCount(): LiveData<Int> {
        return stepRepository.getTodayStepCount()
    }
}