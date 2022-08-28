package com.kaankesan.roblox.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import javax.inject.Singleton

@Dao
@Module
@InstallIn(Singleton::class)
interface UserDao {

    @Query("SELECT * FROM user")
    suspend fun getAll(): List<User>

    @Query("SELECT * FROM user WHERE Name LIKE :first")
    suspend fun findByName(first: String) : User

    @Query("SELECT * FROM user WHERE id LIKE :uuid")
    suspend fun getOne(uuid:String): User

    @Query("SELECT * FROM user WHERE Name LIKE :Search")
    suspend fun getFromSearch(Search:String) : List<User>
    @Insert
    suspend fun insertAll(vararg users: User)

    @Insert
    suspend fun insertOne(user: User)

    @Delete
    suspend fun delete(user: User)
}