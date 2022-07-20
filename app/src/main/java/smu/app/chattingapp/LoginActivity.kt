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

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var mAuth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()

        binding.signUpBtn.setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java)
            finish()
            startActivity(intent)
        }

        binding.loginBtn.setOnClickListener {
            val email = binding.emailEdt.text.toString()
            val pw = binding.passwardEdt.text.toString()
            login(email,pw)
        }
    }

    private fun login(email: String, pw: String) {
        //logic for logging user
        mAuth.signInWithEmailAndPassword(email, pw)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // code for logging in user
                    val intent = Intent(this, MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "signInWithEmail:failure", task.exception)
                    Toast.makeText(this, "User does not existed.",Toast.LENGTH_SHORT).show()

                }
            }


    }
}