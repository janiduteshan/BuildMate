package com.mad.buildmate

import ItemsAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class ItemsListActivity : AppCompatActivity() {

    private lateinit var itemsRecyclerView: RecyclerView
    private lateinit var itemsAdapter: ItemsAdapter
    private lateinit var items: MutableList<Item>

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_items_list)

        itemsRecyclerView = findViewById(R.id.items_recycler_view)
        itemsRecyclerView.layoutManager = LinearLayoutManager(this)
        items = mutableListOf()

        // Initialize the Firebase connection
        database = FirebaseDatabase.getInstance().getReference("items")

        loadItems()

        itemsAdapter = ItemsAdapter(items) { item, action ->
            when (action) {
                ItemsAdapter.ActionType.UPDATE -> updateItem(item)
                ItemsAdapter.ActionType.DELETE -> deleteItem(item.id)
            }
        }
        itemsRecyclerView.adapter = itemsAdapter
    }

    private fun loadItems() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                items.clear()
                for (itemSnapshot in dataSnapshot.children) {
                    val id = itemSnapshot.key ?: ""
                    val title = itemSnapshot.child("title").getValue(String::class.java) ?: ""
                    val description = itemSnapshot.child("description").getValue(String::class.java) ?: ""
                    val price = itemSnapshot.child("price").getValue(String::class.java) ?: ""
                    val contactInfo = itemSnapshot.child("contactInfo").getValue(String::class.java) ?: ""
                    val item = Item(id, title, description, price, contactInfo)
                    items.add(item)
                }
                itemsAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("ItemsListActivity", "Error loading items: ${databaseError.message}")
            }
        })
    }

    private fun updateItem(item: Item) {
        // You can open a new activity to edit the item details or implement your update logic here
    }

    private fun deleteItem(itemId: String) {
        database.child(itemId).removeValue()
    }
}
