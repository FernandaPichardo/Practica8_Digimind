package pichardo.fernanda.mydigimind

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import pichardo.fernanda.mydigimind.databinding.ActivityRegistroBinding

class RegistroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistroBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegistroBinding.inflate(layoutInflater)
        auth = Firebase.auth
        setContentView(binding.root)

        binding.regButton.setOnClickListener {
            validarRegistro()
        }
    }

    private fun validarRegistro() {
        val correo = binding.regEmail.text.toString().trim()
        val password = binding.regPassword.text.toString().trim()
        val confirm = binding.regConfirmpassword.text.toString().trim()

        if (correo.isEmpty() || password.isEmpty() || confirm.isEmpty()){
            Toast.makeText(baseContext, "Ingrese los datos.", Toast.LENGTH_SHORT).show()
        }else{
            if (password != confirm){
                Toast.makeText(baseContext, "Las contraseñas no coinciden.", Toast.LENGTH_SHORT).show()
            }else{
                registroFirebase(correo, password)
            }
        }
    }

    private fun registroFirebase(email: String, password: String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    Toast.makeText(baseContext, "Usuario creado ${user?.email}", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(baseContext, "El registro ha fallado.", Toast.LENGTH_SHORT).show()
                }
            }
    }
}