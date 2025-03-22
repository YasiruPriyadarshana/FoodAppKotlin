package com.example.foodapp.data.sqlite

import android.content.ContentValues
import android.content.Context
import com.example.foodapp.utils.DatabaseConstants
import com.example.foodapp.model.FoodItem


class FoodHelper(context: Context) {
    private val dbHelper = BaseDatabaseHelper.getInstance(context)

    private fun insertFood(foodItem: FoodItem): Long {
        val db = dbHelper.readableDatabase
        val values = ContentValues().apply {
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

            // Only update image path if it's not empty
            if (food.imagePath.isNotEmpty()) {
                put(DatabaseConstants.FOOD_IMAGE_PATH, food.imagePath)
            }
        }
        db.update(DatabaseConstants.FOOD_TABLE_NAME, values, "${DatabaseConstants.FOOD_ID} = ?", arrayOf(food.id.toString()))
    }

     fun insertFoodItem(foodItem: FoodItem) {
        val existingFoodItem:FoodItem? = getFoodItemById(foodItem.id)
        if(existingFoodItem == null){
            insertFood(foodItem)
        }else{
            updateFood(foodItem)
        }
    }

    fun deleteFood(id: Int): Int {
        val db = dbHelper.readableDatabase

        return db.delete(DatabaseConstants.FOOD_TABLE_NAME, "${DatabaseConstants.FOOD_ID} = ?", arrayOf(id.toString()))
    }

    fun getFoodItemById(id: Int): FoodItem? {
        var foodItem:FoodItem? = null
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM ${DatabaseConstants.FOOD_TABLE_NAME} WHERE ${DatabaseConstants.FOOD_ID} = ?",
            arrayOf(id.toString())
        )

        if (cursor.moveToFirst()) {
            foodItem = FoodItem(
                cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseConstants.FOOD_ID)), // id
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.FOOD_NAME)),
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.FOOD_DESCRIPTION)), //// name
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.FOOD_IMAGE_PATH)), // imagePath
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
                val name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.FOOD_NAME))
                val description = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.FOOD_DESCRIPTION))
                val imagePath = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.FOOD_IMAGE_PATH))
                foodList.add(FoodItem(id, name, description, imagePath))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return foodList
    }
}