package com.example.foodapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.foodapp.R
import com.example.foodapp.adapter.FoodAdapter
import com.example.foodapp.data.FoodHelper
import com.example.foodapp.model.FoodItem

class FoodListFragment : Fragment() {
    private lateinit var foodHelper: FoodHelper
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private var foodList: MutableList<FoodItem> = mutableListOf()
    private lateinit var foodAdapter: FoodAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_food_list, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(view.context)

        // Load data
        foodHelper = FoodHelper(requireContext())
        foodList = foodHelper.getAllFoodItems().toMutableList()

        // Set up adapter
        foodAdapter = FoodAdapter(requireContext(), foodList)
        recyclerView.adapter = foodAdapter


        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh)
        swipeRefreshLayout.setOnRefreshListener {
            refreshFoodItems()
        }

        return view
    }

    private fun refreshFoodItems() {
        foodList.clear() // Clear old data
        foodList.addAll(foodHelper.getAllFoodItems()) // Fetch fresh data

        foodAdapter.notifyDataSetChanged() // Notify adapter about data change
        swipeRefreshLayout.isRefreshing = false // Stop the refreshing animation
    }
}