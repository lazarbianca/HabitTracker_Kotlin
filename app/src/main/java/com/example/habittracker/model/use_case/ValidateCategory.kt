package com.example.habittracker.model.use_case

import com.example.habittracker.enums.Category

class ValidateCategory {
    fun execute(category: Category): ValidationResult {
        if(category == Category.UNSELECTED){
            return ValidationResult(
                successful = false,
                errorMessage = "The category needs to be selected."
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}