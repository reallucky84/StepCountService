package com.bhyoo.testapp.repository

import androidx.lifecycle.MutableLiveData
import com.bhyoo.testapp.database.StepDatabaseWrapper
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.util.Calendar

class StepRepositoryTest {

    private lateinit var repository: StepRepository

    @MockK
    lateinit var db: StepDatabaseWrapper

    @Before
    fun setUp(){
        MockKAnnotations.init(this, relaxed = true)
        repository = StepRepositoryImpl(db)
    }

    @Test
    fun testgetTodayStepCountResult(){
        val expected = MutableLiveData(1)
        val todayTimeStamp = getTodayTimestamp()
        every { db.stepDao().getStepCount(todayTimeStamp) } returns expected
        Assert.assertEquals(repository.getTodayStepCount(), expected)
        verify { db.stepDao().getStepCount(todayTimeStamp) }
    }

    private fun getTodayTimestamp(): Long {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }
}