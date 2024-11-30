package com.example.habittracker.view.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.habittracker.model.Habit
import java.util.UUID

@Composable
fun HabitsList(
    habits: List<Habit>,
    modifier: Modifier,
    onDelete: (UUID) -> Unit,
    onUpdate: (UUID) -> Unit // Add this callback
) {
    LazyColumn(modifier = modifier.padding(16.dp)) {
        items(habits, key = { habit -> habit.habitID }) { habit ->
            HabitItem(
                habit = habit,
                onDelete = onDelete,
                modifier = modifier,
                onUpdate = onUpdate // Pass the callback
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}


// the unique id will be used to identify the composable
// to avoid recomposition of all items when (for ex) order changes