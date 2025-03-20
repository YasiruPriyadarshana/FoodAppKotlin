package com.example.foodapp.data

import android.content.ContentValues
import android.content.Context
import com.example.foodapp.model.FoodItem


class FoodHelper(context: Context) {
    private val dbHelper = BaseDatabaseHelper.getInstance(context)

    fun insertFood(foodItem: FoodItem) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("name", foodItem.name)
            put("image", foodItem.imageResId)
            put("description", foodItem.description)
        }
        db.insert("food_items", null, values)
        db.close()
    }

    fun getAllFoodItems(): List<FoodItem> {
        val foodList = mutableListOf<FoodItem>()
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM food_items", null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                val image = cursor.getInt(cursor.getColumnIndexOrThrow("image"))
                val description = cursor.getString(cursor.getColumnIndexOrThrow("description"))
                foodList.add(FoodItem(id, name, image, description))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return foodList
    }
}