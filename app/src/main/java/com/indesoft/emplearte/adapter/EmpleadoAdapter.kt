package com.pruebas.empleados.adapter

import android.app.AlertDialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.indesoft.emplearte.R
import com.indesoft.emplearte.activity.AddEmpleado
import com.indesoft.emplearte.model.Empleados


class EmpleadoAdapter(private  val  empleados: List<Empleados>): RecyclerView.Adapter<EmpleadoAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.visor, parent, false)
        return ViewHolder(
            view
        )
    }

    override fun getItemCount() = empleados.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val objEmpleado = empleados[position]
        holder.TipoNip.text = objEmpleado.TipoDocumento
        holder.Nip.text = objEmpleado.Documento.toString()
        holder.Nombres.text = objEmpleado.Nombres
        holder.Apellidos.text = objEmpleado.Apellidos
        holder. Direccion.text = objEmpleado.Direccion
        holder.Telefono.text = objEmpleado.Telefono


        holder.btnEditar.setOnClickListener(View.OnClickListener { view ->
            val builder = AlertDialog.Builder(holder.itemView.context)
            builder.setTitle("Empleados")
            builder.setMessage("¿Desea ver los datos del empleado con Nip:"  +objEmpleado.Documento +"?")
            //builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))

            builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                val DatosEdicion =
                    Intent(holder.itemView.context.applicationContext, AddEmpleado::class.java)
                DatosEdicion.putExtra("TipoNip", objEmpleado.TipoDocumento)
                DatosEdicion.putExtra("IdEmpleado", objEmpleado.Documento)
                DatosEdicion.putExtra("Nombres", objEmpleado.Nombres)
                DatosEdicion.putExtra("Apellidos", objEmpleado.Apellidos)
                DatosEdicion.putExtra("Direccion", holder.Direccion.text)
                DatosEdicion.putExtra("Telefono", holder.Telefono.text)
                holder.itemView.context.startActivity(DatosEdicion)

            }

            builder.setNegativeButton(android.R.string.no) { dialog, which ->
                Toast.makeText(holder.itemView.context,
                    android.R.string.no, Toast.LENGTH_SHORT).show()
            }
            builder.show()


        })

        holder.btnDelete.setOnClickListener(View.OnClickListener { view ->



        })






    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val TipoNip: TextView = itemView.findViewById(R.id.txtTipoNip) as TextView
        val Nip: TextView = itemView.findViewById(R.id.txtNip) as TextView
        val Nombres: TextView = itemView.findViewById(R.id.txtNombres) as TextView
        val Apellidos: TextView = itemView.findViewById(R.id.txtApellidos) as TextView
        val Telefono: TextView = itemView.findViewById(R.id.txtTel) as TextView
        val Direccion: TextView = itemView.findViewById(R.id.txtDire) as TextView
        val btnEditar: ImageView = itemView.findViewById(R.id.imageViewEdit) as ImageView
        val btnDelete: ImageView = itemView.findViewById(R.id.imageView3Delete) as ImageView
    }

    fun DeleteEmpleados(objEmple: Empleados) {

    }

}

