package com.cypher.shoppinglist.other

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cypher.shoppinglist.data.db.entities.ShoppingItem
import com.cypher.shoppinglist.databinding.ShoppingItemBinding
import com.cypher.shoppinglist.ui.shoppinglist.ShoppingViewModel

class ShoppingItemAdapter(
    var items: List<ShoppingItem>,
    private val viewModel: ShoppingViewModel
) : RecyclerView.Adapter<ShoppingItemAdapter.ShoppingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingViewHolder {
        val binding = ShoppingItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ShoppingViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ShoppingViewHolder, position: Int) {
        val curShoppingItem = items[position]

        holder.bind(curShoppingItem)

        holder.binding.ivDelete.setOnClickListener {
            viewModel.delete(curShoppingItem)
        }

        holder.binding.ivPlus.setOnClickListener {
            curShoppingItem.amount++
            viewModel.upsert(curShoppingItem)
        }

        holder.binding.ivMinus.setOnClickListener {
            if (curShoppingItem.amount > 0) {
                curShoppingItem.amount--
                viewModel.upsert(curShoppingItem)
            }
        }
    }

    inner class ShoppingViewHolder(val binding: ShoppingItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ShoppingItem) {
            binding.tvName.text = item.name
            binding.tvAmount.text = item.amount.toString()
        }
    }
}