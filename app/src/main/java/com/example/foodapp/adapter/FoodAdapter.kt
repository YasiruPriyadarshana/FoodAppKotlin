package com.example.foodapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapp.ui.DetailActivity
import com.example.foodapp.model.FoodItem
import com.example.foodapp.R

class FoodAdapter(private val context: Context, private val foodList: List<FoodItem>) :
    RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    class FoodViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val foodImage: ImageView = view.findViewById(R.id.foodImageView)
        val foodName: TextView = view.findViewById(R.id.foodNameTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_food, parent, false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val food = foodList[position]
        holder.foodImage.setImageResource(food.imageResId)
        holder.foodName.text = food.name

        // Navigate to DetailActivity when clicking the item
        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("name", food.name)
            intent.putExtra("image", food.imageResId)
            intent.putExtra("description", food.description)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
      return foodList.count()
    }


}