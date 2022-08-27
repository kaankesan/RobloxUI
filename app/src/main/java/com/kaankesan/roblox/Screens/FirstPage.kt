package com.kaankesan.roblox.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.kaankesan.roblox.objects.Screen

@Composable
fun FirstPage(navController: NavController){
    Column(modifier = Modifier
        .fillMaxSize(), verticalArrangement = Arrangement.SpaceEvenly
        , horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = { navController.navigate(Screen.Register.route) }) {
            Text(text = "Sign in")
        }
        Button(onClick = { navController.navigate(Screen.Main.route) }) {
            Text(text = "Login")
        }

    }
}