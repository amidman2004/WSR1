package com.example.a31012022

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.a31012022.retrofit.Api
import com.example.a31012022.retrofit.Connect
import com.example.a31012022.retrofit.user
import com.example.a31012022.ui.theme.A31012022Theme
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

private lateinit var pref: SharedPreferences
var lOgin:String? = ""
var pWd:String? = ""
class MainActivity : ComponentActivity() {
    @ExperimentalFoundationApi
    @ExperimentalPagerApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            A31012022Theme {
                pref = getSharedPreferences("TABLE", MODE_PRIVATE)
                pWd = pref.getString("pWd","")
                lOgin = pref.getString("lOgin","")
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "AuthScreen" ){
                    composable("AuthScreen"){ AuthScreen(navController)}
                    composable("MainScreen"){ MainScreen(MainNav = navController)}
                    composable("MoreScreen"){ MoreScreen()}
                }
            }
        }
    }
}

@Composable
fun AuthScreen(MainNav: NavHostController) {

    Box(modifier = Modifier.fillMaxSize()){
        val modifier = Modifier
            .align(Alignment.Center)
            .border(1.dp, Color.Black, RoundedCornerShape(10))
            .background(Color.White)
            .clip(RoundedCornerShape(10))
            .width(300.dp)
        
        if (pWd!!.isEmpty())
            SignUpScreen(modifier, MainNav)
        else
            SignInScreen(modifier, MainNav )

    }
}

@Composable
fun SignInScreen(modifier: Modifier,MainNav:NavHostController) {
    var login by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally){
        Spacer(modifier = Modifier.size(30.dp))
        Text(text = "Enter your Login and Password")
        OutlinedTextField(value = login, onValueChange = {
            login = it
        }, label = { Text(text = "Password")},modifier = Modifier.width(250.dp))
        Spacer(modifier = Modifier.size(20.dp))
        OutlinedTextField(value = password, onValueChange = {
            password = it
        }, label = { Text(text = "Password")}, visualTransformation = PasswordVisualTransformation(),modifier = Modifier.width(250.dp))
        Spacer(modifier = Modifier.size(30.dp))
        Button(onClick = {
            Api().Auth(user(login,password)){
            val edit = pref.edit()
            edit.putString("lOgin",login)
            edit.putString("pWd",password)
            edit.apply()
            MainNav.navigate("MainScreen")
        }
        }, colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Blue,
            contentColor = Color.White
        ), modifier = Modifier.size(200.dp,65.dp)) {
            Text(text = "SIGN IN")

        }
    }
}

@Composable
fun SignUpScreen(modifier: Modifier,MainNav: NavHostController) {
    var login by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally){
        Spacer(modifier = Modifier.size(30.dp))
        Text(text = "SIGN UP")
        OutlinedTextField(value = login, onValueChange = {
            login = it
        }, label = { Text(text = "Password")},modifier = Modifier.width(250.dp))
        Spacer(modifier = Modifier.size(20.dp))
        OutlinedTextField(value = password, onValueChange = {
            password = it
        }, label = { Text(text = "Password")}, visualTransformation = PasswordVisualTransformation(),modifier = Modifier.width(250.dp))
        Spacer(modifier = Modifier.size(30.dp))
        Button(onClick = {
            Api().Auth(user(login,password)){
                val edit = pref.edit()
                edit.putString("lOgin",login)
                edit.putString("pWd",password)
                edit.apply()
                MainNav.navigate("MainScreen")
            }
        }, colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Blue,
            contentColor = Color.White
        ), modifier = Modifier.size(200.dp,65.dp)) {
            Text(text = "SIGN UP")
        }
    }
}