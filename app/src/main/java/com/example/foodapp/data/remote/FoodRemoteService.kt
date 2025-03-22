package com.example.foodapp.data.remote

import com.example.foodapp.model.FoodItem

class FoodRemoteService() : BaseRemoteService(){

    fun fetchAllFoods(onComplete: (List<FoodItem>) -> Unit, onError: (Exception) -> Unit) {
        firebaseDb.collection("foods").get()
            .addOnSuccessListener { result ->
                val foodList = mutableListOf<FoodItem>()
                for (doc in result) {
                    val food = FoodItem(
                        doc.getLong("id")?.toInt() ?: 0,
                        doc.getString("name") ?: "",
                        doc.getString("description") ?: "",
                        doc.getString("imagePath") ?: ""
                    )
                    foodList.add(food)
                }
                onComplete(foodList)
            }
            .addOnFailureListener { onError(it) }
    }

    fun insertFood(foodItem: FoodItem, onComplete: (Boolean) -> Unit) {
        val foodData = hashMapOf(
            "id" to foodItem.id,
            "name" to foodItem.name,
            "description" to foodItem.description,
            "imagePath" to foodItem.imagePath
        )

        firebaseDb.collection("foods")
            .document(foodItem.id.toString())
            .set(foodData)
            .addOnSuccessListener { onComplete(true) }
            .addOnFailureListener { onComplete(false) }
    }

    fun deleteFood(id: Int, onComplete: (Boolean) -> Unit) {
        firebaseDb.collection("foods").document(id.toString())
            .delete()
            .addOnSuccessListener { onComplete(true) }
            .addOnFailureListener { onComplete(false) }
    }
}