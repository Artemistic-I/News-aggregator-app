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
import java.util.*

class TopicSelector : AppCompatActivity() {

    private var mAuth = FirebaseAuth.getInstance()
    private var currentUser = mAuth.currentUser
    lateinit var context:Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
        getSharedPreferences(getString(R.string.mypreference), Context.MODE_WORLD_READABLE)
            .edit()
            .putBoolean(getString(R.string.isRunning), false)
            .commit()
        setContentView(R.layout.activity_topic_selector)

        var actionBar = supportActionBar

        if (actionBar != null) {

            // Customize the back button
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back)

            // showing the back button in action bar
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = getString(R.string.preferences_title)
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
                .putBoolean(getString(R.string.breaking_news), breakingNewsBox.isChecked)
                .putBoolean(getString(R.string.world), worldBox.isChecked)
                .putBoolean(getString(R.string.business), businessBox.isChecked)
                .putBoolean(getString(R.string.technology), technologyBox.isChecked)
                .putBoolean(getString(R.string.entertainment), entertainmentBox.isChecked)
                .putBoolean(getString(R.string.sports), sportsBox.isChecked)
                .putBoolean(getString(R.string.science), scienceBox.isChecked)
                .putBoolean(getString(R.string.health), healthBox.isChecked)
                .commit()

            val snackbar = Snackbar.make(v, getString(R.string.preferences_saved), Snackbar.LENGTH_LONG)
            val tv: TextView = snackbar.view.findViewById(com.google.android.material.R.id.snackbar_text);
            tv.setTextColor(getColor(R.color.light_orange))
            snackbar.show()
        }
    }

    override fun onStart() {
        super.onStart()
        getSharedPreferences(getString(R.string.mypreference), Context.MODE_WORLD_READABLE)
            .edit()
            .putBoolean(getString(R.string.isRunning), false)
            .commit()
        Timer().schedule(object : TimerTask() {
            override fun run() {
                val notifierService = Intent(context, NewsNotifierService::class.java)
                notifierService.putExtra(getString(R.string.action), getString(R.string.stop))
                startService(notifierService)
            }
        }, 1000)
        loadPreferences()
    }

    override fun onStop() {
        super.onStop()
        getSharedPreferences(getString(R.string.mypreference), Context.MODE_WORLD_READABLE)
            .edit()
            .putBoolean(getString(R.string.isRunning), true)
            .commit()
        val notifierService = Intent(context, NewsNotifierService::class.java)
        notifierService.putExtra(getString(R.string.action), getString(R.string.start))
        startService(notifierService)
    }
    private fun loadPreferences() {
        val pref = getSharedPreferences(currentUser?.email.toString(), Context.MODE_PRIVATE)
        val breakingNewsBox = findViewById<CheckBox>(R.id.breaking_news_checkbox)
        breakingNewsBox.isChecked = pref.getBoolean(getString(R.string.breaking_news), false)
        val worldBox = findViewById<CheckBox>(R.id.world_checkbox)
        worldBox.isChecked = pref.getBoolean(getString(R.string.world), false)
        val businessBox = findViewById<CheckBox>(R.id.business_checkbox)
        businessBox.isChecked = pref.getBoolean(getString(R.string.business), false)
        val technologyBox = findViewById<CheckBox>(R.id.technology_checkbox)
        technologyBox.isChecked = pref.getBoolean(getString(R.string.technology), false)
        val entertainmentBox = findViewById<CheckBox>(R.id.entertainment_checkbox)
        entertainmentBox.isChecked = pref.getBoolean(getString(R.string.entertainment), false)
        val sportsBox = findViewById<CheckBox>(R.id.sports_checkbox)
        sportsBox.isChecked = pref.getBoolean(getString(R.string.sports), false)
        val scienceBox = findViewById<CheckBox>(R.id.science_checkbox)
        scienceBox.isChecked = pref.getBoolean(getString(R.string.science), false)
        val healthBox = findViewById<CheckBox>(R.id.health_checkbox)
        healthBox.isChecked = pref.getBoolean(getString(R.string.health), false)
    }
}