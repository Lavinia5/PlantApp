package com.example.plantapp

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class PlantDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE $TABLE_PLANTS (" +
                    "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COLUMN_NAME TEXT, " +
                    "$COLUMN_DETAILS TEXT" +
                    ")"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PLANTS")
        onCreate(db)
    }

    companion object {
        const val DATABASE_NAME = "plants.db"
        const val DATABASE_VERSION = 1
        const val TABLE_PLANTS = "plants"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_DETAILS = "details"
    }
}
