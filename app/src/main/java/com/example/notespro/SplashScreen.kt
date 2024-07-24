package com.example.notespro

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreen : AppCompatActivity() {

    private lateinit var progressBar:ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash_screen)

        progressBar = findViewById(R.id.progressBar)
        CoroutineScope(Dispatchers.Main).launch {
            progressBar.visibility =  View.VISIBLE
            delay(2000)
            goToMainActivity()
        }
    }
    private fun goToMainActivity() {
        progressBar.visibility =  View.INVISIBLE
        var currentUser = FirebaseAuth.getInstance().currentUser
        if(currentUser != null){
            startActivity(Intent(this,MainActivity::class.java))
        }else{
            startActivity(Intent(this,signUPScreen::class.java))
        }

    }
}