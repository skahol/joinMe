package com.example.homeactivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.homeactivity.ui.home.HomeFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LogInActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mAuthStateListener : FirebaseAuth.AuthStateListener
    private lateinit var emailId : EditText
    private lateinit var password : EditText
    private lateinit var btnSignIn : Button
    private lateinit var tvSignUp : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
        mAuth = FirebaseAuth.getInstance()
        emailId = findViewById(R.id.email_id)
        password = findViewById(R.id.pwd)
        btnSignIn = findViewById(R.id.sign_in)
        tvSignUp = findViewById(R.id.text_view)

        mAuthStateListener = FirebaseAuth.AuthStateListener(){
            val mFirebaseUser : FirebaseUser? = mAuth.currentUser
            if(mFirebaseUser!=null){
                Toast.makeText(this,"You are logged in",Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,HomeFragment::class.java))
            } else{
                Toast.makeText(this,"Please Login",Toast.LENGTH_SHORT).show()
            }
        }

        btnSignIn.setOnClickListener {
            val email : String = emailId.text.toString()
            val pwd : String = password.text.toString()

            if (email.isEmpty()){
                emailId.error = "Please enter email id"     // set error
                emailId.requestFocus()
            } else if(pwd.isEmpty()){
                password.error = "Please enter password"
                password.requestFocus()
            } else if(email.isEmpty() && pwd.isEmpty()){
                Toast.makeText(this,"Fields Are Empty!",Toast.LENGTH_SHORT).show()
            } else if(!(email.isEmpty() && pwd.isEmpty())){
                // set event listener to check task is completed or not
                mAuth.createUserWithEmailAndPassword(email,pwd).addOnCompleteListener{
                    if(!it.isSuccessful){
                        Toast.makeText(this,"Login Error, Please Try Again",Toast.LENGTH_SHORT).show()
                    }else {
                        startActivity(Intent(this,HomeFragment::class.java))
                    }
                }
            } else{
                Toast.makeText(this,"Error Occurred!",Toast.LENGTH_SHORT).show()
            }
        }

        tvSignUp.setOnClickListener{
            val intent = Intent(this,MainActivity::class.java) // intent.putExtra(k,v)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        mAuth.addAuthStateListener(mAuthStateListener)

    }
}
