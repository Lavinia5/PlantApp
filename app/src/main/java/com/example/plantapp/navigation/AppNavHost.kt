package com.example.plantapp.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

import com.example.plantapp.onboarding.OnboardingViewModel
import com.example.plantapp.presentation.DailyTipsScreen
import com.example.plantapp.presentation.LoginScreen
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
    startDestination: String = ROUTE_SPLASH
) {
    // Obține ViewModel-ul pentru onboarding
    val onboardingViewModel: OnboardingViewModel = hiltViewModel()

    // Monitorizează ruta curentă
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    // Scaffold pentru layout-ul principal
    Scaffold(
        bottomBar = {
            // Afișează BottomNavigationBar doar pe anumite ecrane
            if (currentRoute == ROUTE_PROFILE || currentRoute == ROUTE_GOAL) {
                BottomNavigationBar(navController)
            }
        }
    ) { innerPadding ->
        // Host pentru navigare
        NavHost(
            modifier = modifier.padding(innerPadding),
            navController = navController,
            startDestination = startDestination
        ) {
            // Ruta Splash
            composable(ROUTE_SPLASH) {
                SplashScreen(
                    navController = navController
                )
            }

            // Ruta Login
            composable(ROUTE_LOGIN) {
                LoginScreen(
                    navController = navController,
                    onSignInClick = {
                        // Navigare exemplu
                        navController.navigate(ROUTE_PROFILE)
                    },
                    onForgotPasswordClick = {
                        // Navigare exemplu
                        navController.navigate(ROUTE_SIGNUP)
                    }
                )
            }

            // Ruta Signup
            composable(ROUTE_SIGNUP) {
                SignUpScreen(
                    onboardingViewModel = onboardingViewModel,
                    navController = navController,
                    onSignUpClick = {
                        // Navigare exemplu
                        navController.navigate(ROUTE_GOAL)
                    }
                )
            }

            // Ruta Profil
            composable(ROUTE_PROFILE) {
                ProfileScreen(
                    navController = navController
                )
            }

            // Ruta Goal (nouă)
            composable(ROUTE_GOAL) {
                GoalScreen(navController = navController)
            }
            // Ruta Daily Tips
            composable(ROUTE_DAILY_TIPS) {
                DailyTipsScreen(navController = navController)
            }


        }
    }
}

// Definiție GoalScreen
@Composable
fun GoalScreen(navController: NavHostController) {
    // UI-ul specific pentru GoalScreen
}
