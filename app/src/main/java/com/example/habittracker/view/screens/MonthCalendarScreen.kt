package com.example.habittracker.view.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.habittracker.viewmodel.HabitViewModel
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.habittracker.view.HabitsNavigation
import com.example.habittracker.view.components.DatePickerDocked
import com.example.habittracker.view.components.HabitsList
import com.example.habittracker.view.events.HabitInputFormEvent

@Composable
fun MonthCalendarScreen(modifier: Modifier, viewModel: HabitViewModel, navController: NavController) {
    val state = viewModel.state
    val activeHabits = viewModel.activeHabits
    Box(modifier = modifier.fillMaxSize()) {
        Column(modifier = modifier.fillMaxSize()) {
            DatePickerDocked(
                modifier = Modifier
                    .fillMaxWidth(),
                onDateSelected = { selectedDate ->
                    viewModel.setSelectedDate(selectedDate)
                }
            )

            // HabitsList takes up the remaining space
            HabitsList(
                habits = activeHabits,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                onDelete = { habitId -> viewModel.deleteHabit(habitId) },
                onUpdate = { habitId ->
                    viewModel.onEvent(HabitInputFormEvent.IsUpdating(true))
                    viewModel.onEvent(HabitInputFormEvent.SelectHabit(habitId))
                    navController.navigate("${HabitsNavigation.HABIT_UPDATE}/$habitId")
                }
            )

        }
        FloatingActionButton(
            onClick = {
                navController.navigate("habitCreate")
//                val newHabit = Habit(title = "New SELF Habit", category = Category.SELF)
//                viewModel.addHabit(newHabit)  // This triggers recomposition
            },
            shape = CircleShape,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomEnd)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add Habit")
        }
    }
}


