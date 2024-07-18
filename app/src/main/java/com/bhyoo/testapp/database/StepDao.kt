package com.bhyoo.testapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import io.reactivex.rxjava3.core.Completable

@Dao
interface StepDao {
    @Query("SELECT COUNT(*) FROM step_schema WHERE createdAt > :today")
    fun getStepCount(today: Long): LiveData<Int>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(steps: StepEntity): Completable
}
