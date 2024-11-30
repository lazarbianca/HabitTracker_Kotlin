package com.example.habittracker.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.habittracker.enums.Category
import java.time.LocalDate

data class HabitInputFormState(
    var title: String = "",
    var titleError: String? = null,

    var description: String? = null,

    var category: Category = Category.UNSELECTED,
    var categoryError: String? = null,

    var startDate: LocalDate = LocalDate.now(),

    var goal: String = "",
    var goalError: String? = null,

    var isForever: Boolean = false,
    val isUpdating: Boolean = false,
)
