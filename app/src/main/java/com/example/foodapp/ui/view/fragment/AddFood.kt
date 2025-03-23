package com.example.foodapp.ui.view.fragment

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
import androidx.lifecycle.ViewModelProvider
import com.example.foodapp.R
import com.example.foodapp.data.remote.FoodRemoteService
import com.example.foodapp.data.repository.FoodRepository
import com.example.foodapp.data.sqlite.FoodHelper
import com.example.foodapp.model.FoodItem
import com.example.foodapp.ui.viewmodel.FoodViewModel
import com.example.foodapp.ui.viewmodel.ViewModelFactory
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class AddFoodFragment : Fragment() {
    private lateinit var viewModel: FoodViewModel
    private lateinit var imageView: ImageView
    private lateinit var editTextName: EditText
    private lateinit var editTextDescription: EditText
    private var imageUri: Uri? = null

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
    ): View {
        val view = inflater.inflate(R.layout.fragment_add_food, container, false)

        // Initialize ViewModel
        val repository = FoodRepository(FoodHelper(requireContext()), FoodRemoteService(), requireContext())
        viewModel = ViewModelProvider(this, ViewModelFactory(repository))[FoodViewModel::class.java]

        editTextName = view.findViewById(R.id.editTextFoodName)
        editTextDescription = view.findViewById(R.id.editTextDescription)
        imageView = view.findViewById(R.id.imageViewFood)
        val buttonPickImage = view.findViewById<Button>(R.id.buttonPickImage)
        val buttonAddFood = view.findViewById<Button>(R.id.buttonAddFood)



        buttonPickImage.setOnClickListener { pickImage() }
        buttonAddFood.setOnClickListener { saveFood() }

        return view
    }

    private fun saveFood() {
        val name = editTextName.text.toString()
        val description = editTextDescription.text.toString()
        val firestoreId = arguments?.getString("firestoreId") ?: ""  // Firestore ID is required

        if (name.isNotEmpty() && description.isNotEmpty() && firestoreId.isNotEmpty()) {
            val imagePath = imageUri?.let { saveImageToMediaFolder(it) } ?: ""

            val existingFood = viewModel.getFoodItemByFireStoreId(firestoreId)
            val foodItem = FoodItem(
                id = existingFood?.id ?: 0, // Keep existing ID if updating
                firestoreId = firestoreId,
                name = name,
                description = description,
                imagePath = imagePath.ifEmpty { existingFood?.imagePath ?: "" } // Retain old image if not changed
            )

            viewModel.insertFoodItem(foodItem) { success ->
                Toast.makeText(requireContext(), if (success) "Food saved!" else "Failed!", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(requireContext(), "Please fill all fields!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun pickImage() {
        imagePickerLauncher.launch("image/*")
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