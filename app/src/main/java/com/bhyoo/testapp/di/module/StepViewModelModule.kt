package com.bhyoo.testapp.di.module

import com.bhyoo.testapp.repository.StepRepository
import com.bhyoo.testapp.repository.StepRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
interface StepViewModelModule {

    @ViewModelScoped
    @Binds
    fun bindStepRepository(impl: StepRepositoryImpl): StepRepository

}