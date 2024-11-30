package com.example.habittracker.view.events

import com.example.habittracker.enums.Category
import java.time.LocalDate
import java.util.UUID

sealed class HabitInputFormEvent {
    data class TitleChanged(val title: String): HabitInputFormEvent()
    data class DescriptionChanged(val description: String): HabitInputFormEvent()
    data class CategoryChanged(val category: Category): HabitInputFormEvent()
    data class StartDateChanged(val startDate: LocalDate): HabitInputFormEvent()
    data class GoalChanged(val goal: String): HabitInputFormEvent()
    data class IsForeverChecked(val isForever: Boolean): HabitInputFormEvent()
    data class IsUpdating(val isUpdating: Boolean): HabitInputFormEvent()
    data class SelectHabit(val habitId: UUID) : HabitInputFormEvent()

    object Submit: HabitInputFormEvent()
}