package com.github.slznvk.todoapp.di

import com.github.slznvk.todoapp.data.repository.TodoRepositoryImpl
import com.github.slznvk.todoapp.domain.repository.TodoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
interface RepositoryModule {

    @Binds
    fun bindsRepository(impl: TodoRepositoryImpl): TodoRepository
}
