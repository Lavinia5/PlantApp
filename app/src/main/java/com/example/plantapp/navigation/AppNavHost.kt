package com.example.plantapp.navigation

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.plantapp.chatgpt.SearchScreen
import com.example.plantapp.dateplants.PlantDetailScreen
import com.example.plantapp.dateplants.PlantSearchScreen
import com.example.plantapp.getalarms.WateringReminderScreen

import com.example.plantapp.onboarding.OnboardingViewModel
import com.example.plantapp.presentation.DailyTipsScreen
import com.example.plantapp.presentation.LoginScreen
import com.example.plantapp.presentation.PlantQuizScreen
import com.example.plantapp.presentation.ProfileScreen
import com.example.plantapp.presentation.SignUpScreen
import com.example.plantapp.presentation.navigation.BottomNavigationBar
import com.example.plantapp.splash.SplashScreen
import com.example.plantapp.ui.theme.EventAppTheme

// Definirea constantelor pentru rute

const val ROUTE_GOAL = "goal" // Ruta nouă

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = ROUTE_SPLASH // Poți schimba aici ruta dorită
) {
    Scaffold(
        bottomBar = {
            val currentRoute = navController.currentBackStackEntryAsState()?.value?.destination?.route
            if (currentRoute in listOf(ROUTE_PROFILE, ROUTE_GOAL, ROUTE_DAILY_TIPS, ROUTE_PLANT_QUIZ, ROUTE_WATERING_REMINDER)) {
                BottomNavigationBar(navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = modifier.padding(innerPadding)
        ) {
            // Ruta Splash
            composable(ROUTE_SPLASH) {
                SplashScreen(navController = navController)
            }
            // Ruta Login
            composable(ROUTE_LOGIN) {
                LoginScreen(
                    navController = navController,
                    onSignInClick = { navController.navigate(ROUTE_PROFILE) },
                    onForgotPasswordClick = { navController.navigate(ROUTE_SIGNUP) }
                )
            }
            // Ruta Signup
            composable(ROUTE_SIGNUP) {
                SignUpScreen(
                    onboardingViewModel = hiltViewModel(),
                    navController = navController,
                    onSignUpClick = { navController.navigate(ROUTE_GOAL) }
                )
            }
            // Ruta Profil
            composable(ROUTE_PROFILE) {
                ProfileScreen(navController = navController)
            }
            // Ruta Goal
            composable(ROUTE_GOAL) {
                GoalScreen(navController = navController)
            }
            // Ruta Daily Tips
            composable(ROUTE_DAILY_TIPS) {
                DailyTipsScreen(navController = navController)
            }
            // Ruta Quiz
            composable(ROUTE_PLANT_QUIZ) {
                PlantQuizScreen(navController = navController)
            }
            // Ruta Watering Reminder
            composable(ROUTE_WATERING_REMINDER) {
                WateringReminderScreen()
            }
            // Ruta Chat
            composable(ROUTE_CHAT) {
                ChatScreen()
            }
            // Ruta Căutare
            composable(ROUTE_SEARCH) {
                PlantSearchScreen(navController = navController)
            }
            // Ruta Detalii Plantă
            composable(
                route = ROUTE_PLANT_DETAILS,
                arguments = listOf(navArgument("plantName") { type = NavType.StringType })
            ) { backStackEntry ->
                val plantName = backStackEntry.arguments?.getString("plantName") ?: ""
                PlantDetailScreen(plantName = plantName)
            }
        }
    }
}


// Definiție GoalScreen
@Composable
fun GoalScreen(navController: NavHostController) {
    // UI-ul specific pentru GoalScreen
}
@Composable
fun ChatScreen() {
    // Aici implementezi interfața și funcționalitatea pentru ChatGPT
    Text(text = "Welcome to Chat!")
}
