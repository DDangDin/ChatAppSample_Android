package com.myschoolproject.androidchatapp.core.common

object Constants {

    // SharedPreference Key Name
    const val PREFERENCE_USERNAME = "username"

    // Firebase Realtime Database
    const val FIREBASE_DATABASE_USER_TABLE = "user"
    const val FIREBASE_DATABASE_CHAT_TABLE = "chat"
    const val FIREBASE_DATABASE_INITIALZE_MESSAGE = "init"
    // Firebase Realtime Database - Message
    const val FIREBASE_DATABASE_INITIALIZE_SUCCESS = "db_init_success"
    const val FIREBASE_DATABASE_INITIALIZE_EXISTS_ERROR = "이미 있는 닉네임 입니다."

    // Unexpected Error Messages
    const val ERROR_MESSAGE_UNEXPECTED = "알 수 없는 오류"
}