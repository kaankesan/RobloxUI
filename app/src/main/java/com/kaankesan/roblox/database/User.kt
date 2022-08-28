package com.kaankesan.roblox.database

import androidx.room.AutoMigration
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.inject.Inject


@Entity(tableName = "user")
data class User @Inject constructor(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "Name") val name: String,
    @ColumnInfo(name = "Password") val password: String,
    @ColumnInfo(name = "Mail") val mail: String
    ){
    fun test(){
        println("testing")
    }
}
