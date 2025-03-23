package com.example.foodapp.model

data class FoodItem(
    val id: Int = 0,
    val firestoreId: String,
    val name: String,
    val description: String,
    val imagePath: String
)