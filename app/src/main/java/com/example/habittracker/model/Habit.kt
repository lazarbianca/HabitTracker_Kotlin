package com.example.habittracker.model

import com.example.habittracker.enums.Category
import java.time.LocalDate
import java.util.Date
import java.util.UUID

data class Habit(
    val habitID:  UUID = UUID.randomUUID(),
    val title: String,
    val description: String? = null,
    val category: Category,
    val startDate: LocalDate = LocalDate.now(),
    val goal: Int? = null,  // null = "forever"
    val completionData: List<LocalDate> = emptyList()  // dates when habit was completed
)
