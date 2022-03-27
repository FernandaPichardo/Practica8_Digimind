package pichardo.fernanda.mydigimind.ui.dashboard

import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import pichardo.fernanda.mydigimind.R
import pichardo.fernanda.mydigimind.databinding.FragmentDashboardBinding
import pichardo.fernanda.mydigimind.ui.Task
import pichardo.fernanda.mydigimind.ui.home.HomeFragment
import pichardo.fernanda.mydigimind.ui.home.HomeViewModel
import java.text.SimpleDateFormat
import java.util.*

class DashboardFragment : Fragment() {
    private lateinit var _binding : FragmentDashboardBinding
    private val binding get() = _binding!!
    private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)
        _binding = FragmentDashboardBinding.inflate(layoutInflater)
        val root = binding.root
        dashboardViewModel.text.observe(viewLifecycleOwner, Observer{

        })

        binding.btnTime.setOnClickListener {
            set_time()
        }

        binding.btnSave.setOnClickListener {
            guardar()
        }
        return root
    }

    fun guardar() {
        var titulo : String = binding.etTask.text.toString()
        var tiempo : String = binding.btnTime.text.toString()
        var dia : String = ""

        if(binding.rbDay1.isChecked) dia = "Lunes"
        if(binding.rbDay2.isChecked) dia = "Martes"
        if(binding.rbDay3.isChecked) dia = "Miercoles"
        if(binding.rbDay4.isChecked) dia = "Jueves"
        if(binding.rbDay5.isChecked) dia = "Viernes"
        if(binding.rbDay6.isChecked) dia = "Sabado"
        if(binding.rbDay7.isChecked) dia = "Domingo"

        var tarea = Task(titulo,dia,tiempo)
        HomeFragment.task.add(tarea)
        Toast.makeText(context, "Se agrego la tarea", Toast.LENGTH_SHORT).show()
        guardar_gson()
    }

    fun guardar_gson() {
        val preferencias = context?.getSharedPreferences("preferencias",Context.MODE_PRIVATE)
        val editor = preferencias?.edit()
        val gson : Gson = Gson()
        val json = gson.toJson(HomeFragment.task)
        editor?.putString("tareas",json)
        editor?.apply ()

    }

    fun set_time() {
        val cal = Calendar.getInstance()
        val timeSetListener = TimePickerDialog.OnTimeSetListener{timePicker, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY,hour)
            cal.set(Calendar.MINUTE,minute)

            binding.btnTime.text = SimpleDateFormat("HH:mm").format(cal.time)
        }

        TimePickerDialog(context,timeSetListener,cal.get(Calendar.HOUR_OF_DAY),
        cal.get(Calendar.MINUTE),true).show()
    }

}