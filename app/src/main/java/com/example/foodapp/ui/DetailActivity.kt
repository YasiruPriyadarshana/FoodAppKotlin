package com.example.foodapp.ui

import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.foodapp.R

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail)
        val foodName = intent.getStringExtra("name")
        val foodImage = intent.getStringExtra("image",)
        val foodDesc = intent.getStringExtra("description")

        val foodNameTextView = findViewById<TextView>(R.id.foodNameTextView)
        val foodImageView = findViewById<ImageView>(R.id.foodImageView)
        val foodDescriptionTextView = findViewById<TextView>(R.id.foodDescriptionTextView)
        val downloadButton = findViewById<Button>(R.id.btnDownloadImage)

        foodNameTextView.text = foodName
        foodImageView.setImageURI(Uri.parse(foodImage))
        foodDescriptionTextView.text = foodDesc

        downloadButton.setOnClickListener {
            saveImageToDownloads(foodImageView)
        }
    }

    private fun saveImageToDownloads(imageView: ImageView) {
        val bitmap = (imageView.drawable as BitmapDrawable).bitmap
        val fileName = "Downloaded_${System.currentTimeMillis()}.jpg"

        val resolver = contentResolver
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_DCIM + "/FoodApp") // FIXED
            put(MediaStore.Images.Media.IS_PENDING, 1)
        }

        val imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        imageUri?.let { uri ->
            resolver.openOutputStream(uri)?.use { outputStream ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            }
            contentValues.clear()
            contentValues.put(MediaStore.Images.Media.IS_PENDING, 0)
            resolver.update(uri, contentValues, null, null)

            Toast.makeText(this, "Image saved to Pictures/MyApp!", Toast.LENGTH_SHORT).show()
        } ?: run {
            Toast.makeText(this, "Failed to save image!", Toast.LENGTH_SHORT).show()
        }
    }
}