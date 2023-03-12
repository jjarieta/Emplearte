package com.indesoft.emplearte.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.indesoft.emplearte.R
import com.indesoft.emplearte.model.Empleados
import com.indesoft.emplearte.services.ApiService
import com.pruebas.empleados.adapter.EmpleadoAdapter




import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {



    val  BASE_URL:String = "http://20.194.196.176:5001"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        try {
            CrearControlesMain()
            cargarempleados()

        }catch (e: Exception ){

            Toast.makeText(
                this,
                "Error al cargar empleados: " + e.message,
                Toast.LENGTH_SHORT
            ).show()
            Log.d("falla", "onFailures" + e.message)
        }


    }

    override fun onRestart() {
        super.onRestart()
        try {
            CrearControlesMain()
            cargarempleados()


        }catch (e: Exception ){

            Toast.makeText(
                this,
                "Error al cargar empleados: " + e.message,
                Toast.LENGTH_SHORT
            ).show()
            Log.d("falla", "onFailures" + e.message)
        }
    }

    private fun CrearControlesMain() {
        val btnAddEmpleados =  findViewById<FloatingActionButton>(R.id.btnAddEmpleados)
        btnAddEmpleados.setOnClickListener {
            // Code here executes on main thread after user presses button
            val AddEmpleadoActivity = Intent(applicationContext, AddEmpleado::class.java)


            startActivity(AddEmpleadoActivity)
        }
    }


    fun  cargarempleados() {

        //Carga de la lista
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(ApiService::class.java)
        api.fetchAllEmpleadoss().enqueue(object : Callback<List<Empleados>> {
            override fun onFailure(call: Call<List<Empleados>>, t: Throwable) {
                Log.d("falla", "onFailures" + t.message)

            }

            override fun onResponse(call: Call<List<Empleados>>, response: Response<List<Empleados>>) {

                if (response.body()!!.size>0) {
                    showData(response.body()!!)

                    val myToast = Toast.makeText(applicationContext,"Cantidad de empleados registrados "+ response.body()!!.size.toString(),
                        Toast.LENGTH_SHORT)
                    myToast.setGravity(Gravity.CENTER,200,200)
                    myToast.show()
                }else{
                    val myToast = Toast.makeText(applicationContext,"Lista vacia ",
                        Toast.LENGTH_SHORT)
                    myToast.setGravity(Gravity.CENTER,200,200)
                    myToast.show()

                }


            }

        })
    }

    private fun showData(empleado:  List<Empleados>) {

        val recyclerView = findViewById<View>(R.id.recyclerView) as RecyclerView
        val adapter = EmpleadoAdapter(empleado)
        recyclerView.setHasFixedSize(true)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter


    }



}
