package com.example.foodapp.ui.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.foodapp.ui.view.fragment.AddFoodFragment
import com.example.foodapp.ui.view.fragment.FoodListFragment
import com.example.foodapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

//        val foodHelper = FoodHelper(this)
//
//        if (foodHelper.getAllFoodItems().isEmpty()) {
//            foodHelper.insertFood(FoodItem(name = "Pizza 🍕", imagePath = R.drawable.pizza, description = "A delicious cheesy pizza with a perfectly baked crust, topped with rich tomato sauce, gooey mozzarella cheese, and a variety of fresh toppings. Whether you prefer classic pepperoni, fresh vegetables, or a meat lover’s combination, this pizza is the ultimate comfort food, packed with flavor in every bite."))
//            foodHelper.insertFood(FoodItem(name = "Burger 🍔", imagePath = R.drawable.burger, description ="A juicy, mouthwatering beef burger grilled to perfection, served on a toasted sesame bun with crisp lettuce, ripe tomatoes, crunchy pickles, and melted cheese. Topped with a special sauce, this burger is a perfect blend of textures and flavors, making it a satisfying meal for any time of the day."))
//            foodHelper.insertFood(FoodItem(name = "Pasta 🍝", imagePath = R.drawable.pasta, description = "A classic Italian pasta dish made with perfectly cooked al dente noodles, smothered in a rich and savory sauce. Whether it's a creamy Alfredo, a tangy marinara, or a meaty Bolognese, this dish brings the authentic taste of Italy to your plate, sprinkled with fresh herbs and grated Parmesan cheese."))
//        }

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        // Load default fragment
        if (savedInstanceState == null) {
            loadFragment(FoodListFragment())
        }

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_list -> loadFragment(FoodListFragment())
                R.id.nav_add -> loadFragment(AddFoodFragment())
            }
            true
        }

        val foodId = intent.getIntExtra("food_id", -1)

        if(foodId!= -1){
            val fragment = AddFoodFragment().apply {
                arguments = Bundle().apply { putInt("food_id", foodId) }
            }

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }

    }


    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}