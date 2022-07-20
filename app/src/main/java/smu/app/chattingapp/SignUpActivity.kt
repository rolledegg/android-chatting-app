package smu.app.chattingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import smu.app.chattingapp.databinding.ActivityLoginBinding
import smu.app.chattingapp.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()
        
        binding.signUpBtn.setOnClickListener {
            val email = binding.emailEdt.text.toString()
            val pw = binding.passwardEdt.text.toString()
            
            signUp(email,pw)
        }
        
    }

    private fun signUp(email: String, pw: String) {
        //logic of creating user
        mAuth.createUserWithEmailAndPassword(email, pw)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    //cod for jumping to home
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)

                } else {
                    Log.w("TAG", "createUserWithEmail:failure", task.exception)
                  Toast.makeText(this,"Some error occured",Toast.LENGTH_SHORT).show()
                }
            }
    }
}