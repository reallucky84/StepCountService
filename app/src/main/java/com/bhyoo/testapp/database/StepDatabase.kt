package com.bhyoo.testapp.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [StepEntity::class], version = 1)
abstract class StepDatabase : RoomDatabase(){
    abstract fun stepDao(): StepDao
}
