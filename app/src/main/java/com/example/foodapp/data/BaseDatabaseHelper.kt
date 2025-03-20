package com.example.foodapp.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BaseDatabaseHelper private constructor(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "app_database.db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_FOOD = "food_items"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_IMAGE = "image"
        private const val COLUMN_DESCRIPTION = "description"

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
            CREATE TABLE $TABLE_FOOD (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT,
                $COLUMN_IMAGE INTEGER,
                $COLUMN_DESCRIPTION TEXT
            )
        """.trimIndent()



        db.execSQL(createFoodTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_FOOD")
        onCreate(db)
    }
}