package com.pma.weatherapp.base.functional

import kotlin.coroutines.CoroutineContext

interface ICoroutineContextProvider {
    val main: CoroutineContext
    val io: CoroutineContext
    val default: CoroutineContext
}