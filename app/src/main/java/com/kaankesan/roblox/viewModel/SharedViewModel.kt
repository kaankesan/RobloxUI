package com.kaankesan.roblox.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.kaankesan.roblox.database.User

class SharedViewModel : ViewModel(){
    val users = mutableListOf<User>()
    val inAccount = mutableStateOf(false)
}