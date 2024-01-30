package com.example.pixelshop.util


sealed class Resource<out R> {
    data class Success<out R>(val data: R): Resource<R>()
    data class Failure<R>(val exception: R): Resource<Nothing>()
    object Loading: Resource<Nothing>()
    object Idle: Resource<Nothing>()
}
