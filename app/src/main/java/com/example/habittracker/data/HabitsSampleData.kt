package com.example.habittracker.data

import androidx.compose.runtime.mutableStateListOf
import com.example.habittracker.enums.Category
import com.example.habittracker.model.Habit
import java.time.LocalDate

object HabitsSampleData {
    val habitsSample = mutableStateListOf(
        Habit(
            title = "Quit Smoking",
            category = Category.QUIT_BAD_HABIT
        ),
        Habit(
            title = "Practice React Native",
            description = "See study plan on Discord",
            category = Category.ACADEMIC,
            startDate = LocalDate.of(2024, 11, 27),
            goal = 365
        ),
        Habit(
            title = "Exercise Daily",
            description = "Morning jog or gym session",
            category = Category.HEALTH,
            startDate = LocalDate.of(2024, 11, 1),
            goal = 30
        ),
        Habit(
            title = "Read Books",
            description = "Read one chapter daily",
            category = Category.SELF,
            startDate = LocalDate.of(2024, 11, 15),
            goal = 50
        ),
        Habit(
            title = "Meditate",
            description = "Daily meditation session",
            category = Category.SELF,
            startDate = LocalDate.of(2024, 11, 20), goal = 60
        ),
        Habit(
            title = "Visit Grandparents",
            description = "Bring them a nice gift",
            category = Category.SOCIAL,
            startDate = LocalDate.of(2024, 12, 1),
            goal = 90
        ),
        Habit(
            title = "Save More Money",
            description = "Save at least $80 daily",
            category = Category.FINANCE,
            startDate = LocalDate.of(2024, 11, 27),
            goal = 365
        )
    )
}