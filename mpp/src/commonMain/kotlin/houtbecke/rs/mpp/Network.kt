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

    // private val client = io.ktor.client.HttpClient()

    //    fun about(callback: (String) -> Unit) {
    //        GlobalScope.launch(CommonCoroutineContext) {
    //            val result: String = client.get {
    //                url {
    //                    protocol = URLProtocol.HTTPS
    //                    port = 443
    //                    host = "tools.ietf.org"
    //                    encodedPath = "rfc/rfc1866.txt"
    //                }
    //            }
    //            callback(result)
    //        }
    //    }

    fun setMood(emoticon: Emoticon, mood:String, write: OnWrite, failure:OnFailure) {
        return firestore
                .collection("users")
                .document(emoticon.emoji
                ).set(
                    mapOf("mood" to mood),
                    write,
                    failure
                )
    }

    interface OnUserStatusModelSuccess: OnSuccess<UserStatusModel>

    fun retrieveMood(emoticon: Emoticon, success: OnSuccess<UserStatusModel>, failure: OnFailure) {
        firestore.collection("users").document(emoticon.emoji).get( { document ->
            document.data?.let { data ->
                success(UserStatusModel(emoticon.emoji, data))
            }
        }, failure)
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