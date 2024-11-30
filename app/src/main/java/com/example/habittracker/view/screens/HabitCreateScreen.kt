package com.example.habittracker.view.screens

import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import com.example.habittracker.view.components.HabitInputForm
import com.example.habittracker.view.HabitsNavigation
import com.example.habittracker.viewmodel.HabitViewModel

@Composable
fun HabitCreateScreen(modifier: Modifier, viewModel: HabitViewModel, navController: NavController) {
    val state = viewModel.state
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.clearForm()
        viewModel.validationEvents.collect { event ->
            when (event) {
                is HabitViewModel.ValidationEvent.Success -> {
                    Toast.makeText(context, "Habit added successfully", Toast.LENGTH_LONG).show()
                    navController.popBackStack() // navigate back
                }
            }
        }
    }

    Scaffold(
        topBar = {
            HabitTopAppBar(
                title = "Add a new Habit",
                onNavigationClick = { navController.navigate(HabitsNavigation.MONTH_CALENDAR_HABIT_LIST) }
            )
        }
    ) { innerPadding ->
        HabitInputForm(
            modifier = modifier.padding(innerPadding),
            state = state,
            onEvent = viewModel::onEvent
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitTopAppBar(
    title: String,
    onNavigationClick: () -> Unit
) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Text(
                title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            IconButton(onClick = onNavigationClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Navigate Back"
                )
            }
        }
    )
}
