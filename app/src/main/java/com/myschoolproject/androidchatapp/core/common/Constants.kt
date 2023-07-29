package com.myschoolproject.androidchatapp.core.common

object Constants {

    // SharedPreference Key Name
    const val PREFERENCE_USERNAME = "username"

    // Firebase Realtime Database
    const val FIREBASE_DATABASE_USER_TABLE = "user"
    const val FIREBASE_DATABASE_CHAT_TABLE = "chat"
    const val FIREBASE_DATABASE_INITIALIZE_MESSAGE = "00_init"
    // Firebase Realtime Database - Message
    const val FIREBASE_DATABASE_INITIALIZE_EXISTS_ERROR = "이미 있는 닉네임 입니다."

    // Error Messages
    const val ERROR_MESSAGE_UNEXPECTED = "알 수 없는 오류"
    const val ERROR_MESSAGE_NICKNAME_BLANK = "닉네임을 입력 해 주세요"
    const val ERROR_MESSAGE_NO_USER = "존재하지 않는 계정"
    const val ERROR_MESSAGE_INTERNET_CONNECTION = "인터넷 연결을 확인 해 주세요"
}