package com.ds.money_manager.base.helpers

import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ds.money_manager.utils.ApiException
import com.github.kittinunf.result.Result
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

typealias Response<K> = Deferred<Result<K, Exception>>

@OptIn(DelicateCoroutinesApi::class)
fun LifecycleCoroutineScope.launchUI(
    context: CoroutineContext = Dispatchers.Main,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
): Job = this.launch(context, start, block)

fun ViewModel.launchUI(
    context: CoroutineContext = Dispatchers.Main,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
): Job = viewModelScope.launch(context, start, block)

inline fun <T : Any> CoroutineScope.asyncR(crossinline callback: () -> T): Response<T> =
    this.async(Dispatchers.IO) {
        Result.of {
            callback.invoke()
        }
    }

suspend inline fun <V : Any> Response<V>.awaitFold(
    success: (V) -> Unit,
    failure: (Exception) -> Unit
) = this.await().fold(success, failure)

suspend inline fun <V : Any> Response<V>.awaitFoldApi(
    success: (V) -> Unit,
    apiFailure: (ApiException) -> Unit,
    failure: (Exception) -> Unit
) {
    this.await().fold(success) { e ->
        when (e) {
            is ApiException -> apiFailure(e)
            else -> failure(e)
        }
    }
}
