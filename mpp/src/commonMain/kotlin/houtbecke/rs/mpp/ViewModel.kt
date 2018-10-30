package houtbecke.rs.mpp

import kotlin.properties.ReadWriteProperty

typealias StringReadWriteProperty =  ReadWriteProperty<Any?, String>

class ViewModel(val network: Network,
                userImpl: StringReadWriteProperty,
                moodImpl: StringReadWriteProperty,
                statusImpl: StringReadWriteProperty) {

    var user: String by userImpl
    var mood: String by moodImpl

    var status: String by statusImpl

    init {
        user = "🧐"
        mood = "🙀"
        status = "😃 everything is fine"
    }

    // @TODO move interactions outside of the view model? For this simple example Network acts as the Interactor

    fun retrieveTapped() {
        status = "⏳ retrieving"


        try {
            val serverUser = valueOfEmoji(user)
            network.retrieveMood(serverUser)
                .onFailure {
                    status = "😣 Failed to update! You can only set a cat emoji as a mood! $it"
                }
                .onSuccess {
                    val serverMood = it.get("mood")
                    if (serverMood is String) {
                        mood = serverMood
                        status = "$user is $mood"
                    }
                    else
                        status = "🤷 mood of $serverUser is unknown, try setting one!"

                }
        } catch (e:IllegalArgumentException) {
            status = "🤭 use one of ${Emoticon.values().contentToString()} for a user: $e"
        }
    }


    fun updateTapped() {
        status = "⏳ updating"

        try {
            // example of client side validation, and error from the server
            network.setMood(valueOfEmoji(user), mood)
                    .onFailure {
                        status = "😣 Failed to update! You can only set a cat emoji as a mood! $it"
                    }
                    .onSuccess {
                        status = "$user is $mood"
                    }

        } catch (e:IllegalArgumentException) {
            status = "🤭 use one of ${Emoticon.values().contentToString()} for a user: $e"
        }
    }

}

