package houtbecke.rs.mpp

import io.ktor.client.request.get
import io.ktor.http.URLProtocol
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

internal expect val CommonCoroutineContext: CoroutineContext

class Network {

    private val client = io.ktor.client.HttpClient()

    fun about(callback: (String) -> Unit) {
        GlobalScope.launch(CommonCoroutineContext) {
            val result: String = client.get {
                url {
                    protocol = URLProtocol.HTTPS
                    port = 443
                    host = "tools.ietf.org"
                    encodedPath = "rfc/rfc1866.txt"
                }
            }
            callback(result)
        }
    }
}