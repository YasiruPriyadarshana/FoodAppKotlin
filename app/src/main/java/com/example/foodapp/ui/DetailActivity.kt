package com.example.foodapp.ui

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.foodapp.R

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail)
        val foodName = intent.getStringExtra("name")
        val foodImage = intent.getIntExtra("image", 0)
        val foodDesc = intent.getStringExtra("description")

        findViewById<TextView>(R.id.foodNameTextView).text = foodName
        findViewById<ImageView>(R.id.foodImageView).setImageResource(foodImage)
        findViewById<TextView>(R.id.foodDescriptionTextView).text = foodDesc
    }
}