package com.example.coffeeit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coffeeit.databinding.ActivityMainBinding
import com.example.coffeeit.helpers.CoffeeItemAdapter
import com.example.coffeeit.models.CoffeeAttributes
import com.example.coffeeit.models.CoffeeItem
import com.example.coffeeit.viewmodels.HomeViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var adapter: CoffeeItemAdapter
    private lateinit var b: ActivityMainBinding
    private lateinit var vm: HomeViewModel
    private val coffeeItemList: MutableList<CoffeeItem> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)

        setUpToolbar()
        setUpScreen()
        setUpAdapter()

        vm = ViewModelProvider(this)[HomeViewModel::class.java]
        vm.fetchDeviceDetails()
        vm.coffeeAttributesLiveData?.observe(this, Observer {
            if (it != null){
                populateList(it)
                adapter.notifyDataSetChanged()
            } else{
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setUpScreen() {
        b.titleTV.text = resources.getString(R.string.screen_title, "style");
    }

    private fun setUpToolbar() {
        setSupportActionBar(b.toolbar)
        supportActionBar?.title = "Brew with Lex"
    }
    private fun setUpAdapter() {
        adapter = CoffeeItemAdapter(this,coffeeItemList)

        b.itemsRV.adapter = adapter
        b.itemsRV.layoutManager = LinearLayoutManager(this)
    }


    private fun populateList(result: CoffeeAttributes.Result) {
        for (i in result.types){

            val coffeeItem = CoffeeItem(name = i.name, typeCategory = i.name)

            coffeeItemList.add(coffeeItem)
        }
    }
}