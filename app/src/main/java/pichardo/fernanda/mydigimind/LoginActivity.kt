package pichardo.fernanda.mydigimind

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import pichardo.fernanda.mydigimind.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        auth = Firebase.auth
        setContentView(binding.root)

        binding.loginSingin.setOnClickListener {
            validarIngreso()
        }

        binding.loginSingup.setOnClickListener {
            startActivity(Intent(this, RegistroActivity::class.java))
        }

        binding.loginRecover.setOnClickListener {
            startActivity(Intent(this, ContrasenaActivity::class.java))
        }
    }

    private fun validarIngreso(){
        val correo = binding.loginEmail.text.toString().trim()
        val password = binding.loginPassword.text.toString().trim()

        if (correo.isEmpty() || password.isEmpty()){
            Toast.makeText(baseContext, "Ingrese los datos.", Toast.LENGTH_SHORT).show()
        }else{
            ingresarFirebase(correo, password)
        }
    }

    private fun ingresarFirebase(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(baseContext, "${task.exception}.", Toast.LENGTH_SHORT).show()
                }
            }
    }
}