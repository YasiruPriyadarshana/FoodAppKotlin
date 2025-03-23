package com.example.foodapp.data.sqlite

import android.content.ContentValues
import android.content.Context
import com.example.foodapp.utils.DatabaseConstants
import com.example.foodapp.model.FoodItem


class FoodHelper(context: Context) {
    private val dbHelper = BaseDatabaseHelper.getInstance(context)

    private fun insertFood(foodItem: FoodItem): Long {
        val db = dbHelper.writableDatabase  // Fix: Use writableDatabase
        val values = ContentValues().apply {
            put(DatabaseConstants.FOOD_FIRESTORE_ID, foodItem.firestoreId) // Required
            put(DatabaseConstants.FOOD_NAME, foodItem.name)
            put(DatabaseConstants.FOOD_DESCRIPTION, foodItem.description)
            put(DatabaseConstants.FOOD_IMAGE_PATH, foodItem.imagePath)
        }
        return db.insert(DatabaseConstants.FOOD_TABLE_NAME, null, values)
    }

    private fun updateFood(food: FoodItem) {
        val db = dbHelper.readableDatabase
        val values = ContentValues().apply {
            put(DatabaseConstants.FOOD_NAME, food.name)
            put(DatabaseConstants.FOOD_DESCRIPTION, food.description)
            put(DatabaseConstants.FOOD_FIRESTORE_ID, food.firestoreId)

            // Only update image path if it's not empty
            if (food.imagePath.isNotEmpty()) {
                put(DatabaseConstants.FOOD_IMAGE_PATH, food.imagePath)
            }

        }
        db.update(DatabaseConstants.FOOD_TABLE_NAME, values, "${DatabaseConstants.FOOD_FIRESTORE_ID} = ?", arrayOf(food.firestoreId))
    }

     fun insertFoodItem(foodItem: FoodItem) {
        val existingFoodItem:FoodItem? = getFoodItemByFireStoreId(foodItem.firestoreId)
        if(existingFoodItem == null){
            insertFood(foodItem)
        }else{
            updateFood(foodItem)
        }
    }

    fun deleteFood(firestoreId: String): Int {
        val db = dbHelper.readableDatabase

        return db.delete(DatabaseConstants.FOOD_TABLE_NAME, "${DatabaseConstants.FOOD_FIRESTORE_ID} = ?", arrayOf(firestoreId))
    }

    fun getFoodItemByFireStoreId(id: String): FoodItem? {
        var foodItem:FoodItem? = null
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM ${DatabaseConstants.FOOD_TABLE_NAME} WHERE ${DatabaseConstants.FOOD_FIRESTORE_ID} = ?",
            arrayOf(id)
        )

        if (cursor.moveToFirst()) {
            foodItem = FoodItem(
                cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseConstants.FOOD_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.FOOD_FIRESTORE_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.FOOD_NAME)),
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.FOOD_DESCRIPTION)),
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.FOOD_IMAGE_PATH))
            )
        }
        cursor.close()
        return foodItem
    }

    fun getAllFoodItems(): List<FoodItem> {
        val foodList = mutableListOf<FoodItem>()
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM ${DatabaseConstants.FOOD_TABLE_NAME}", null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseConstants.FOOD_ID))
                val fId = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.FOOD_FIRESTORE_ID))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.FOOD_NAME))
                val description = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.FOOD_DESCRIPTION))
                val imagePath = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.FOOD_IMAGE_PATH))
                foodList.add(FoodItem(id, fId, name, description, imagePath))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return foodList
    }
}