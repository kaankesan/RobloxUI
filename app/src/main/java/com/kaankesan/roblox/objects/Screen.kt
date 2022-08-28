package com.kaankesan.roblox.objects

sealed class Screen(val route:String) {
    object Login : Screen("Login")
    object Register: Screen("Register")
    object Opening : Screen("Opening")
    object Main: Screen("Main/{name}")
}