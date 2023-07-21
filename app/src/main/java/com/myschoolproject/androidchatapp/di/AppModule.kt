package com.myschoolproject.androidchatapp.di

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.myschoolproject.androidchatapp.data.repository.ChatRepositoryImpl
import com.myschoolproject.androidchatapp.domain.repository.ChatRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideChatRef() = Firebase.database.reference

    @Provides
    @Singleton
    fun provideChatRepository(chatRef: DatabaseReference): ChatRepository {
        return ChatRepositoryImpl(chatRef)
    }
}