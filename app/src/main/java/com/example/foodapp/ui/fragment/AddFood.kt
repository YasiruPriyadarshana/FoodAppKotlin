package com.example.foodapp.ui.fragment

import android.net.Uri
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.foodapp.R
import com.example.foodapp.data.FoodHelper
import com.example.foodapp.model.FoodItem
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class AddFoodFragment : Fragment() {
    private lateinit var foodHelper: FoodHelper
    private lateinit var imageView: ImageView
    private var imageUri: Uri? = null

    companion object {
        private const val PICK_IMAGE_REQUEST = 1
    }
    private val imagePickerLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                imageUri = uri
                imageView.setImageURI(uri)
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_food, container, false)
        foodHelper = FoodHelper(requireContext())

        val editTextName = view.findViewById<EditText>(R.id.editTextFoodName)
        val editTextDescription = view.findViewById<EditText>(R.id.editTextDescription)
        imageView = view.findViewById(R.id.imageViewFood)
        val buttonPickImage = view.findViewById<Button>(R.id.buttonPickImage)
        val buttonAddFood = view.findViewById<Button>(R.id.buttonAddFood)

        buttonPickImage.setOnClickListener { pickImage() }
        buttonAddFood.setOnClickListener {
            val name = editTextName.text.toString()
            val description = editTextDescription.text.toString()

            if (name.isNotEmpty() && description.isNotEmpty()) {
                val imagePath = saveImageToMediaFolder(imageUri)
                val foodItem = FoodItem(name = name, description = description, imagePath = imagePath)
                foodHelper.insertFood(foodItem)
                Toast.makeText(requireContext(), "Food added!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Please fill all fields!", Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }

    private fun pickImage() {
        imagePickerLauncher.launch("image/*") // Opens gallery for image selection
    }

    private fun saveImageToMediaFolder(uri: Uri?): String {
        uri ?: return ""

        val context = requireContext()
        val fileName = "food_${System.currentTimeMillis()}.jpg"
        val file = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), fileName)

        try {
            val inputStream = context.contentResolver.openInputStream(uri)
            val outputStream = FileOutputStream(file)
            inputStream?.copyTo(outputStream)
            inputStream?.close()
            outputStream.close()
            return file.absolutePath
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return ""
    }
}