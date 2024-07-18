package com.bhyoo.testapp.viewmodel

import androidx.lifecycle.MutableLiveData
import com.bhyoo.testapp.database.StepDatabaseWrapper
import com.bhyoo.testapp.repository.StepRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class StepViewModelTest {

    private lateinit var viewModel: StepViewModel

    @MockK
    lateinit var repository: StepRepository

    @Before
    fun setUp(){
        MockKAnnotations.init(this, relaxed = true)
        viewModel = StepViewModel(repository)
    }

    @Test
    fun testgetTodayStepCount(){
        val expected = MutableLiveData(1)
        every { repository.getTodayStepCount() } returns expected
        Assert.assertEquals(viewModel.getTodayStepCount(), expected)
    }
}