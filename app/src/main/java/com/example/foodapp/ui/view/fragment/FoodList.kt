package com.example.foodapp.ui.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.foodapp.R
import com.example.foodapp.adapter.FoodAdapter
import com.example.foodapp.data.remote.FoodRemoteService
import com.example.foodapp.data.repository.FoodRepository
import com.example.foodapp.data.sqlite.FoodHelper
import com.example.foodapp.ui.viewmodel.FoodViewModel
import com.example.foodapp.ui.viewmodel.ViewModelFactory

class FoodListFragment : Fragment() {
    private lateinit var viewModel: FoodViewModel
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var foodAdapter: FoodAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_food_list, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(view.context)

        // Initialize ViewModel
        val repository = FoodRepository(FoodHelper(requireContext()), FoodRemoteService(), requireContext())
        viewModel = ViewModelProvider(this, ViewModelFactory(repository))[FoodViewModel::class.java]

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