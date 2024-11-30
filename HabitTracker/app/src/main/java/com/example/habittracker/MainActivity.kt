package com.example.habittracker

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.habittracker.ui.theme.HabitTrackerTheme
import com.example.habittracker.view.HabitsNavigation
import com.example.habittracker.viewmodel.HabitViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            HabitTrackerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val habitsViewModel = viewModel<HabitViewModel>()
                    val state = habitsViewModel.state
                    val context = LocalContext.current
                    val navController = rememberNavController()
                    LaunchedEffect(key1 = context) {
                        habitsViewModel.validationEvents.collect{ event ->
                            when(event){
                                is HabitViewModel.ValidationEvent.Success -> {
                                    Toast.makeText(
                                        context,
                                        "Habit added successfully",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    navController.popBackStack()
                                }
                            }
                        }
                    }
                    HabitsNavigation(
                        modifier = Modifier.padding(innerPadding),
                        viewModel = habitsViewModel,
                        navController = navController
                    )

                }

            }

        }
    }
}
