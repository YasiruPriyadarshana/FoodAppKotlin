package com.example.foodapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapp.R
import com.example.foodapp.adapter.FoodAdapter
import com.example.foodapp.model.FoodItem

class FoodListFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_food_list, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView?.layoutManager = LinearLayoutManager(view.context)

        val foodList = listOf(
            FoodItem("Pizza \uD83C\uDF55", R.drawable.pizza,
                "A delicious cheesy pizza with a perfectly baked crust, topped with rich tomato sauce, gooey mozzarella cheese, and a variety of fresh toppings. Whether you prefer classic pepperoni, fresh vegetables, or a meat lover’s combination, this pizza is the ultimate comfort food, packed with flavor in every bite."),
            FoodItem("Burger \uD83C\uDF54", R.drawable.burger, "A juicy, mouthwatering beef burger grilled to perfection, served on a toasted sesame bun with crisp lettuce, ripe tomatoes, crunchy pickles, and melted cheese. Topped with a special sauce, this burger is a perfect blend of textures and flavors, making it a satisfying meal for any time of the day."),
            FoodItem("Pasta \uD83C\uDF5D", R.drawable.pasta, "A classic Italian pasta dish made with perfectly cooked al dente noodles, smothered in a rich and savory sauce. Whether it's a creamy Alfredo, a tangy marinara, or a meaty Bolognese, this dish brings the authentic taste of Italy to your plate, sprinkled with fresh herbs and grated Parmesan cheese.")
        )

        recyclerView?.adapter = FoodAdapter(view.context,foodList)

        return view
    }
}