package com.example.foodapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodapp.data.repository.FoodRepository
import com.example.foodapp.model.FoodItem

class FoodViewModel(private val repository: FoodRepository) : ViewModel() {
    private val _foodItems = MutableLiveData<List<FoodItem>>()
    val foodItems: LiveData<List<FoodItem>> get() = _foodItems

    fun loadFoodItems() {
        repository.getAllFoodItems().observeForever { foodList ->
            _foodItems.value = foodList
        }
    }

    fun insertFoodItem(foodItem: FoodItem, onComplete: (Boolean) -> Unit) {
        repository.insertFoodItem(foodItem) { success ->
            if (success) loadFoodItems()
            onComplete(success)
        }
    }

    fun deleteFood(id: String, onComplete: (Boolean) -> Unit) {
        repository.deleteFood(id) { success ->
            if (success) loadFoodItems()
            onComplete(success)
        }
    }

    fun getFoodItemByFireStoreId(firestoreId: String): FoodItem? {
        return repository.getFoodItemByFireStoreId(firestoreId)
    }
}