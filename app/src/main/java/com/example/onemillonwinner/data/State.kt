package com.example.onemillonwinner.data

sealed class State<out T> {
    data class Success<T>(val data: T?) : State<T>()
    class Failure(val message: String) : State<Nothing>()
    object Loading : State<Nothing>()

    ////will delete it
    object Complete : State<Nothing>()

    fun toData(): T? = if (this is Success) data else null
}
