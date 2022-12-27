package com.ds.money_manager.base.helpers

import com.ds.money_manager.utils.ApiException
import com.github.kittinunf.result.Result
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

typealias Response<K> = Deferred<Result<K, Exception>>

@OptIn(DelicateCoroutinesApi::class)
fun launchUI(
    context: CoroutineContext = Dispatchers.Main,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
): Job = GlobalScope.launch(context, start, block)


@OptIn(DelicateCoroutinesApi::class)
inline fun <T : Any> asyncR(crossinline callback: () -> T): Response<T> =
    GlobalScope.async {
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
