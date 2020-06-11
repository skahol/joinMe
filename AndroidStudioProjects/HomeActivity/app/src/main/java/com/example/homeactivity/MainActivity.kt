package com.example.homeactivity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
/*import androidx.navigation.findNavController
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController*/
import com.example.homeactivity.ui.home.HomeFragment
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var emailId : EditText
    private lateinit var password : EditText
    private lateinit var btnSignUp : Button
    private lateinit var tvSignIn : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /*val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)*/

        mAuth = FirebaseAuth.getInstance()
        emailId = findViewById(R.id.email_id)
        password = findViewById(R.id.pwd)
        btnSignUp = findViewById(R.id.sign_up)
        tvSignIn = findViewById(R.id.text_view)
        btnSignUp.setOnClickListener {
            val email : String = emailId.text.toString()
            val pwd : String = password.text.toString()

            if (email.isEmpty()){
                emailId.error = "Please enter email id"
                emailId.requestFocus()
            } else if(pwd.isEmpty()){
                password.error = "Please enter password"
                password.requestFocus()
            } else if(email.isEmpty() && pwd.isEmpty()){
                Toast.makeText(this,"Fields Are Empty!",Toast.LENGTH_SHORT).show()
            } else if(!(email.isEmpty() && pwd.isEmpty())){
                mAuth.createUserWithEmailAndPassword(email,pwd).addOnCompleteListener{
                    if(!it.isSuccessful){
                        Toast.makeText(this,"Sign Up Unsuccessful, Please Try Again",Toast.LENGTH_SHORT).show()
                    }else {
                        startActivity(Intent(this,HomeFragment::class.java))
                    }
                }
            } else{
                Toast.makeText(this,"Error Occurred!",Toast.LENGTH_SHORT).show()
            }
        }
        tvSignIn.setOnClickListener{
            val intent = Intent(this,LogInActivity::class.java) // intent.putExtra(k,v)
            startActivity(intent)
        }
    }
}
