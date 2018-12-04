package com.wozpi.core.service

interface ApiCallback<T> {
    fun  getResult(data: T)
}