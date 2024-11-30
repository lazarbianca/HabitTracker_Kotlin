package com.example.habittracker.view.screens

import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.habittracker.view.HabitsNavigation
import com.example.habittracker.view.components.HabitInputForm
import com.example.habittracker.view.events.HabitInputFormEvent
import com.example.habittracker.viewmodel.HabitViewModel
import java.util.UUID

@Composable
fun HabitUpdateScreen(
    modifier: Modifier = Modifier,
    viewModel: HabitViewModel,
    navController: NavController,
    habitId: UUID
) {
    val context = LocalContext.current

    // Fetch the habit data based on habitId
    val habit = viewModel.habits.find { it.habitID == habitId }

    // Prepopulate the state with the habit's data
    LaunchedEffect(habit) {
        habit?.let {
            viewModel.onEvent(HabitInputFormEvent.TitleChanged(habit.title))
            viewModel.onEvent(HabitInputFormEvent.DescriptionChanged(habit.description ?: ""))
            viewModel.onEvent(HabitInputFormEvent.CategoryChanged(habit.category))
            viewModel.onEvent(HabitInputFormEvent.StartDateChanged(habit.startDate))
            viewModel.onEvent(HabitInputFormEvent.GoalChanged(habit.goal?.toString() ?: ""))
        }
    }

    LaunchedEffect(Unit) {
        viewModel.validationEvents.collect { event ->
            when (event) {
                is HabitViewModel.ValidationEvent.Success -> {
                    Toast.makeText(context, "Habit updated successfully", Toast.LENGTH_LONG).show()
                    navController.popBackStack() // Navigate back
                }
            }
        }
    }

    Scaffold(
        topBar = {
            HabitTopAppBar(
                title = "Update Habit",
                onNavigationClick = { navController.navigate(HabitsNavigation.MONTH_CALENDAR_HABIT_LIST) }
            )
        }
    ) { innerPadding ->
        HabitInputForm(
            modifier = Modifier.padding(innerPadding),
            state = viewModel.state,
            onEvent = viewModel::onEvent
        )
    }
}
