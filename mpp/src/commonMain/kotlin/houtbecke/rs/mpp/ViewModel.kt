package houtbecke.rs.mpp

import houtbecke.rs.mpp.binding.MutableListReadWriteProperty
import houtbecke.rs.mpp.binding.StringReadWriteProperty
import kotlin.properties.ReadWriteProperty

class ViewModel(val network: Network,
                userImpl: StringReadWriteProperty,
                moodImpl: StringReadWriteProperty,
                statusImpl: StringReadWriteProperty,
                updatesImpl: MutableListReadWriteProperty<UserStatusModel>) {

    var user: String by userImpl
    var mood: String by moodImpl

    var status: String by statusImpl

    var updates: MutableList<UserStatusModel> by updatesImpl

    init {
        user = "🧐"
        mood = "🙀"
        status = "😃 everything is fine"
        follow()

    }


    fun follow() {
        network.followUpdates(user, {
            updates.add(it)
            updates = updates // yes?
        },   {
            status = "🤬 Could not listen for updates: $it"
        })
        status+=", following updates of $user"

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
                    follow()
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
                        follow()
                    }

        } catch (e:IllegalArgumentException) {
            status = "🤭 use one of ${Emoticon.values().contentToString()} for a user: $e"
        }
    }

}

