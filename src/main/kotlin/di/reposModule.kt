package com.routeplanner.api.di

import com.routeplanner.api.data.repository.UserRepositoryImpl
import com.routeplanner.api.domain.repository.UserRepository
import org.koin.dsl.module

val reposModule = module {
    single<UserRepository> { UserRepositoryImpl() }
}