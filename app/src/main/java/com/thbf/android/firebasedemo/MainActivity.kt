package com.thbf.android.firebasedemo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var mFirebaseAnalytics: FirebaseAnalytics? = null
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)

        //Authentication instance
        mAuth = FirebaseAuth.getInstance()
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = mAuth!!.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser!=null){
            val intent = Intent(this,Control::class.java)
            startActivity(intent)
        }
    }

    fun buLoginEvent(view:View){
        var email = etEmail.text.toString()
        var password = etPassword.text.toString()
        loginToFirebase(email,password)
    }

    private fun loginToFirebase(email: String, password: String) {
        mAuth!!.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(applicationContext,"Successful login",Toast.LENGTH_LONG).show()
                        val currentUser = mAuth!!.currentUser
                        Log.d("Login:",currentUser!!.uid)
                    }
                    else {
                        Toast.makeText(applicationContext,"Failed login",Toast.LENGTH_LONG).show()
                    }
                }
    }


}