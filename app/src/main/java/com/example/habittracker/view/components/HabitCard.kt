package com.example.habittracker.view.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.habittracker.R
import com.example.habittracker.enums.Category
import com.example.habittracker.model.Habit
import java.util.UUID

@Composable
fun HabitItem(
    habit: Habit,
    modifier: Modifier,
    onDelete: (UUID) -> Unit,
    onUpdate: (UUID) -> Unit // Callback for navigating to the update page
) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    // Show the delete confirmation dialog when the trash icon is clicked
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete Habit") },
            text = { Text("Are you sure you want to delete this habit?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDelete(habit.habitID)  // confirm
                        showDeleteDialog = false  // close dialog
                    }
                ) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDeleteDialog = false } // close dialog on dismiss
                ) {
                    Text("No")
                }
            }
        )
    }

    SwipeBox(
        onDelete = { habitId ->
            onDelete(habitId)
        },
        modifier = modifier,
        habitID = habit.habitID
    ) {
        HabitCard(
            habit = habit,
            onUpdate = { onUpdate(habit.habitID) },
            onDeleteClick = { showDeleteDialog = true } // Set the dialog visibility when trash icon is clicked
        )
    }
}

@Composable
fun HabitCard(
    habit: Habit,
    onUpdate: () -> Unit, // Callback for update button
    onDeleteClick: () -> Unit // Callback for delete button
) {
    val imageResource = when (habit.category) {
        Category.ACADEMIC -> R.drawable.education_svgrepo_com
        Category.WORK -> R.drawable.work_case_svgrepo_com
        Category.SELF -> R.drawable.meditation_svgrepo_com
        Category.HEALTH -> R.drawable.health_svgrepo_com
        Category.SOCIAL -> R.drawable.cheers_svgrepo_com
        Category.FINANCE -> R.drawable.cash_svgrepo_com
        Category.QUIT_BAD_HABIT -> R.drawable.deny_cancel_reject_block_blocked_svgrepo_com
        Category.UNSELECTED -> R.drawable.image_square_svgrepo_com
    }

    val imageModifier = Modifier
        .size(100.dp)
        .background(Color.LightGray)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(imageResource),
                contentDescription = habit.title + " " + habit.category,
                contentScale = ContentScale.Fit,
                modifier = imageModifier
            )
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f)
            ) {
                Text(text = habit.title, style = MaterialTheme.typography.titleMedium)
                habit.description?.let {
                    Text(text = it, style = MaterialTheme.typography.bodySmall)
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Update button (edit icon)
                IconButton(
                    onClick = onUpdate, // Trigger the update action
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "Update Habit",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

                // Trash button (delete icon)
                IconButton(
                    onClick = onDeleteClick, // Show delete confirmation dialog
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Delete, // Use a delete (trash) icon
                        contentDescription = "Delete Habit",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeBox(modifier: Modifier = Modifier, habitID: UUID, onDelete: (UUID) -> Unit, content: @Composable () -> Unit) {
    val swipeState = rememberSwipeToDismissBoxState()

    val icon: ImageVector = Icons.Outlined.Delete
    val alignment: Alignment = Alignment.CenterEnd
    val color: Color = MaterialTheme.colorScheme.primary

    SwipeToDismissBox(
        modifier = modifier
            .animateContentSize()
            .fillMaxSize()
            .clip(MaterialTheme.shapes.small),
        state = swipeState,
        backgroundContent = {
            Box(
                contentAlignment = alignment,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(MaterialTheme.shapes.small)
                    .background(color)
            ) {
                Icon(
                    modifier = Modifier.minimumInteractiveComponentSize(),
                    imageVector = icon, contentDescription = null,
                )
            }
        }
    ) {
        content()
    }

    if (swipeState.currentValue == SwipeToDismissBoxValue.EndToStart ||
        swipeState.currentValue == SwipeToDismissBoxValue.StartToEnd) {
        onDelete(habitID)
    }
}