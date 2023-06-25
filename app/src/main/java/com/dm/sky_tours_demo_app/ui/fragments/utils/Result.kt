package com.dm.sky_tours_demo_app.ui.fragments.utils

sealed class Result<out T : Any> {
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val errorMsg: String) : Result<Nothing>()
    object Loading : Result<Nothing>()
}
