package com.bhyoo.testapp.database

import javax.inject.Inject


interface StepDatabaseWrapper {
    fun stepDao(): StepDao
}

class StepDatabaseWrapperImpl @Inject constructor(
    private val db: StepDatabase
) : StepDatabaseWrapper {
    override fun stepDao(): StepDao = db.stepDao()
}