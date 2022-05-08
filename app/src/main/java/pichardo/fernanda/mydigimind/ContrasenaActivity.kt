package pichardo.fernanda.mydigimind

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import pichardo.fernanda.mydigimind.databinding.ActivityContrasenaBinding

class ContrasenaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContrasenaBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityContrasenaBinding.inflate(layoutInflater)
        auth = Firebase.auth
        setContentView(binding.root)

        binding.recoverButton.setOnClickListener {
            val email = binding.recoverEmail.text.toString().trim()
            if (email.isEmpty()){
                Toast.makeText(baseContext, "Ingrese su correo.", Toast.LENGTH_SHORT).show()
            } else{
                recoverPassword(email)
            }
        }
    }

    private fun recoverPassword(email: String){
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    Toast.makeText(baseContext, "Revise la bandeja de entrada del correo para recuperar la contraseña.", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(baseContext, "La recuperación de contraseña ha fallado.", Toast.LENGTH_SHORT).show()
                }
            }
    }
}