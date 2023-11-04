package com.cypher.shoppinglist.ui.shoppinglist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.cypher.shoppinglist.data.db.entities.ShoppingItem
import com.cypher.shoppinglist.databinding.ActivityShoppingBinding
import com.cypher.shoppinglist.other.ShoppingItemAdapter
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class ShoppingActivity : AppCompatActivity(), KodeinAware {
    private lateinit var binding: ActivityShoppingBinding

    override val kodein by kodein()
    private val factory: ShoppingViewModelFactory by instance()

    lateinit var viewModel: ShoppingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityShoppingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, factory).get(ShoppingViewModel::class.java)

        val adapter = ShoppingItemAdapter(listOf(), viewModel)

        binding.rvShoppingItems.layoutManager = LinearLayoutManager(this)
        binding.rvShoppingItems.adapter = adapter

        viewModel.getAllShoppingItems().observe(this, Observer {
            adapter.items = it
            adapter.notifyDataSetChanged()
        })

        binding.fab.setOnClickListener {
            AddShoppingItemDialog(
                this,
                object : AddDialogListener {
                    override fun onAddButtonClicked(item: ShoppingItem) {
                        viewModel.upsert(item)
                    }
                }).show()
        }
    }
}