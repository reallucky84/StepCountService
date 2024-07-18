package com.bhyoo.testapp.repository

import androidx.lifecycle.LiveData
import com.bhyoo.testapp.database.StepDatabaseWrapper
import java.util.Calendar
import java.util.TimeZone
import javax.inject.Inject


interface StepRepository{
    fun getTodayStepCount(): LiveData<Int>
}

class StepRepositoryImpl @Inject constructor(
    private val db: StepDatabaseWrapper
) : StepRepository {

    override fun getTodayStepCount(): LiveData<Int> {
        val todayTimeStamp = getTodayTimestamp()
        return db.stepDao().getStepCount(todayTimeStamp)
    }

    private fun getTodayTimestamp(): Long {
        val calendar = Calendar.getInstance(TimeZone.getDefault())
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }

}