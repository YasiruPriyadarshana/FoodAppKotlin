package com.example.foodapp.data.remote

import com.example.foodapp.model.FoodItem
import javax.inject.Inject

class FoodRemoteService @Inject constructor(
    private val baseRemoteService: BaseRemoteService
) {

    fun fetchAllFoods(onComplete: (List<FoodItem>) -> Unit, onError: (Exception) -> Unit) {
        baseRemoteService.firebaseDb.collection("foods").get()
            .addOnSuccessListener { result ->
                val foodList = mutableListOf<FoodItem>()

                for (doc in result) {
                    val food = FoodItem(
                        doc.getLong("id")?.toInt() ?: 0,
                        doc.getString("fireStoreId") ?: "",
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

    fun insertFood(foodItem: FoodItem, onComplete: (Boolean,String) -> Unit) {
        val collectionRef = baseRemoteService.firebaseDb.collection("foods")

        val newId = if (foodItem.firestoreId == "") collectionRef.document().id else foodItem.firestoreId

        val foodData = hashMapOf(
            "id" to foodItem.id,
            "fireStoreId" to newId,
            "name" to foodItem.name,
            "description" to foodItem.description,
            "imagePath" to foodItem.imagePath
        )

        collectionRef.document(newId).set(foodData)
            .addOnSuccessListener { onComplete(true,newId) }
            .addOnFailureListener { onComplete(false,"") }
    }

    fun deleteFood(id: String, onComplete: (Boolean) -> Unit) {
        baseRemoteService.firebaseDb.collection("foods").document(id)
            .delete()
            .addOnSuccessListener { onComplete(true) }
            .addOnFailureListener { onComplete(false) }
    }
}