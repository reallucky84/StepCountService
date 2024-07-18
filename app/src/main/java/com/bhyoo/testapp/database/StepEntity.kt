package com.bhyoo.testapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "step_schema")
data class StepEntity(
    @PrimaryKey(autoGenerate = true) val uid: Long = 0,
    @ColumnInfo val createdAt: Long
)