package com.cypher.shoppinglist.ui.shoppinglist

import com.cypher.shoppinglist.data.db.entities.ShoppingItem

interface AddDialogListener {
    fun onAddButtonClicked(item: ShoppingItem)
}