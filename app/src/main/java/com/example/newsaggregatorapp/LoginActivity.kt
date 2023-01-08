package com.example.newsaggregatorapp

import android.content.Context
import android.content.Intent
import android.hardware.input.InputManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    //authentication variables
    private var mAuth = FirebaseAuth.getInstance()
    private var currentUser = mAuth.currentUser

    //ui elements that are used a lot
    lateinit var emailText : EditText
    lateinit var pwText : EditText
    lateinit var loginBtn : Button
    lateinit var regBtn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSharedPreferences("mypreference", Context.MODE_PRIVATE)
            .edit()
            .putBoolean("shouldBeRunning", false)
            .commit()
        val notifierService = Intent(this, NewsNotifierService::class.java)
        stopService(notifierService)
        //check for login status
        if (currentUser != null) {
            val newIntent = Intent(this, MainActivity::class.java)
            startActivity(newIntent)
        }
        setContentView(R.layout.activity_login)


        //set up those ui elements
        emailText = findViewById(R.id.input_username)
        pwText = findViewById(R.id.input_password)
        loginBtn = findViewById(R.id.sign_in_btn)
        regBtn = findViewById(R.id.reg_btn)

        loginBtn.setOnClickListener{v -> loginClick(v)}
        regBtn.setOnClickListener{v -> regClick(v)}
    }
    private fun loginClick(v : View) {
        try {
            mAuth.signInWithEmailAndPassword(
                emailText.text.toString(),
                pwText.text.toString()
            ).addOnCompleteListener(this) { task ->
                closeKeyBoard()
                if (task.isSuccessful) {
                    val newIntent = Intent(this, MainActivity::class.java)
                    startActivity(newIntent)
                    update()
                } else {
                    displayMessage(loginBtn, getString(R.string.login_failure))
                }
            }
        } catch(e:Exception) {

        }
        val snackbar = Snackbar.make(v, "Hello :)", Snackbar.LENGTH_SHORT)
        snackbar.show()
    }
    private fun regClick(v : View) {
        if (currentUser != null) {
            val newIntent = Intent(this, MainActivity::class.java)
            startActivity(newIntent)
        } else {
            try {
                mAuth.createUserWithEmailAndPassword(
                    emailText.text.toString(),
                    pwText.text.toString()
                ).addOnCompleteListener(this) { task ->
                    closeKeyBoard()
                    if (task.isSuccessful) {
                        update()
                    } else {
                        displayMessage(regBtn, getString(R.string.register_failure))
                    }
                }
            } catch (e:Exception) {

            }
        }
    }
    private fun displayMessage(view:View, msg:String) {
        val sb = Snackbar.make(view, msg, Snackbar.LENGTH_SHORT)
        sb.show()
    }
    private fun update() {
        currentUser = mAuth.currentUser
        val currentEmail = currentUser?.email

        Log.d("===================", "signed in")
    }
    private fun closeKeyBoard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}