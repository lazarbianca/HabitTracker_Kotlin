package com.example.habittracker.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.example.habittracker.viewmodel.HabitViewModel
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.habittracker.view.screens.HabitCreateScreen
import com.example.habittracker.view.screens.HabitUpdateScreen
import com.example.habittracker.view.screens.MonthCalendarScreen
import java.util.UUID

object HabitsNavigation {
    const val MONTH_CALENDAR_HABIT_LIST = "habitsList"
    const val HABIT_DETAILS = "habitDetails"
    const val HABIT_CREATE = "habitCreate"
    const val HABIT_UPDATE = "habitUpdate"
}

@Composable
fun HabitsNavigation(modifier: Modifier, viewModel: HabitViewModel, navController: NavHostController) {
    Scaffold { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = HabitsNavigation.MONTH_CALENDAR_HABIT_LIST,
            modifier = modifier.padding(innerPadding)
        ) {
            // Define navigation destinations
            composable(HabitsNavigation.MONTH_CALENDAR_HABIT_LIST) {
                MonthCalendarScreen(
                    modifier = modifier,
                    viewModel = viewModel,
                    navController = navController
                )
            }
            composable(HabitsNavigation.HABIT_CREATE) {
                HabitCreateScreen(
                    modifier = modifier,
                    viewModel = viewModel,
                    navController = navController
                )
            }
            composable(
                route = "${HabitsNavigation.HABIT_UPDATE}/{habitId}",
                arguments = listOf(navArgument("habitId") { type = NavType.StringType })
            ) { backStackEntry ->
                val habitId = UUID.fromString(backStackEntry.arguments?.getString("habitId"))
                HabitUpdateScreen(
                    viewModel = viewModel,
                    navController = navController,
                    habitId = habitId
                )
            }
        }
    }
}

