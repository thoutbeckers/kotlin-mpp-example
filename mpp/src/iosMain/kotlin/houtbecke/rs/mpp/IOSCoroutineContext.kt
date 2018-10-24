package houtbecke.rs.mpp

import kotlin.coroutines.CoroutineContext

internal actual val CommonCoroutineContext: CoroutineContext = houtbecke.rs.mpp.MainQueueCoroutineDispatcher()
