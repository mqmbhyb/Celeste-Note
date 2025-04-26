package com.bhyb.celestenote.domain.usecase.remote

import com.bhyb.celestenote.domain.usecase.remote.userusecase.UserLogin
import com.bhyb.celestenote.domain.usecase.remote.userusecase.UserRegister

data class UserUseCase (
    val userLogin: UserLogin,
    val userRegister: UserRegister
)