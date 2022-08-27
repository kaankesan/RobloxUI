package com.kaankesan.roblox

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavArgument
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kaankesan.roblox.Screens.FirstPage
import com.kaankesan.roblox.Screens.MainPage
import com.kaankesan.roblox.Screens.RegisterPage
import com.kaankesan.roblox.database.User
import com.kaankesan.roblox.objects.Screen
import com.kaankesan.roblox.ui.theme.RobloxTheme
import com.kaankesan.roblox.viewModel.SharedViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RobloxTheme {
                UsersApplication()
            }
        }
    }
}

@Composable
fun UsersApplication(){
    val vieModel : SharedViewModel = viewModel()
    val navController  = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.Opening.route
    ){
        composable(route = Screen.Opening.route){
            FirstPage(navController = navController)
        }

        composable(route = Screen.Register.route){
            RegisterPage(navController = navController)
        }
        
        composable(
            route = Screen.Main.route,
            arguments = listOf(navArgument("uuid"){
                type = NavType.StringType
            })
        ){
            MainPage(navController = navController)
        }
    }
}

