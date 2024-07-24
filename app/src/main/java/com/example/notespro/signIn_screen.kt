package com.example.notespro

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.google.firebase.auth.FirebaseAuth

class signIn_screen : AppCompatActivity() {

    private lateinit var emialEdittextview:EditText
    private lateinit var passwordEditTextView:EditText
    private lateinit var signinBtn:AppCompatButton
    private lateinit var goToSignUpScreen:TextView
    private lateinit var progressBar:ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_in_screen)

        emialEdittextview = findViewById(R.id.email_signIn_edittext)
        passwordEditTextView = findViewById(R.id.password_sihnIn_edittext)
        signinBtn = findViewById(R.id.signIn_btn)
        goToSignUpScreen = findViewById(R.id.goToSignUP_screen)
        progressBar = findViewById(R.id.progressBar_signIn)

        goToSignUpScreen.setOnClickListener {
            startActivity(Intent(this,signUPScreen::class.java))
        }
        signinBtn.setOnClickListener {
            signinBtn.visibility = View.INVISIBLE
            progressBar.visibility = View.VISIBLE
            var email = emialEdittextview.text.toString()
            var password = passwordEditTextView.text.toString()
           var isValidated = validateEmailPassword(email,password)
            if(isValidated){
                signInWithEmailAndPassword(email,password)
            }else{
                signinBtn.visibility = View.VISIBLE
                progressBar.visibility = View.INVISIBLE
            }
        }
    }

    private fun signInWithEmailAndPassword(email: String, password: String) {
                  var auth = FirebaseAuth.getInstance()
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    Toast.makeText(this,"SIGN IN SUCCESSFULLY",Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this,MainActivity::class.java))
                    finish()
                }else{
                    Toast.makeText(this,"EXCEPTION : ${it.exception?.message}",Toast.LENGTH_SHORT).show()
                    signinBtn.visibility = View.VISIBLE
                    progressBar.visibility = View.INVISIBLE
                }
            }
    }

    private fun validateEmailPassword(email: String, password: String):Boolean {

        if(email.isEmpty()){
            Toast.makeText(this,"ENTER EMAIL",Toast.LENGTH_SHORT).show()
            return false
        }else if(password.isEmpty() && password.length>=6){
            Toast.makeText(this,"ENTER PASSWORD WITH LENGTH SIX OR GREATER",Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}