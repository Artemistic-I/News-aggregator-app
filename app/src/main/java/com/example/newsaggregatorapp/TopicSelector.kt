package com.example.newsaggregatorapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class TopicSelector : AppCompatActivity() {

    private var mAuth = FirebaseAuth.getInstance()
    private var currentUser = mAuth.currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val notifierService = Intent(this, NewsNotifierService::class.java)
        notifierService.putExtra("action", "stop")
        startService(notifierService)

        getSharedPreferences("mypreference", Context.MODE_PRIVATE)
            .edit()
            .putBoolean("shouldBeRunning", false)
            .commit()
        setContentView(R.layout.activity_topic_selector)


        var actionBar = supportActionBar

        if (actionBar != null) {

            // Customize the back button
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back)

            // showing the back button in action bar
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = "Recieve notifications for:"
        }
        val pref = getSharedPreferences(currentUser?.email.toString(), Context.MODE_PRIVATE)
        val breakingNewsBox = findViewById<CheckBox>(R.id.breaking_news_checkbox)
        val worldBox = findViewById<CheckBox>(R.id.world_checkbox)
        val businessBox = findViewById<CheckBox>(R.id.business_checkbox)
        val technologyBox = findViewById<CheckBox>(R.id.technology_checkbox)
        val entertainmentBox = findViewById<CheckBox>(R.id.entertainment_checkbox)
        val sportsBox = findViewById<CheckBox>(R.id.sports_checkbox)
        val scienceBox = findViewById<CheckBox>(R.id.science_checkbox)
        val healthBox = findViewById<CheckBox>(R.id.health_checkbox)

        val confirmBtn = findViewById<Button>(R.id.confirm_btn)
        confirmBtn.setOnClickListener {v ->
            pref.edit()
                .putBoolean("breaking-news", breakingNewsBox.isChecked)
                .putBoolean("world", worldBox.isChecked)
                .putBoolean("business", businessBox.isChecked)
                .putBoolean("technology", technologyBox.isChecked)
                .putBoolean("entertainment", entertainmentBox.isChecked)
                .putBoolean("sports", sportsBox.isChecked)
                .putBoolean("science", scienceBox.isChecked)
                .putBoolean("health", healthBox.isChecked)
                .commit()

            val snackbar = Snackbar.make(v, "preferences saved", Snackbar.LENGTH_LONG)
            val tv: TextView = snackbar.view.findViewById(com.google.android.material.R.id.snackbar_text);
            tv.setTextColor(getColor(R.color.light_orange))
            snackbar.show()
        }
    }

    override fun onStart() {
        super.onStart()
        loadPreferences()
    }
    private fun loadPreferences() {
        val pref = getSharedPreferences(currentUser?.email.toString(), Context.MODE_PRIVATE)
        val breakingNewsBox = findViewById<CheckBox>(R.id.breaking_news_checkbox)
        breakingNewsBox.isChecked = pref.getBoolean("breaking-news", false)
        val worldBox = findViewById<CheckBox>(R.id.world_checkbox)
        worldBox.isChecked = pref.getBoolean("world", false)
        val businessBox = findViewById<CheckBox>(R.id.business_checkbox)
        businessBox.isChecked = pref.getBoolean("business", false)
        val technologyBox = findViewById<CheckBox>(R.id.technology_checkbox)
        technologyBox.isChecked = pref.getBoolean("technology", false)
        val entertainmentBox = findViewById<CheckBox>(R.id.entertainment_checkbox)
        entertainmentBox.isChecked = pref.getBoolean("entertainment", false)
        val sportsBox = findViewById<CheckBox>(R.id.sports_checkbox)
        sportsBox.isChecked = pref.getBoolean("sports", false)
        val scienceBox = findViewById<CheckBox>(R.id.science_checkbox)
        scienceBox.isChecked = pref.getBoolean("science", false)
        val healthBox = findViewById<CheckBox>(R.id.health_checkbox)
        healthBox.isChecked = pref.getBoolean("health", false)
    }
}