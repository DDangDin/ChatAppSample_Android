package com.myschoolproject.androidchatapp.data.source.remote.firebase

data class MyChatListPreview(
    val friendName: String = "",
    val lastMessage: Chat = Chat()
)

fun MyChatListPreview.lastMessagePreview(startIndex: Int, endIndex: Int): String {
    return if (this.lastMessage.message.length > 15) {
        this.lastMessage.message.substring(startIndex, endIndex) + " ..."
    } else {
        this.lastMessage.message
    }
}
