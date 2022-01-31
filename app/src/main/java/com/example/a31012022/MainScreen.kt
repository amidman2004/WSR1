package com.example.a31012022

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import android.graphics.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.a31012022.BottomScreens.HomeScreen
import com.example.a31012022.BottomScreens.ProfileScreen
import com.google.accompanist.pager.ExperimentalPagerApi

data class bottomEl(var name: String,var image:Int)

@ExperimentalFoundationApi
@ExperimentalPagerApi
@SuppressLint("UseCompatLoadingForDrawables")
@Composable
fun MainScreen(MainNav:NavHostController) {
    var topTextField by remember {
        mutableStateOf("")
    }
    val list = arrayListOf<bottomEl>(
        bottomEl("Home",R.drawable.ic_baseline_home_24),
        bottomEl("Cart",R.drawable.ic_baseline_shopping_cart_24),
        bottomEl("Profile",R.drawable.ic_baseline_account_box_24),
        bottomEl("History",R.drawable.ic_baseline_history_24)
    )
    val bottomNav = rememberNavController()
    val bottomNavItems by bottomNav.currentBackStackEntryAsState()
    Scaffold(topBar = {
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier
            .fillMaxWidth()
            .background(Color.Gray)) {
            TextField(value = topTextField, onValueChange = {
                topTextField = it
            },modifier = Modifier
                .background(Color.White)
                .width(300.dp)
                )
            Icon(painter = painterResource(id = R.drawable.home_black),
            contentDescription = null,modifier = Modifier
                    .size(40.dp)
                    .padding(start = 10.dp))
            Icon(painter = painterResource(id = R.drawable.home_black),
                contentDescription = null,modifier = Modifier
                    .size(40.dp)
                    .padding(start = 10.dp))
        }
    },
    bottomBar = {
        BottomNavigation(backgroundColor = Color.Gray) {
            list.forEachIndexed{index: Int, bottomEl: bottomEl ->  
                BottomNavigationItem(
                    selected = bottomEl.name
                    == bottomNavItems?.destination?.route, onClick = {
                        bottomNav.navigate(bottomEl.name)
                    }, label = {Text(text = bottomEl.name)}, icon = {
                        Icon(painter = painterResource(id = bottomEl.image),
                            contentDescription = null)
                    }, unselectedContentColor = Color.White,
                selectedContentColor = Color.Red)
            }
        }
            
    }) {
            NavHost(navController = bottomNav, startDestination = "Home",
                modifier = Modifier.padding(bottom = it.calculateBottomPadding())){
                composable("Home"){ HomeScreen(bottomNav,MainNav)}
                composable("Cart"){ Text(text = "Cart")}
                composable("Profile"){ ProfileScreen(bottomNav)}
                composable("History"){ Text(text = "History")}

            }

        
        

    }
}