package com.example.foodapp.data

import android.content.ContentValues
import android.content.Context
import com.example.foodapp.model.FoodItem


class FoodHelper(context: Context) {
    private val dbHelper = BaseDatabaseHelper.getInstance(context)

    fun insertFood(foodItem: FoodItem): Long {
        val db = dbHelper.readableDatabase
        val values = ContentValues().apply {
            put(DatabaseConstants.FOOD_NAME, foodItem.name)
            put(DatabaseConstants.FOOD_DESCRIPTION, foodItem.description)
            put(DatabaseConstants.FOOD_IMAGE_PATH, foodItem.imagePath)
        }
        return db.insert(DatabaseConstants.FOOD_TABLE_NAME, null, values)
    }

    fun deleteFood(id: Int): Int {
        val db = dbHelper.readableDatabase

        return db.delete(DatabaseConstants.FOOD_TABLE_NAME, "${DatabaseConstants.FOOD_ID} = ?", arrayOf(id.toString()))
    }

    fun getAllFoodItems(): List<FoodItem> {
        val foodList = mutableListOf<FoodItem>()
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM ${DatabaseConstants.FOOD_TABLE_NAME}", null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseConstants.FOOD_ID))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.FOOD_NAME))
                val imagePath = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.FOOD_IMAGE_PATH))
                val description = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseConstants.FOOD_DESCRIPTION))
                foodList.add(FoodItem(id, name, description, imagePath))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return foodList
    }
}