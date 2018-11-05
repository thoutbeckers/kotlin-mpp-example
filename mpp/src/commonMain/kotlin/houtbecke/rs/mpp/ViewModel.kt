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
            network.retrieveMood(serverUser,
                {   userStatusModel:UserStatusModel ->
                    follow()
                    userStatusModel.mood?.let {
                        status = "$user is $it"
                    } ?: run {
                        status = "🤷 mood of $serverUser is unknown, try setting one!"
                    }


                } as Network.OnUserStatusModelSuccess,
                {
                    status = "😣 Failed to update! You can only set a cat emoji as a mood! $it"
                }
            )

        } catch (e:IllegalArgumentException) {
            status = "🤭 use one of ${Emoticon.values().contentToString()} for a user: $e"
        }
    }


    fun updateTapped() {
        status = "⏳ updating"

        try {
            // example of client side validation, and error from the server
            network.setMood(valueOfEmoji(user), mood,
                {
                    status = "$user is $mood"
                    follow()
                },
                {
                    status = "😣 Failed to update! You can only set a cat emoji as a mood! $it"
                }
            )
        } catch (e:IllegalArgumentException) {
            status = "🤭 use one of ${Emoticon.values().contentToString()} for a user: $e"
        }
    }

}

