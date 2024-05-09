package com.example.flixster

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_best_movies_detail)

        val person = intent.getParcelableExtra<Person>("person")
        if (person != null) {
            displayPersonDetails(person)
        } else {
            Toast.makeText(this, "Person data not available", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun displayPersonDetails(person: Person) {
        // Display person details in the views
        tvName.text = person.name
        // Set more views for additional details
    }
}
