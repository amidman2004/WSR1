package com.example.a31012022.BottomScreens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.a31012022.R

data class dish(var name:String,var price:String,var image:Int)
var disH = dish("","",0)
@ExperimentalFoundationApi
@Composable
fun DishScreen(bottomNav: NavHostController,MainNav:NavHostController) {
    val dishNav = rememberNavController()
    NavHost(navController = dishNav, startDestination = "DishList"){
        composable("DishList"){ DishList(dishNav) }
        composable("SelectedDish"){ SelectedDish(dishNav,bottomNav,MainNav)}
    }
}

@ExperimentalFoundationApi
@Composable
fun DishList(dishNav:NavHostController) {
    val dishList = arrayListOf<dish>(
        dish("EDA","100 RUB", R.drawable.ic_baseline_account_box_24),
        dish("EDA","100 RUB", R.drawable.ic_baseline_account_box_24),
        dish("EDA","100 RUB", R.drawable.ic_baseline_account_box_24),
        dish("EDA","100 RUB", R.drawable.ic_baseline_account_box_24),
        dish("EDA","100 RUB", R.drawable.ic_baseline_account_box_24),
        dish("EDA","100 RUB", R.drawable.ic_baseline_account_box_24),
        dish("EDA","100 RUB", R.drawable.ic_baseline_account_box_24),
        dish("EDA","100 RUB", R.drawable.ic_baseline_account_box_24),
        dish("EDA","100 RUB", R.drawable.ic_baseline_account_box_24),
        dish("EDA","100 RUB", R.drawable.ic_baseline_account_box_24),
        dish("EDA","100 RUB", R.drawable.ic_baseline_account_box_24),
        dish("EDA","100 RUB", R.drawable.ic_baseline_account_box_24),
        dish("EDA","100 RUB", R.drawable.ic_baseline_account_box_24),
        dish("EDA","100 RUB", R.drawable.ic_baseline_account_box_24),
        dish("EDA","100 RUB", R.drawable.ic_baseline_account_box_24),
        dish("EDA","100 RUB", R.drawable.ic_baseline_account_box_24),
    )
    LazyVerticalGrid(cells = GridCells.Fixed(2),modifier= Modifier
        .fillMaxSize()
        .background(Color.White)){
        itemsIndexed(dishList){index: Int, item: dish ->
            DishItem(dishNav = dishNav, dish = item)
        }
    }
}

@Composable
fun DishItem(dishNav: NavHostController,dish: dish) {
    Column(modifier = Modifier
        .padding(20.dp)
        .clip(RoundedCornerShape(10))
        .background(Color.White)
        .border(1.dp, Color.Black, RoundedCornerShape(10))
        .clickable(
            onClick = {
                disH = dish
                dishNav.navigate("SelectedDish")
            }
        ))
    {
        Spacer(modifier = Modifier.height(10.dp))
        Icon(painter = painterResource(id = dish.image),
            contentDescription = null, Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(10))
                .border(1.dp, Color.Black, RoundedCornerShape(10)))
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = dish.name, fontSize = 14.sp)
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = dish.price,fontSize = 14.sp)
        Spacer(modifier = Modifier.height(10.dp))

    }
}

@Composable
fun SelectedDish(dishNav: NavHostController,bottomNav:NavHostController,MainNav: NavHostController) {
    var quantity by remember {
        mutableStateOf(0)
    }
    var checker by remember {
        mutableStateOf(false)
    }
    @Composable
    fun StartButtons(modifier: Modifier) {
        Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(id = R.drawable.ic_baseline_account_box_24),
                contentDescription = null,
                Modifier
                    .size(30.dp)
                    .clickable(
                        onClick = {
                            if (quantity > 0)
                                quantity -= 1
                        }
                    ))
            Text(text = quantity.toString(), fontSize = 24.sp)
            Icon(painter = painterResource(id = R.drawable.ic_baseline_account_box_24),
                contentDescription = null,
                Modifier
                    .size(30.dp)
                    .clickable(
                        onClick = {
                            quantity += 1
                        }
                    ))
            Button(onClick = {checker = true}, colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Red,
                contentColor = Color.White
            )) {
                Text(text = "Add to Cart")
                Icon(painter = painterResource(id = R.drawable.ic_baseline_account_box_24),
                    contentDescription = null)
            }
        }
    }

    @Composable
    fun EndButtons(modifier: Modifier) {
        Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = {dishNav.navigate("DishList")}, colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Red,
                contentColor = Color.White
            )) {
                Text(text = "Continue buy")
            }
            Spacer(modifier = Modifier.height(5.dp))
            Button(onClick = {bottomNav.navigate("Cart")}, colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Red,
                contentColor = Color.White
            )) {
                Text(text = "Go to Cart")
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()){
        Box(modifier = Modifier
            .clip(RoundedCornerShape(10))
            .background(Color.White)
            .size(300.dp)
            .border(1.dp, Color.Black, RoundedCornerShape(10))
            .align(Alignment.Center)
            ) {
            IconButton(onClick = {dishNav.navigate("DishList")},
                modifier = Modifier.padding(top = 35.dp, start = 40.dp)) {
                Icon(painter = painterResource(id = R.drawable.ic_baseline_account_box_24),
                    contentDescription = null)
            }
            TextButton(onClick = {MainNav.navigate("MoreScreen")},
                modifier = Modifier
                    .padding(top = 35.dp, end = 40.dp)
                    .align(Alignment.TopEnd)) {
                Text(text = "More", color = Color.Black)
            }
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 80.dp),
                verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                Icon(painter = painterResource(id = R.drawable.ic_baseline_account_box_24),
                    contentDescription = null, modifier = Modifier
                        .size(80.dp)
                        .clip(
                            RoundedCornerShape(10)
                        )
                        .border(1.dp, Color.Black, RoundedCornerShape(10)), tint = Color.Black)
                Spacer(modifier = Modifier.width(40.dp))
                Column() {
                    Text(text = disH.name)
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = disH.price)
                }
            }
            val modifier = Modifier.align(Alignment.BottomCenter)
            if (!checker)
                StartButtons(modifier = modifier)
            else
                EndButtons(modifier)
        }
    }
}