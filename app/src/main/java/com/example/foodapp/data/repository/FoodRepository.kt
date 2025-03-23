package com.example.foodapp.data.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.foodapp.data.remote.FoodRemoteService
import com.example.foodapp.data.sqlite.FoodHelper
import com.example.foodapp.model.FoodItem
import com.example.foodapp.utils.NetworkConnection

class FoodRepository(
    private val foodHelper: FoodHelper,
    private val remoteService: FoodRemoteService,
    private val context: Context
) {

    fun getAllFoodItems(): LiveData<List<FoodItem>> {
        val liveData = MutableLiveData<List<FoodItem>>()

        // If offline
        if (!NetworkConnection.isConnected(context)) {
            liveData.value = foodHelper.getAllFoodItems()

            return liveData
        }

        remoteService.fetchAllFoods(
            onComplete = { foodList ->
                for (food in foodList) {
                    foodHelper.insertFoodItem(food)
                }
                liveData.value = foodList
            },
            onError = { exception ->
                Log.e("FoodRepository", "Failed to fetch from Firebase", exception)
            }
        )

        return liveData
    }

    fun insertFoodItem(foodItem: FoodItem, onComplete: (Boolean) -> Unit) {
        // If offline
        if (!NetworkConnection.isConnected(context)) {
            foodHelper.insertFoodItem(foodItem)
            onComplete(true)
            return
        }

        remoteService.insertFood(foodItem) { success, firestoreId ->
            if (success) {
                val newFoodItem = foodItem.copy(firestoreId = firestoreId)
                foodHelper.insertFoodItem(newFoodItem)
            }
            onComplete(success)
        }
    }

    fun deleteFood(id: String, onComplete: (Boolean) -> Unit) {
        foodHelper.deleteFood(id)

        // If offline
        if (!NetworkConnection.isConnected(context)) {
            onComplete(true)
            return
        }

        remoteService.deleteFood(id, onComplete)
    }

    fun getFoodItemByFireStoreId(firestoreId: String): FoodItem? {
        return foodHelper.getFoodItemByFireStoreId(firestoreId)
    }
}