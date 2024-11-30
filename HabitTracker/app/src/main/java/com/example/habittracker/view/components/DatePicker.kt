package com.example.habittracker.view.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidthIn
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDocked(
    modifier: Modifier = Modifier,
    onDateSelected: (LocalDate) -> Unit
) {
    val datePickerState = rememberDatePickerState()
    LaunchedEffect(datePickerState.selectedDateMillis) {
        datePickerState.selectedDateMillis?.let { millis ->
            val selectedDate = Instant.ofEpochMilli(millis)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
            onDateSelected(selectedDate)
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp) // Padding inside the top section
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        BoxWithConstraints {
            // 360 is minimum because DatePicker uses 12.dp horizontal padding and 48.dp for each week day
            val scale = remember(this.maxWidth) { if(this.maxWidth > 360.dp) 1f else (this.maxWidth / 360.dp) }
            // Make sure there is always enough room, so use requiredWidthIn
            Box(modifier = Modifier.requiredWidthIn(min = 360.dp)) {
                // Scale in case the width is too large for the screen
                DatePicker(
                    modifier = Modifier.scale(scale),
                    state = datePickerState,
                    title = null,
                    headline = null,
                    showModeToggle = false,
                )
            }
        }
    }
}
