package pichardo.fernanda.mydigimind

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson
import pichardo.fernanda.mydigimind.ui.Task
import pichardo.fernanda.mydigimind.ui.home.HomeFragment

class AdaptadorTareas: BaseAdapter {
    lateinit var context: Context
    var tasks: ArrayList<Task> = ArrayList<Task>()

    constructor(context: Context, task: ArrayList<Task>){
        this.context = context
        this.tasks = task
    }

    override fun getCount(): Int {
        return tasks.size
    }

    override fun getItem(p0: Int): Any {
        return tasks[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var inflador = LayoutInflater.from(context)
        var vista = inflador.inflate(R.layout.task_view, null)
        var task = tasks[p0]

        val tv_titulo: TextView = vista.findViewById(R.id.tv_title)
        val tv_time: TextView = vista.findViewById(R.id.tv_time)
        val tv_dia: TextView = vista.findViewById(R.id.tv_days)

        tv_titulo.setText(task.title)
        tv_time.setText(task.time)
        tv_dia.setText(task.day)
        tv_time.setText(task.time)
        vista.setOnClickListener {
            eliminar(task)
        }
        return vista
    }

    fun eliminar(task : Task) {
        val alertDialog : AlertDialog.Builder = context?.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setPositiveButton("ELIMINAR",
                DialogInterface.OnClickListener{dialog,id ->
                    HomeFragment.task.remove(task)
                    HomeFragment.adaptador.notifyDataSetChanged()
                    guardar_gson()
                    Toast.makeText(context, "Se elimino correctamente", Toast.LENGTH_SHORT).show()
                })
                setNegativeButton("CANCELAR",
                DialogInterface.OnClickListener{dialog,id ->
                    dialog.dismiss()
                })

                builder?.setMessage("Desea eliminar esta tarea?").setTitle("AVISO")
                builder.create()
            }
        }
        alertDialog.show()
    }

    fun guardar_gson() {
        val preferencias = context?.getSharedPreferences("preferencias",Context.MODE_PRIVATE)
        val editor = preferencias?.edit()
        val gson : Gson = Gson()
        val json = gson.toJson(HomeFragment.task)
        editor?.putString("tareas",json)
        editor?.apply ()

    }
}