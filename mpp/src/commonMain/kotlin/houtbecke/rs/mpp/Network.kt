package houtbecke.rs.mpp

import houtbecke.rs.mpp.firebase.*
import io.ktor.client.request.get
import io.ktor.http.URLProtocol
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

internal expect val CommonCoroutineContext: CoroutineContext

fun valueOfEmoji(s: String): Emoticon {
    try {
        return Emoticon.values().filter {
            it.emoji == s
        }.single()
    } catch (e: NoSuchElementException) {
        throw IllegalArgumentException(e)
    }
}


enum class Emoticon(val emoji: String) {
    loopy("🤪"), rich("🧐"), nerd("🤓"), cool("😎"), alien("👽"), droid("🤖");

    override fun toString(): String {
        return emoji
    }
}


class Network(val firestore: FirestoreMPP) {

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

    fun setMood(emoticon: Emoticon, mood:String): UnitTaskMPP {
        return firestore.collection("users").document(emoticon.emoji).set(
            mapOf("mood" to mood)
        )
    }

    fun retrieveMood(emoticon: Emoticon): DocumentSnapshotTaskMPP {
        return firestore.collection("users").document(emoticon.emoji).get()

    }

}