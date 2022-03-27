package pichardo.fernanda.mydigimind.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import pichardo.fernanda.mydigimind.AdaptadorTareas
import pichardo.fernanda.mydigimind.R
import pichardo.fernanda.mydigimind.databinding.ActivityMainBinding.inflate
import pichardo.fernanda.mydigimind.databinding.FragmentHomeBinding
import pichardo.fernanda.mydigimind.ui.Task

class HomeFragment : Fragment() {
    private lateinit var _binding : FragmentHomeBinding
    private val binding get()= _binding!!
    private lateinit var homeViewModel: HomeViewModel

    companion object{
        var task : ArrayList<Task> = ArrayList<Task>()
        var first = true
        lateinit var adaptador : AdaptadorTareas
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

        val gridView : GridView = binding.gridView

        if(first) {
            cargar_tareas()
            first = false
        }
         adaptador = AdaptadorTareas(root.context,task)

        gridView.adapter = adaptador
        return root
    }

    fun cargar_tareas() {
        val preferencias = context?.getSharedPreferences("preferencias", Context.MODE_PRIVATE)
        val gson : Gson = Gson()
        val json = preferencias?.getString("tareas",null)
        val type = object : TypeToken<ArrayList<Task?>?>() {}.type
        if(json == null) {
            task = ArrayList<Task>()
        } else {
            task = gson.fromJson(json,type)

        }
    }
}