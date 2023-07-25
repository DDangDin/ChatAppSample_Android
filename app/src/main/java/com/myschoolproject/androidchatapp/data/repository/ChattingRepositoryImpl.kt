package com.myschoolproject.androidchatapp.data.repository

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.myschoolproject.androidchatapp.core.common.Constants.ERROR_MESSAGE_INTERNET_CONNECTION
import com.myschoolproject.androidchatapp.core.common.Constants.ERROR_MESSAGE_UNEXPECTED
import com.myschoolproject.androidchatapp.core.common.Constants.FIREBASE_DATABASE_CHAT_TABLE
import com.myschoolproject.androidchatapp.core.common.Constants.FIREBASE_DATABASE_INITIALIZE_MESSAGE
import com.myschoolproject.androidchatapp.core.common.Constants.FIREBASE_DATABASE_USER_TABLE
import com.myschoolproject.androidchatapp.core.utils.Resource
import com.myschoolproject.androidchatapp.data.source.remote.firebase.Chat
import com.myschoolproject.androidchatapp.data.source.remote.firebase.UserStatus
import com.myschoolproject.androidchatapp.domain.repository.ChattingRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Locale

class ChattingRepositoryImpl(
    private val chatRef: DatabaseReference
): ChattingRepository {

    override suspend fun sendMessage(myName: String, friendName: String, message: String) {

        val now = System.currentTimeMillis()
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd-HH:mm:ss", Locale.KOREAN).format(now)

        val chat = Chat(
            username = myName,
            message = message
        )

        chatRef
            .child(FIREBASE_DATABASE_CHAT_TABLE)
            .child("${myName}-${friendName}")
            .child(simpleDateFormat)
            .setValue(chat)
        chatRef
            .child(FIREBASE_DATABASE_CHAT_TABLE)
            .child("${friendName}-${myName}")
            .child(simpleDateFormat)
            .setValue(chat)
    }

    override suspend fun getChatMessages(
        myName: String,
        friendName: String
    ): Flow<Resource<List<Chat>>> = callbackFlow {
        chatRef.child(FIREBASE_DATABASE_CHAT_TABLE).child("${myName}-${friendName}")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    Log.d(
                        "ChattingLog",
                        "chattingRepository Trigger!"
                    )
                    val chatList = arrayListOf<Chat>()
                    snapshot.children.forEach { s ->
                        chatList.add(s.getValue(Chat::class.java) ?: Chat())
                    }
                    trySend(Resource.Success(chatList)).isSuccess
                }

                override fun onCancelled(error: DatabaseError) {
                    trySend(Resource.Error(error.message))
                }
            })

        awaitClose { close() }
    }

    override suspend fun initializeChat(
        myName: String,
        friendName: String
    ): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading())

        try {

            val chat = Chat(
                username = "Admin",
                message = ""
            )

            chatRef
                .child(FIREBASE_DATABASE_CHAT_TABLE)
                .child("${myName}-${friendName}")
                .child(FIREBASE_DATABASE_INITIALIZE_MESSAGE)
                .setValue(chat)
            chatRef
                .child(FIREBASE_DATABASE_CHAT_TABLE)
                .child("${friendName}-${myName}")
                .child(FIREBASE_DATABASE_INITIALIZE_MESSAGE)
                .setValue(chat)

            emit(Resource.Success(true))
        } catch (e: IOException) {
            emit(Resource.Error(ERROR_MESSAGE_INTERNET_CONNECTION))
        }
    }

    override suspend fun startChatting(myName: String) {
        chatRef
            .child(FIREBASE_DATABASE_USER_TABLE)
            .child(myName)
            .setValue(
                UserStatus(
                    nickname = myName,
                    isChatting = true
                )
            )
    }

    override suspend fun quitChatting(myName: String, friendName: String) {
        chatRef
            .child(FIREBASE_DATABASE_USER_TABLE)
            .child(myName)
            .setValue(
                UserStatus(
                    nickname = myName,
                    isChatting = false
                )
            )
        chatRef
            .child(FIREBASE_DATABASE_CHAT_TABLE)
            .child("${myName}-${friendName}")
            .child(FIREBASE_DATABASE_INITIALIZE_MESSAGE)
            .removeValue()
        chatRef
            .child(FIREBASE_DATABASE_CHAT_TABLE)
            .child("${friendName}-${myName}")
            .child(FIREBASE_DATABASE_INITIALIZE_MESSAGE)
            .removeValue()
    }
}