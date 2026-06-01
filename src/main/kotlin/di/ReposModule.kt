package com.routeplanner.api.di

import com.routeplanner.api.data.repository.RouteRepositoryImpl
import com.routeplanner.api.data.repository.StopRepositoryImpl
import com.routeplanner.api.data.repository.UserRepositoryImpl
import com.routeplanner.api.domain.repository.RouteRepository
import com.routeplanner.api.domain.repository.StopRepository
import com.routeplanner.api.domain.repository.UserRepository
import org.koin.dsl.module

val ReposModule = module {
    single<UserRepository> { UserRepositoryImpl() }
    single<RouteRepository> { RouteRepositoryImpl() }
    single<StopRepository> { StopRepositoryImpl() }
}