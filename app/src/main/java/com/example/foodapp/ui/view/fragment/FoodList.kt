package com.example.foodapp.ui.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.foodapp.R
import com.example.foodapp.adapter.FoodAdapter
import com.example.foodapp.ui.viewmodel.FoodViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FoodListFragment : Fragment() {
    private val viewModel: FoodViewModel by viewModels()
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var foodAdapter: FoodAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_food_list, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(view.context)

        // Set up adapter
        foodAdapter = FoodAdapter(requireContext(), mutableListOf())
        recyclerView.adapter = foodAdapter

        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh)
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.loadFoodItems()
        }

        viewModel.foodItems.observe(viewLifecycleOwner) { foodList ->
            foodAdapter.updateData(foodList)
            swipeRefreshLayout.isRefreshing = false
        }

        viewModel.loadFoodItems()

        return view
    }
}