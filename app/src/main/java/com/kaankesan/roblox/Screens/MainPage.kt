package com.kaankesan.roblox.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.navArgument
import androidx.room.Room
import com.kaankesan.roblox.R
import com.kaankesan.roblox.database.User
import com.kaankesan.roblox.database.UserDatabase
import com.kaankesan.roblox.objects.Screen
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


@Composable
fun MainPage(navController: NavController){
    Scaffold(
        topBar = { MainPageTopAppBar(navController = navController)},
        content = { MainPageContent(navController = navController)},
        bottomBar = {}
    )
}

@Composable
fun MainPageTopAppBar(navController: NavController){
    TopAppBar(
        title = { Text(text = "Home")},
        actions = {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_search_24),
                contentDescription = "",
                Modifier.size(height = 32.dp, width = 32.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))

            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = "",
                Modifier.size(height = 32.dp, width = 32.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))

            IconButton(onClick = { navController.navigate(Screen.Opening.route) }) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "",
                    Modifier.size(height = 32.dp, width = 32.dp)
                )
            }
        }
    )
}


@Composable
fun MainPageContent(navController: NavController){
    val uuid : String
    var user : User
    val context = LocalContext.current.applicationContext
    val db = Room.databaseBuilder(
        context,
        UserDatabase::class.java,"user"
    ).build()
    val userDao = db.userDao()
    navController.currentBackStackEntry.let {
         uuid = it?.id.toString()
        val getUser: suspend () -> Unit = {
            user = userDao.getOne(uuid = uuid)
        }
    }

    Surface() {
        Column(modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)) {

        }
    }

}


