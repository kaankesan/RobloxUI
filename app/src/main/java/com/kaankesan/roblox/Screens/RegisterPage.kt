package com.kaankesan.roblox.Screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.room.Room
import com.kaankesan.roblox.R
import com.kaankesan.roblox.database.User
import com.kaankesan.roblox.database.UserDatabase
import com.kaankesan.roblox.objects.Screen
import com.kaankesan.roblox.viewModel.SharedViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

@Composable
fun RegisterPage(navController: NavController){
    Scaffold(
        topBar = {TopBar(navController = navController)},
        content = { RegisterPageContent(navController = navController)}
    )
}

@Composable
fun TopBar(navController: NavController){
    TopAppBar(
        title = {Text(text = "Sign in")},
        navigationIcon = {
            IconButton(onClick = {navController.navigate(Screen.Opening.route)}) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_cancel_24),
                    contentDescription = "")
            }
        }
    )
}

@Composable
fun RegisterPageContent(navController: NavController){
    val viewModel: SharedViewModel = viewModel()
    val context = LocalContext.current
    val name = remember{ mutableStateOf("")}
    val mail = remember{ mutableStateOf("")}
    val password = remember{ mutableStateOf("")}
    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "Create an account")
        OutlinedTextField(value = name.value, onValueChange = {name.value = it}, label = { Text(text = "Name")})
        OutlinedTextField(value = mail.value, onValueChange = {mail.value = it}, label = { Text(text = "Mail")})
        OutlinedTextField(value = password.value, onValueChange = {password.value = it}, label = { Text(text = "Password")})
        Button(onClick = {
            if(name.value.equals("")||mail.value.equals("")||password.value.equals("")){
                Toast.makeText(context,"Enter name, mail and password",Toast.LENGTH_SHORT).show()
            }else{
                val db = Room.databaseBuilder(
                    context,
                    UserDatabase::class.java,"user"
                ).build()
                val userDao = db.userDao()
                val uuid: String = UUID.randomUUID().toString()
                val newUser = User(name = name.value, mail = mail.value, password = password.value, id = uuid)
                GlobalScope.launch(Dispatchers.Main) {
                    userDao.insertOne(newUser)
                }
                navController.navigate("Main/" + name.value)
            }
        }) {
            Text(text = "Create Account")
        }
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Sign in")
        }
    }
}