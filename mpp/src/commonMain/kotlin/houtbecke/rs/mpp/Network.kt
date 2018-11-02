package houtbecke.rs.mpp

import houtbecke.rs.mpp.firebase.*
import io.ktor.client.request.get
import io.ktor.http.URLProtocol
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

internal expect val CommonCoroutineContext: CoroutineContext


enum class Emoticon(val emoji: String) {
    loopy("🤪"), rich("🧐"), nerd("🤓"), cool("😎"), alien("👽"), droid("🤖");

    override fun toString(): String {
        return emoji
    }
}

fun valueOfEmoji(s: String): Emoticon {
    try {
        return Emoticon.values().single {
            it.emoji == s
        }
    } catch (e: NoSuchElementException) {
        throw IllegalArgumentException(e)
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

    var listenerRegistration: ListenerRegistrationMPP? = null

    fun followUpdates(user: String, updateListener: (UserStatusModel) -> Unit, errorListener: OnFailure) {
        listenerRegistration?.remove()
        listenerRegistration = firestore.document("/users/$user").onSnapshot(
            {
                it.data?.let { data ->
                    val model = UserStatusModel(user, data)
                    updateListener.invoke(model)
                }
            },
            {
                errorListener.invoke(it)
            }
        )
    }

}