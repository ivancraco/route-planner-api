package com.routeplanner.api.di

import com.routeplanner.api.data.service.RouteServiceImpl
import com.routeplanner.api.data.service.StopServiceImpl
import com.routeplanner.api.data.service.UserServiceImpl
import com.routeplanner.api.domain.service.RouteService
import com.routeplanner.api.domain.service.StopService
import com.routeplanner.api.domain.service.UserService
import org.koin.dsl.module

val ServicesModule = module {
    single<UserService> { UserServiceImpl(get()) }
    single<RouteService> { RouteServiceImpl(get()) }
    single<StopService> { StopServiceImpl(get()) }
}