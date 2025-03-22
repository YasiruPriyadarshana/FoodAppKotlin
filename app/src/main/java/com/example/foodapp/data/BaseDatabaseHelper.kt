package com.example.foodapp.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BaseDatabaseHelper private constructor(context: Context) :
    SQLiteOpenHelper(context, DatabaseConstants.DATABASE_NAME, null, DatabaseConstants.DATABASE_VERSION) {

    companion object {

        @Volatile
        private var instance: BaseDatabaseHelper? = null

        fun getInstance(context: Context): BaseDatabaseHelper {
            return instance ?: synchronized(this) {
                instance ?: BaseDatabaseHelper(context.applicationContext).also { instance = it }
            }
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createFoodTable = """
            CREATE TABLE ${DatabaseConstants.FOOD_TABLE_NAME} (
                ${DatabaseConstants.FOOD_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
                ${DatabaseConstants.FOOD_NAME} TEXT,
                ${DatabaseConstants.FOOD_DESCRIPTION} TEXT,
                ${DatabaseConstants.FOOD_IMAGE_PATH} TEXT
            )
        """.trimIndent()



        db.execSQL(createFoodTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS ${DatabaseConstants.FOOD_TABLE_NAME}")
        onCreate(db)
    }
}