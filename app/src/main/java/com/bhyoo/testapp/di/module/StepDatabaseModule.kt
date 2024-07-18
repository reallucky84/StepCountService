package com.bhyoo.testapp.di.module

import android.content.Context
import androidx.room.Room
import com.bhyoo.testapp.database.StepDatabase
import com.bhyoo.testapp.database.StepDatabaseWrapper
import com.bhyoo.testapp.database.StepDatabaseWrapperImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface StepDatabaseModule {

    @Singleton
    @Binds
    fun bindStepDatabaseWarpper(impl: StepDatabaseWrapperImpl): StepDatabaseWrapper

    companion object {
        @Singleton
        @Provides
        fun provideDatabase(@ApplicationContext context: Context): StepDatabase {
            return Room.databaseBuilder(
                context,
                StepDatabase::class.java, "step_db"
            ).build()
        }

        @Provides
        fun provideDao(database: StepDatabase) = database.stepDao()
    }


}