package com.example.habittracker.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.habittracker.enums.Category
import com.example.habittracker.view.events.HabitInputFormEvent
import com.example.habittracker.viewmodel.HabitInputFormState
import java.time.LocalDate


@Composable
fun HabitInputForm(modifier: Modifier, state: HabitInputFormState, onEvent: (HabitInputFormEvent) -> Unit) {
//    var title by remember { mutableStateOf("") }
//    var description by remember { mutableStateOf("") }
//    var category by remember { mutableStateOf(Category.UNSELECTED) }
//    var startDate by remember { mutableStateOf(LocalDate.now()) }
//    var goal by remember { mutableStateOf("") }
//    var isForever by remember { mutableStateOf(false) } // State for the "forever" checkbox

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Title
        TextField(
            value = state.title,
            onValueChange = { onEvent(HabitInputFormEvent.TitleChanged(it)) },
            label = { Text("Title") },
            singleLine = true,
            isError = state.titleError != null,
            modifier = Modifier.fillMaxWidth()
        )
        state.titleError?.let{
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        // Description
        TextField(
            value = state.description.orEmpty(),
            onValueChange = { onEvent(HabitInputFormEvent.DescriptionChanged(it)) },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )

        // Category Dropdown
        Text("Category", style = MaterialTheme.typography.labelMedium)
        CategoryDropdownMenu(
            selectedCategory = state.category,
            onCategorySelected = { onEvent(HabitInputFormEvent.CategoryChanged(it)) },
            modifier = Modifier.fillMaxWidth()
        )
        state.categoryError?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

       // Start Date Picker
        DatePickerField(
            date = state.startDate,
            onDateSelected = { onEvent(HabitInputFormEvent.StartDateChanged(it)) },
            label = "Start Date"
        )

        // Goal Checkbox and TextField
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = state.isForever,
                onCheckedChange = { onEvent(HabitInputFormEvent.IsForeverChecked(it)) }
            )
            Text(text = "Forever")
        }
        TextField(
            value = state.goal,
            onValueChange = { onEvent(HabitInputFormEvent.GoalChanged(it)) },
            label = { Text("Goal (days)") },
            singleLine = true,
            enabled = !state.isForever,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = state.goalError != null,
            modifier = Modifier.fillMaxWidth()
        )
        state.goalError?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        // Save Button
        Button(
            onClick = { onEvent(HabitInputFormEvent.Submit) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Habit")
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDropdownMenu(
    selectedCategory: Category,
    onCategorySelected: (Category) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val categories = Category.values() // Get all categories
    val categoryNames = categories.map { it.name.replace("_", " ").lowercase().capitalize() }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        // TextField acting as the dropdown trigger
        TextField(
            value = selectedCategory.name.replace("_", " ").lowercase().capitalize(),
            onValueChange = {},
            readOnly = true, // Prevent typing
            label = { Text("Category") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .menuAnchor() // Required to anchor the dropdown menu
                .fillMaxWidth()
        )

        // Dropdown menu items
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            categories.forEachIndexed { index, category ->
                DropdownMenuItem(
                    text = { Text(categoryNames[index]) },
                    onClick = {
                        onCategorySelected(category) // Update the selected category
                        expanded = false // Close the dropdown
                    }
                )
            }
        }
    }
}

// TODO: make the date selectable
@Composable
fun DatePickerField(date: LocalDate, onDateSelected: (LocalDate) -> Unit, label: String) {
    // Replace with actual date picker implementation
    Text("$label: ${date.toString()}")
}

//@Preview(showBackground = true)
//@Composable
//fun previewHabitInputForm(){
//    HabitInputForm(Modifier)
//}