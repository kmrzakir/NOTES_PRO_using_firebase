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
import kotlin.contracts.Returns

class signUPScreen : AppCompatActivity() {

    private lateinit var passwordEdittextview:EditText
    private lateinit var emailEdittextview:EditText
    private lateinit var conformPasswordEdittextview:EditText
    private lateinit var signUPBtn:AppCompatButton
    private lateinit var goToLoginScreen:TextView
    private lateinit var progressBar:ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_upscreen)

        //==================FINDING IDES HERE
        passwordEdittextview = findViewById(R.id.password_EditText)
        emailEdittextview = findViewById(R.id.email_EditText)
        conformPasswordEdittextview = findViewById(R.id.conform_password_EditText)
        signUPBtn = findViewById(R.id.signUp_btn)
        goToLoginScreen = findViewById(R.id.goToLogin_screen)
        progressBar = findViewById(R.id.progressBar)

        goToLoginScreen.setOnClickListener {
            startActivity(Intent(this,signIn_screen::class.java))
        }

        signUPBtn.setOnClickListener {
            var email = emailEdittextview.text.toString()
            var password = passwordEdittextview.text.toString()
            var conformPassword = conformPasswordEdittextview.text.toString()
            var isValidated = validateEmailPassword(email,password,conformPassword)
            if(isValidated){
                createUser(email,password)
            }
        }
    }

    private fun createUser(email: String, password: String) {
        signUPBtn.visibility = View.INVISIBLE
        progressBar.visibility = View.VISIBLE
        var auth = FirebaseAuth.getInstance()
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    Toast.makeText(this,"USER LOGIN SUCCESSFULLY CHECK EMAIL",Toast.LENGTH_SHORT).show()
                    auth.currentUser?.sendEmailVerification()
                    startActivity(Intent(this,MainActivity::class.java))
                    finish()
                }else{
                    Toast.makeText(this,"EXCEPTION : ${it.exception?.message}",Toast.LENGTH_SHORT).show()
                }
            }


    }

    private fun validateEmailPassword(email: String, password: String, conformPassword: String):Boolean {
               if(password != conformPassword){
                   Toast.makeText(this,"PASSWORD AND CONFORM PASSWORD MUST BE SAME",Toast.LENGTH_SHORT).show()
                   return false
               }else if(password.isEmpty()){
                   Toast.makeText(this,"ENTER PASSWORD",Toast.LENGTH_SHORT).show()
                   return false
               }else if(conformPassword.isEmpty()){
                   Toast.makeText(this,"ENTER CONFORM PASSWORD",Toast.LENGTH_SHORT).show()
                   return false
               }else if(email.isEmpty()){
                   Toast.makeText(this,"ENTER EMAIL",Toast.LENGTH_SHORT).show()
                   return false
               }
        return true
    }
}