package com.mad.buildmate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    private lateinit var titleInput: EditText
    private lateinit var descriptionInput: EditText
    private lateinit var priceInput: EditText
    private lateinit var contactInfoInput: EditText
    private lateinit var submitButton: Button
    private lateinit var viewItemsButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        titleInput = findViewById(R.id.title)
        descriptionInput = findViewById(R.id.description)
        priceInput = findViewById(R.id.price)
        contactInfoInput = findViewById(R.id.contact_info)
        submitButton = findViewById(R.id.submit)
        viewItemsButton = findViewById(R.id.view)

        submitButton.setOnClickListener {
            saveItemToFirebase()
        }

        viewItemsButton.setOnClickListener {
            val intent = Intent(this, ItemsListActivity::class.java)
            startActivity(intent)
        }
    }

    private fun saveItemToFirebase() {
        val title = titleInput.text.toString().trim()
        val description = descriptionInput.text.toString().trim()
        val price = priceInput.text.toString().trim()
        val contactInfo = contactInfoInput.text.toString().trim()

        if (title.isNotEmpty() && description.isNotEmpty() && price.isNotEmpty() && contactInfo.isNotEmpty()) {
            val database = FirebaseDatabase.getInstance().getReference("items")
            val itemId = database.push().key

            val item = Item(itemId!!, title, description, price, contactInfo)

            database.child(itemId).setValue(item).addOnCompleteListener {
                titleInput.text.clear()
                descriptionInput.text.clear()
                priceInput.text.clear()
                contactInfoInput.text.clear()
            }
        }
    }
}
