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
import com.example.foodapp.data.FoodHelper
import com.example.foodapp.model.FoodItem

class FoodListFragment : Fragment() {
    private lateinit var foodHelper: FoodHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_food_list, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView?.layoutManager = LinearLayoutManager(view.context)


        foodHelper = FoodHelper(requireContext())
        val foodList = foodHelper.getAllFoodItems()  // Fetch data from database

        recyclerView.adapter = FoodAdapter(requireContext(), foodList)

        recyclerView?.adapter = FoodAdapter(view.context,foodList)

        return view
    }
}