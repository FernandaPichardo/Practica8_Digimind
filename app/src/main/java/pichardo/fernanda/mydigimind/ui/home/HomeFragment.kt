package pichardo.fernanda.mydigimind.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import pichardo.fernanda.mydigimind.AdaptadorTareas
import pichardo.fernanda.mydigimind.databinding.FragmentHomeBinding
import pichardo.fernanda.mydigimind.ui.Task

class HomeFragment : Fragment() {
    private lateinit var _binding : FragmentHomeBinding
    private val binding get()= _binding!!
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var db: FirebaseFirestore

    companion object{
        var task : ArrayList<Task> = ArrayList()
        var first = true
        lateinit var adaptador : AdaptadorTareas
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = Firebase.firestore
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        val root = binding.root
        homeViewModel.text.observe(viewLifecycleOwner, Observer{

        })

        cargar_tareas()

        return root
    }

    fun cargar_tareas() {
        val actividades = ArrayList<Task>()
        db.collection("actividades").get().addOnSuccessListener { documents ->
            for(actividad in documents){
                actividades.add(Task(
                    actividad.getString("actividad")!!,
                    selectDay(actividad),
                    actividad.getString("tiempo")!!))
            }

            task = actividades
            adaptador = AdaptadorTareas(requireContext(), task)
            binding.gridView.adapter = adaptador
        }
    }

    fun selectDay(actividad: QueryDocumentSnapshot): String{
        when {
            actividad.getBoolean("lu") == true -> {
                return "Lunes"
            }
            actividad.getBoolean("ma") == true -> {
                return "Martes"
            }
            actividad.getBoolean("mi") == true -> {
                return "Miercoles"
            }
            actividad.getBoolean("ju") == true -> {
                return "Jueves"
            }
            actividad.getBoolean("vi") == true -> {
                return "Viernes"
            }
            actividad.getBoolean("sa") == true -> {
                return "Sabado"
            }
            else -> {
                return "Domingo"
            }
        }
    }

    /*fun cargar_tareas() {
        val preferencias = context?.getSharedPreferences("preferencias", Context.MODE_PRIVATE)
        val gson : Gson = Gson()
        val json = preferencias?.getString("tareas",null)
        val type = object : TypeToken<ArrayList<Task?>?>() {}.type
        if(json == null) {
            task = ArrayList<Task>()
        } else {
            task = gson.fromJson(json,type)

        }
    }*/
}