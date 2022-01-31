package com.example.a31012022.BottomScreens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@ExperimentalPagerApi
@Composable
fun HomeScreen(bottomNav:NavHostController,MainNav:NavHostController) {
    
    val tabList = arrayListOf(
        "Food","Snacks","Drinks","Sauces")
    val pagerState = rememberPagerState(pageCount = tabList.size)
    val scope = rememberCoroutineScope()
    Scaffold(topBar = {
        TabRow(selectedTabIndex = 0, backgroundColor = Color.Gray,
            modifier = Modifier.fillMaxWidth(),
        indicator = {
            TabRowDefaults.Indicator(
            color = Color.Red,
                modifier = Modifier.pagerTabIndicatorOffset(pagerState,it)
        )}
        ) {
            tabList.forEachIndexed{index:Int,name:String ->
                Tab(selected = pagerState.currentPage == index,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                text = { Text(text = name)},
                unselectedContentColor = Color.White,
                selectedContentColor = Color.Red)
            }
        }
    }) {
        HorizontalPager(state = pagerState) {
            when(it){
                0 -> {
                    DishScreen(bottomNav,MainNav)
                }
                else -> Box(modifier = Modifier.fillMaxSize())
            }
        }

    }
}


