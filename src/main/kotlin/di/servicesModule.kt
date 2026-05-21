package com.routeplanner.api.di

import com.routeplanner.api.data.service.UserServiceImpl
import com.routeplanner.api.domain.service.UserService
import org.koin.dsl.module

val servicesModule = module {
    single<UserService> { UserServiceImpl(get()) }
}