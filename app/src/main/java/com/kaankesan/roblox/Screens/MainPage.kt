package com.kaankesan.roblox.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.room.Room
import com.kaankesan.roblox.Enum.SearchWidgetState
import com.kaankesan.roblox.R
import com.kaankesan.roblox.database.UserDatabase
import com.kaankesan.roblox.objects.Screen
import com.kaankesan.roblox.viewModel.SharedViewModel
import kotlinx.coroutines.*


@Composable
fun MainPage(navController: NavController,viewModel: SharedViewModel){

    val searchWidgetState by viewModel.searchWidgetState
    val searchTextState by viewModel.searchTextState

    Scaffold(
        topBar = {
            MainPageTopAppBar(
                navController = navController,
                searchTextState = searchTextState,
                searchWidgetState = searchWidgetState,
                viewModel = viewModel,
                onTextChange = {
                    viewModel.updateSearchTextState(newValue = it)
                },
                onCloseClicked = {
                    viewModel.updateSearchTextState(newValue = "")
                    viewModel.updateSearchWidgetState(newValue = SearchWidgetState.CLOSED)
                },
                onSearchClicked = {

                },
                onSearchTriggered = {
                    viewModel.updateSearchWidgetState(newValue = SearchWidgetState.OPENED)
                }
            ) },
        content = {
                  if(viewModel.searchWidgetState.value == SearchWidgetState.CLOSED){
                      MainPageContent(navController = navController)
                  }else{
                      SearchContent(searchTextState)
                  }
        },
        bottomBar = {}
    )

}


@Composable
fun MainPageTopAppBar(
    navController: NavController,
    searchWidgetState: SearchWidgetState,
    searchTextState : String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
    onSearchTriggered: () -> Unit,
    viewModel: SharedViewModel
){
    when(searchWidgetState){
        SearchWidgetState.CLOSED -> {
            MainTopBar(navController = navController, onSearchClicked = onSearchTriggered)
        }
        SearchWidgetState.OPENED -> {
            SearchAppBar(
                text = searchTextState,
                onTextChange = onTextChange,
                onCloseClicked = onCloseClicked,
                onSearchClicked = onSearchClicked,
                viewModel = viewModel
            )
        }
    }
    
}


@Composable
fun MainPageContent(navController: NavController){
    val viewModel:SharedViewModel = viewModel()
    val name : String
    val context = LocalContext.current.applicationContext
    val db = Room.databaseBuilder(
        context,
        UserDatabase::class.java,"user"
    ).build()
    val userDao = db.userDao()
    navController.currentBackStackEntry.let {
         name = it?.arguments!!.getString("name").toString()
    }

    LaunchedEffect(key1 = true){
        viewModel.users = userDao.getAll().toMutableList()
    }


    Surface() {
        Column(Modifier.fillMaxSize()) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)) {
                Row(Modifier.fillMaxSize()) {
                    Image(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_baseline_verified_user_24),
                        contentDescription = "",
                        Modifier
                            .size(64.dp)
                            .padding(start = 16.dp, top = 16.dp)
                    )
                    Text(text = name, fontSize = 32.sp, modifier = Modifier.padding(start = 16.dp, top = 16.dp))
                }

            }

            Row(Modifier.height(64.dp)) {
                Text(text = "All Users",Modifier.padding(start = 8.dp), fontSize = 32.sp)
                Text(text = "(${viewModel.users.size})",Modifier.padding(start = 8.dp), fontSize = 32.sp)
            }

            LazyRow( modifier = Modifier
                .fillMaxSize()){
                items(viewModel.users){ user ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(64.dp)
                            .wrapContentHeight(Alignment.CenterVertically)
                            .padding(6.dp),
                        elevation = 8.dp,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(text = user.name, fontSize = 32.sp)
                    }
                }
            }

        }

    }

}

@Composable
fun MainTopBar(navController: NavController,onSearchClicked: () -> Unit){
    TopAppBar(
        title = { Text(text = "Home")},
        actions = {
            IconButton(
                onClick = {
                    onSearchClicked()
                }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_search_24),
                    contentDescription = "",
                    Modifier.size(height = 32.dp, width = 32.dp)
                )
            }

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
fun SearchAppBar(
    text:String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
    viewModel: SharedViewModel
){
    val context = LocalContext.current.applicationContext
    val db = Room.databaseBuilder(
        context,
        UserDatabase::class.java,"user"
    ).build()
    val userDao = db.userDao()
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp),
        elevation = AppBarDefaults.TopAppBarElevation
    ) {
        TextField(
            value = text,
            onValueChange = {
                onTextChange(it)
            },
            placeholder = {
                Text(modifier = Modifier
                    .alpha(ContentAlpha.medium),
                    text = "Search here..."
                )
            },
            textStyle = TextStyle(
                fontSize = MaterialTheme.typography.subtitle1.fontSize
            ),
            singleLine = true,
            leadingIcon = {
                IconButton(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium),
                    onClick = {}
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "",
                    )
                }
            },
            trailingIcon = {
                IconButton(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium),
                    onClick = {
                        if(text.isNotEmpty()){
                            onTextChange("")
                        }else{
                            onCloseClicked()
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "",
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClicked(text)
                    CoroutineScope(Dispatchers.Main).launch {
                        viewModel.showableUser = userDao.getFromSearch(text).toMutableList()
                    }
                }
            )
        )
    }
}

@Composable
fun SearchContent(text: String){
    val viewModel:SharedViewModel = viewModel()
    val context = LocalContext.current.applicationContext
    val db = Room.databaseBuilder(
        context,
        UserDatabase::class.java,"user"
    ).build()
    val userDao = db.userDao()

    LaunchedEffect(key1 = true){
        viewModel.showableUser = userDao.getFromSearch(text).toMutableList()
    }

    LazyColumn(modifier = Modifier.fillMaxSize()){
        items(viewModel.showableUser){ user ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .wrapContentHeight(Alignment.CenterVertically)
                    .padding(6.dp),
                elevation = 8.dp,
                shape = RoundedCornerShape(8.dp)
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                        .wrapContentHeight(Alignment.CenterVertically)
                        .padding(6.dp),
                    elevation = 8.dp,
                    shape = RoundedCornerShape(8.dp)
                ) {
                        Text(text = user.name, fontSize = 32.sp)
                }
            }
        }
    }
}

