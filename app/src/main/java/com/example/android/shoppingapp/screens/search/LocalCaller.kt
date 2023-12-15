package com.example.android.shoppingapp.screens.search

import android.util.Log
import com.example.android.shoppingapp.R

suspend inline fun <T> dbCall(crossinline call: suspend () -> T): Response<T> {
    return try {
        Response.Success(data = call())
    } catch (e: Exception) {
        Log.d("DB CALL EXCEPTION", e.stackTraceToString())
        Response.Error(errorMessageId = R.string.unknown_error)
    }
}