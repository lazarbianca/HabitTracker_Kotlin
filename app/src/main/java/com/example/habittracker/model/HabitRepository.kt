package com.example.habittracker.model

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.habittracker.data.HabitsSampleData
import java.time.LocalDate
import java.util.UUID

class HabitRepository {
    private val habits: MutableList<Habit> = HabitsSampleData.habitsSample

    fun getHabits(): List<Habit>{
        return habits
    }

    fun addHabit(habit: Habit){
        habits.add(habit)
    }

   fun deleteHabit(habitId: UUID) {
       habits.removeAll { it.habitID == habitId }
   }

    fun updateHabit(updatedHabit: Habit) {
        val index = habits.indexOfFirst { it.habitID == updatedHabit.habitID }
        if (index != -1) {
            habits[index] = updatedHabit
        }
    }
}