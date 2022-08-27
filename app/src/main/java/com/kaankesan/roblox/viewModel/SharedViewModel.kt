package com.kaankesan.roblox.viewModel

import android.content.SharedPreferences
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.kaankesan.roblox.database.User
import com.kaankesan.roblox.database.UserDao

class SharedViewModel : ViewModel(){
    val users = mutableListOf<User>()
}