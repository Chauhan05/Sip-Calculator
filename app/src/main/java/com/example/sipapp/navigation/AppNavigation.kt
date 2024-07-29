package com.example.sipapp.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sipapp.model.SipViewModel
import com.example.sipapp.screens.MainScreen

@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    val viewModel:SipViewModel= viewModel()
    NavHost(navController = navController, startDestination = "main"){
        composable("main"){
            MainScreen(navController,viewModel)
        }
    }
}