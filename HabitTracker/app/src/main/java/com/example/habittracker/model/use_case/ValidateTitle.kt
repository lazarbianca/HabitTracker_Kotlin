package com.example.habittracker.model.use_case

class ValidateTitle {
    fun execute(title: String): ValidationResult {
        if(title.isBlank()){
            return ValidationResult(
                successful = false,
                errorMessage = "The title cannot be blank"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}