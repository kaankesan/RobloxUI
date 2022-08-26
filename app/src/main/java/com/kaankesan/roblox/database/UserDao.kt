package com.kaankesan.roblox.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Query("SELECT* FROM user")
    suspend fun getAll(): List<User>

    @Query("SELECT * FROM user WHERE Name LIKE :first")
    suspend fun findByName(first: String) : User

    @Insert
    suspend fun insertAll(vararg users: User)

    @Insert
    suspend fun insertOne(user: User)

    @Delete
    suspend fun delete(user: User)
}