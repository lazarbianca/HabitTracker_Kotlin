package com.example.habittracker.model.use_case

import com.example.habittracker.enums.Category

class ValidateGoal {
    fun execute(goal: String?): ValidationResult
    {
        if (goal.isNullOrBlank()) {
            return ValidationResult(
                successful = true
            )
        }
        if (!goal.matches(Regex("^[1-9]\\d*\$"))) {
            return ValidationResult(
                successful = false,
                errorMessage = "The goal must be a valid number without leading zeros or symbols."
            )
        }
        try {
            val goalInt = goal.toInt()
            if (goalInt < 1 || goalInt > 9999) {
                return ValidationResult(
                    successful = false,
                    errorMessage = "Invalid day range."
                )
            }
        } catch (e: NumberFormatException) {
            return ValidationResult(
                successful = false,
                errorMessage = "The goal number is too large."
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}