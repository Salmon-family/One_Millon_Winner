package com.example.onemillonwinner.data

sealed class NetworkStatus<out T> {
    data class Success<T>(val data: T?) : NetworkStatus<T>()
    class Failure(val message: String) : NetworkStatus<Nothing>()
    object Loading : NetworkStatus<Nothing>()

    fun toData():T? = if (this is Success) data else null
}
