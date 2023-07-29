package com.myschoolproject.androidchatapp.data.repository

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.myschoolproject.androidchatapp.core.common.Constants.ERROR_MESSAGE_NO_USER
import com.myschoolproject.androidchatapp.core.common.Constants.ERROR_MESSAGE_UNEXPECTED
import com.myschoolproject.androidchatapp.core.common.Constants.FIREBASE_DATABASE_INITIALIZE_EXISTS_ERROR
import com.myschoolproject.androidchatapp.core.common.Constants.FIREBASE_DATABASE_USER_TABLE
import com.myschoolproject.androidchatapp.core.utils.Resource
import com.myschoolproject.androidchatapp.data.source.remote.firebase.UserStatus
import com.myschoolproject.androidchatapp.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ChatRepositoryImpl(
    private val chatRef: DatabaseReference
) : ChatRepository {

    override suspend fun initializeUser(myName: String): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading())

        // Focus Coroutine Block
        try {
            val exists = suspendCoroutine<Boolean> { continuation ->
                // 중복 검사
                chatRef.child(FIREBASE_DATABASE_USER_TABLE).addListenerForSingleValueEvent(object: ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val exists = snapshot.child(myName).exists()
                        continuation.resume(exists)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        continuation.resume(false)
                    }
                })
            }

            if (exists) {
                emit(Resource.Error(FIREBASE_DATABASE_INITIALIZE_EXISTS_ERROR))
            } else {
                // 초기화 (사실 이 부분도 성공/실패 여부 체크 해줘야 함)
                chatRef
                    .child(FIREBASE_DATABASE_USER_TABLE)
                    .child(myName)
                    .setValue(UserStatus(nickname = myName, isChatting = false))
                emit(Resource.Success(true))
            }
        } catch (e: IOException) {
            emit(Resource.Error(e.localizedMessage))
        }
    }

    override suspend fun checkNickname(myName: String): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading())

        // Focus Coroutine Block
        try {
            val exists = suspendCoroutine<Boolean> { continuation ->
                // 중복 검사
                chatRef.child(FIREBASE_DATABASE_USER_TABLE).addListenerForSingleValueEvent(object: ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val exists = snapshot.child(myName).exists()
                        continuation.resume(exists)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        continuation.resume(false)
                    }
                })
            }

            if (exists) {
                emit(Resource.Success(true))
            } else {
                emit(Resource.Error(ERROR_MESSAGE_NO_USER))
            }
        } catch (e: IOException) {
            emit(Resource.Error(e.localizedMessage))
        }
    }

    override suspend fun getUserList(): Flow<Resource<List<UserStatus>>> = flow {
        emit(Resource.Loading())

        try {
            val userList = arrayListOf<UserStatus>()
            val isSuccess = suspendCoroutine<Boolean> { continuation ->
                chatRef.child(FIREBASE_DATABASE_USER_TABLE).addListenerForSingleValueEvent(object: ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.children.forEach { it ->
                            it.getValue(UserStatus::class.java)?.let { user -> userList.add(user) }
                        }
                        continuation.resume(true)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        continuation.resume(false)
                    }
                })
            }

            if (isSuccess) {
                emit(Resource.Success(userList))
            } else {
                emit(Resource.Error(ERROR_MESSAGE_UNEXPECTED))
            }
        } catch (e: IOException) {
            emit(Resource.Error(e.localizedMessage))
        }
    }

}