package com.example.foodapp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
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


//        val people = listOf("kaity","lindah","aaronc")

//        val myButton: Button = findViewById(R.id.obapnBtn)
//
//        myButton.setOnClickListener({
//            Toast.makeText(this, "Button Clicked!", Toast.LENGTH_SHORT).show()
//
//        })


//        setContent {
//            BasicKotlinTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                    LazyColumn {
//                        items(people){
//                            ListItem(it)
//                        }
//
//                    }
//                }
//            }
//        }
    }


    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

//    fun onButtonClick(view: View) {
//        Toast.makeText(this, "Button Clicked oiiiii!", Toast.LENGTH_SHORT).show()
//        Log.d("","hello world")
//    }
}

//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    BasicKotlinTheme {
//        Greeting("Android")
//    }
//}
//
//@Composable
//fun ListItem(item:String){
//    Card(
//      modifier = Modifier.fillMaxSize().padding(12.dp)
//    ) {
//        Column {
//            Image(painter = painterResource(R.drawable.baseline_person_24,
//            ), modifier = Modifier.width(100.dp).height(100.dp), contentDescription = "no image")
//            Text(
//                text =  item,
//                modifier = Modifier.padding(12.dp)
//            )
//        }
//
//    }
//}