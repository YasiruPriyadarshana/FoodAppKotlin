package com.example.foodapp.model

data class FoodItem(
    val id: Int = 0,  // Optional ID for database
    val name: String,
    val description: String,
    val imagePath: String
)