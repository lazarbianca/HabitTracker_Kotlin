package com.example.habittracker.viewmodel

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.example.habittracker.enums.Category
import com.example.habittracker.model.Habit
import com.example.habittracker.model.HabitRepository
import java.time.LocalDate
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.habittracker.model.use_case.ValidateCategory
import com.example.habittracker.model.use_case.ValidateGoal
import com.example.habittracker.model.use_case.ValidateTitle
import com.example.habittracker.view.events.HabitInputFormEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.UUID
import kotlin.text.toLong

class HabitViewModel(
    habitRepository: HabitRepository = HabitRepository(),
    private val validateTitle: ValidateTitle = ValidateTitle(),
    private val validateCategory: ValidateCategory = ValidateCategory(),
    private val validateGoal: ValidateGoal = ValidateGoal()
): ViewModel() {

    private val repository = habitRepository
    private var selectedHabit: Habit? = null
        private set

    var state by mutableStateOf(HabitInputFormState())
    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    private val _habits = mutableStateListOf<Habit>()
    private val _selectedDate = mutableStateOf(LocalDate.now())

    val habits: SnapshotStateList<Habit> get() = _habits
    val selectedDate: LocalDate get() = _selectedDate.value

    val activeHabits by derivedStateOf {
        _habits.filter { currentHabit ->
            selectedDate >= currentHabit.startDate &&
                    (currentHabit.goal == null || selectedDate <= currentHabit.startDate.plusDays(currentHabit.goal.toLong()))
        }
    }

    init {
        loadHabits()
    }

    private fun loadHabits(){
        _habits.clear()
        _habits.addAll(repository.getHabits())
    }

    fun setSelectedDate(date: LocalDate){
        _selectedDate.value = date
    }

    fun addHabit(habit: Habit){
        _habits.add(habit)
        repository.addHabit(habit)
    }

    fun deleteHabit(habitId: UUID) {
        _habits.removeAll { it.habitID == habitId }
        repository.deleteHabit(habitId)
    }


//    fun setSelectedHabit(habitId: UUID) {
//        _selectedHabitId.value = habitId
//    }
//
//    fun clearSelectedHabit() {
//        _selectedHabitId.value = null
//    }
fun clearForm() {
    state = HabitInputFormState()
}


    fun onEvent(event: HabitInputFormEvent){
        when(event){
            is HabitInputFormEvent.TitleChanged -> {
                state = state.copy(title = event.title)
            }
            is HabitInputFormEvent.DescriptionChanged -> {
                state = state.copy(description = event.description)
            }
            is HabitInputFormEvent.CategoryChanged -> {
                state = state.copy(category = event.category)
            }
            is HabitInputFormEvent.StartDateChanged -> {
                state = state.copy(startDate = event.startDate)
            }
            is HabitInputFormEvent.GoalChanged -> {
                state = state.copy(goal = event.goal)
            }
            is HabitInputFormEvent.IsForeverChecked -> {
                state = state.copy(isForever = event.isForever)
            }
            is HabitInputFormEvent.IsUpdating -> {
                state = state.copy(isUpdating = event.isUpdating)
            }
            is HabitInputFormEvent.SelectHabit -> {
                val habit = _habits.find { it.habitID.equals(event.habitId) }
                if (habit != null) {
                    state = state.copy(
                        title = habit.title,
                        description = habit.description,
                        category = habit.category,
                        startDate = habit.startDate,
                        goal = habit.goal?.toString() ?: "",
                        isForever = habit.goal == null,
                        isUpdating = true
                    )
                    selectedHabit = habit
                }
            }
            is HabitInputFormEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        val titleResult = validateTitle.execute(state.title)
        val categoryResult = validateCategory.execute(state.category)
        val goalResult = validateGoal.execute(state.goal)

        val hasError = listOf(
            titleResult,
            categoryResult,
            goalResult
        ).any { !it.successful }

        if (hasError) {
            state = state.copy(
                titleError = titleResult.errorMessage,
                categoryError = categoryResult.errorMessage,
                goalError = goalResult.errorMessage
            )
            return
        }

        val habit = Habit(
            title = state.title,
            description = state.description?.takeIf { it.isNotBlank() },
            category = state.category,
            startDate = state.startDate,
            goal = if (state.isForever) null else state.goal.toIntOrNull()
        )

        if (state.isUpdating) {
            _habits.replaceAll { if (it.habitID == selectedHabit?.habitID) habit else it }
            repository.updateHabit(habit)
        } else {
            addHabit(habit)
        }
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
        }
    }


    sealed class ValidationEvent{
        object Success: ValidationEvent()
    }
}