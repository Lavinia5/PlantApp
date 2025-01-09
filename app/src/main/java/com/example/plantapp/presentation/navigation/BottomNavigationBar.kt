package com.example.plantapp.presentation.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.TipsAndUpdates
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.QuestionMark
import androidx.compose.material.icons.outlined.TipsAndUpdates
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTonalElevationEnabled
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.plantapp.navigation.ROUTE_DAILY_TIPS
import com.example.plantapp.navigation.ROUTE_DASHBOARD
import com.example.plantapp.navigation.ROUTE_PLANT_QUIZ
import com.example.plantapp.navigation.ROUTE_PROFILE
import com.example.plantapp.ui.theme.EventAppTheme

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: String // Adăugăm o rută pentru navigare
)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BottomNavigationBar(
    navController: NavController
) {
    var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }

    // Lista de elemente pentru bara de navigare
    val items = listOf(
        BottomNavigationItem(
            title = "Chat",
            selectedIcon = Icons.Filled.Chat,
            unselectedIcon = Icons.Outlined.Chat,
            route = ROUTE_DASHBOARD // Exemplu de rută
        ),
        BottomNavigationItem(
            title = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            route = ROUTE_DASHBOARD
        ),
        BottomNavigationItem(
            title = "Profile",
            selectedIcon = Icons.Filled.Person,
            unselectedIcon = Icons.Outlined.Person,
            route = ROUTE_PROFILE
        ),
        BottomNavigationItem(
            title = "Quiz",
            selectedIcon = Icons.Filled.QuestionMark,
            unselectedIcon = Icons.Outlined.QuestionMark,
            route = ROUTE_PLANT_QUIZ
        ),
        BottomNavigationItem(
            title = "Daily Tips",
            selectedIcon = Icons.Filled.TipsAndUpdates,
            unselectedIcon = Icons.Outlined.TipsAndUpdates,
            route = ROUTE_DAILY_TIPS
        )
    )

    CompositionLocalProvider(LocalTonalElevationEnabled provides false) {
        EventAppTheme(dynamicColor = false) {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 6.dp,
                modifier = Modifier.height(110.dp)
            ) {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedItemIndex == index,
                        onClick = {
                            selectedItemIndex = index
                            navController.navigate(item.route) // Navigăm către ruta definită
                        },
                        label = { Text(text = item.title) },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = Color.Transparent
                        ),
                        icon = {
                            Icon(
                                imageVector = if (index == selectedItemIndex) {
                                    item.selectedIcon
                                } else item.unselectedIcon,
                                contentDescription = item.title,
                                tint = if (index == selectedItemIndex) {
                                    MaterialTheme.colorScheme.primary
                                } else {
                                    MaterialTheme.colorScheme.onBackground
                                },
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    )
                }
            }
        }
    }
}
