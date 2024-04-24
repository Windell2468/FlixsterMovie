package com.example.flixster

import BestMoviesFragment
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.flixster.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val supportFragmentManager = supportFragmentManager
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        // Replace "id.content" with the correct ID from your layout file
        fragmentTransaction.replace(R.id.content, BestMoviesFragment(), null).commit()
    }
}
