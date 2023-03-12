package com.indesoft.emplearte.activity


import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.JsonObject

import com.indesoft.emplearte.R
import com.indesoft.emplearte.model.*
import com.indesoft.emplearte.services.ApiService
import okhttp3.ResponseBody


import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import java.util.*


class AddEmpleado : AppCompatActivity() {
    lateinit var Textnombre: EditText
    lateinit var TextApellidos: EditText
    lateinit var TextDocumento: EditText
    lateinit var TextDireccion : EditText
    lateinit var TextTelefono: EditText
    lateinit var  btnActualizar : Button
    lateinit var  btnGuardar : Button
    lateinit var  btnBorrar : Button
    var intDocumento:Int = 0
    val Main =  MainActivity()

    var ListadoAreas = ArrayList<AreasC>()
    val arrayListSubArea = ArrayList<SubAreasC>()
    var ListaDoc = ArrayList<TipoDoc>()

    var CodIdArea:Int =0
    var CodIdSubArea:Int =0
    var CodIdTD:Int =0
    var pos:Int =0
    lateinit var strDoc:String
    lateinit var strDireccion:String
    lateinit var strTelefono:String
    lateinit var strTipoDoc:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_empleado)

        //Creacion de controles
        CrearControles()

        val mIntent = intent
         intDocumento = mIntent.getIntExtra("IdEmpleado", 0)
         strTipoDoc = mIntent.getStringExtra("TipoNip").toString()
        val strNombres = mIntent.getStringExtra("Nombres")
        val strApellidos = mIntent.getStringExtra("Apellidos")
         strDireccion = mIntent.getStringExtra("Direccion").toString()
         strTelefono= mIntent.getStringExtra("Telefono").toString()

        //Para determinar que botones va mostrar
        if (intDocumento!=0){
            TextDocumento.setText(intDocumento.toString())
            if (strNombres != null) {
                Textnombre.setText(strNombres.uppercase())
            }
            if (strApellidos != null) {
                TextApellidos.setText(strApellidos.uppercase())
            }
            if (strDireccion != null) {
                TextDireccion.setText(strDireccion.uppercase())
            }

            if (strTelefono != null) {
                TextTelefono.setText(strTelefono)
            }

            btnActualizar.visibility = View.VISIBLE
            btnGuardar.visibility = View.GONE
            btnBorrar.visibility = View.VISIBLE

        }else{
            btnGuardar.visibility = View.VISIBLE
            btnActualizar.visibility = View.GONE
            btnBorrar.visibility = View.GONE
        }

        //carga de tipos de documentos
        CargarTipoDoc()




    }

    //creación de controles
    private fun CrearControles() {

        //Cajas de textos
        Textnombre =  findViewById(R.id.TextNombres)
        TextApellidos =  findViewById(R.id.TextApellidos)
        TextDocumento=  findViewById(R.id.txtDocumento)
        TextDireccion=  findViewById(R.id.txtDireccion)
        TextTelefono=  findViewById(R.id.txtTelefono)

        //botón de guardar
        btnGuardar = findViewById<Button>(R.id.btnGuardar)
        btnGuardar.setOnClickListener {

            if (ValidarCampos()== true){
                val value: String = TextDocumento.getText().toString()
                val Nip = value.toInt()
                val objEmple = Empleados(strDoc ,Nip ,Textnombre.text.toString(),TextApellidos.text.toString(),TextDireccion.text.toString(),TextTelefono.text.toString())
                SaveEmpleados(objEmple)
            }

        }


        btnActualizar = findViewById<Button>(R.id.btnActualizar)

        btnActualizar.setOnClickListener {
            if (ValidarCampos()== true){
                val value: String = TextDocumento.getText().toString()
                val Nip = value.toInt()
                val objEmple = Empleados(strDoc ,Nip ,Textnombre.text.toString(),TextApellidos.text.toString(),TextDireccion.text.toString(),TextTelefono.text.toString())
                ActualizarEmpleados(objEmple)
            }
        }

        btnBorrar = findViewById(R.id.btnBorrar)

        btnBorrar.setOnClickListener {
            if (ValidarCampos()== true){

                val builder = AlertDialog.Builder(this)
                builder.setTitle("Empleados")
                builder.setMessage("¿Desea eliminar al empleado con Nip? " +TextDocumento.getText().toString())
                builder.setPositiveButton(android.R.string.yes) { dialog, which ->

                    val value: String = TextDocumento.getText().toString()
                    val Nip = value.toInt()
                    val objEmple = Empleados(strDoc ,Nip ,Textnombre.text.toString(),TextApellidos.text.toString(),TextDireccion.text.toString(),TextTelefono.text.toString())
                    DeleteEmpleados(objEmple)


                }

                builder.setNegativeButton(android.R.string.no) { dialog, which ->
                    Toast.makeText(applicationContext,
                        android.R.string.no, Toast.LENGTH_SHORT).show()
                }
                builder.show()

            }
        }
    }

    //llena el listado de tipos de documentos
    private fun CargarTipoDoc() {
        val SpinnerTipoDoc = findViewById<Spinner>(R.id.spinnerTipoDoc)

        ListaDoc.clear()
        ListaDoc.add(TipoDoc(1,"CC"))
        ListaDoc.add(TipoDoc(2,"CE"))
        ListaDoc.add(TipoDoc(3,"PE"))


        val adapter: ArrayAdapter<TipoDoc> = ArrayAdapter<TipoDoc>(
            applicationContext,
            android.R.layout.simple_spinner_dropdown_item,
            ListaDoc
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        SpinnerTipoDoc.adapter = adapter

        if (intDocumento!=0){
            SpinnerTipoDoc.setSelection(BuscarposicionDocumento(strTipoDoc, ListaDoc));
        }
        SpinnerTipoDoc.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View,
                i: Int,
                l: Long
            ) {
                try {

                    BuscarTipoDoc(
                        SpinnerTipoDoc.selectedItem.toString(),
                        ListaDoc
                    ).toString()
                } catch (e: Exception) {
                    Log.e("error", e.message.toString())
                }


                //val text: String = spinner.selectedItem.toString()
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {
                return
            }

        }
    }







    private  fun  SaveEmpleados(vararg ObjEmpleados:Empleados){
        val retrofit = Retrofit.Builder()
            .baseUrl(Main.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val  postParamEmpleado =  JsonObject()

        postParamEmpleado.addProperty("TipoDoc",ObjEmpleados[0].TipoDocumento)
        postParamEmpleado.addProperty("Documento",ObjEmpleados[0].Documento)
        postParamEmpleado.addProperty("Nombres",ObjEmpleados[0].Nombres)
        postParamEmpleado.addProperty("Apellidos",ObjEmpleados[0].Apellidos)
        postParamEmpleado.addProperty("Direccion",ObjEmpleados[0].Direccion)
        postParamEmpleado.addProperty("Telefono",ObjEmpleados[0].Telefono)


        val api = retrofit.create(ApiService::class.java)
        api.SendDataEmpleados(postParamEmpleado).enqueue(object : Callback<Empleados> {
            override fun onResponse(call: Call<Empleados>, response: Response<Empleados>) {
               if (response.code()==200){
                   Toast.makeText(
                       applicationContext,
                       "Empleado Creado" ,
                       Toast.LENGTH_SHORT
                   ).show()
               }

                LimpiarFormulario()
                val EmpleadoActivity = Intent(applicationContext, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                startActivity(EmpleadoActivity)
                //finish()
            }

            override fun onFailure(call: Call<Empleados>, t: Throwable) {
                Toast.makeText(
                    applicationContext,
                    t.message ,
                    Toast.LENGTH_SHORT
                ).show()
            }

        });
    }

    private fun ActualizarEmpleados(objEmple: Empleados) {
        val retrofit = Retrofit.Builder()
            .baseUrl(Main.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val  postParamEmpleado =  JsonObject()

        postParamEmpleado.addProperty("TipoDoc",objEmple.TipoDocumento)
        postParamEmpleado.addProperty("Documento",objEmple.Documento)
        postParamEmpleado.addProperty("Nombres",objEmple.Nombres)
        postParamEmpleado.addProperty("Apellidos",objEmple.Apellidos)
        postParamEmpleado.addProperty("Direccion",objEmple.Direccion)
        postParamEmpleado.addProperty("Telefono",objEmple.Telefono)


        val api = retrofit.create(ApiService::class.java)
        api.updateEmpleados(objEmple.Documento?.toInt() ?: 0,postParamEmpleado).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.code()==200){
                    Toast.makeText(
                        applicationContext,
                        "Empleado Actualizado" ,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                LimpiarFormulario()

                val EmpleadoActivity = Intent(applicationContext, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                startActivity(EmpleadoActivity)
                finish()

            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(
                    applicationContext,
                    t.message ,
                    Toast.LENGTH_SHORT
                ).show()
            }

        });
    }

    private fun DeleteEmpleados(objEmple: Empleados) {
        val retrofit = Retrofit.Builder()
            .baseUrl(Main.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val  postParamEmpleado =  JsonObject()

        postParamEmpleado.addProperty("TipoDoc",objEmple.TipoDocumento)
        postParamEmpleado.addProperty("Documento",objEmple.Documento)
        postParamEmpleado.addProperty("Nombres",objEmple.Nombres)
        postParamEmpleado.addProperty("Apellidos",objEmple.Apellidos)
        postParamEmpleado.addProperty("Direccion",objEmple.Direccion)
        postParamEmpleado.addProperty("Telefono",objEmple.Telefono)


        val api = retrofit.create(ApiService::class.java)
        api.DeleteEmpleados(objEmple.Documento?.toInt() ?: 0,postParamEmpleado).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.code()==200){
                    Toast.makeText(
                        applicationContext,
                        "Empleado Eliminado" ,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                LimpiarFormulario()

                val EmpleadoActivity = Intent(applicationContext, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                startActivity(EmpleadoActivity)
                finish()

            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(
                    applicationContext,
                    t.message ,
                    Toast.LENGTH_SHORT
                ).show()
            }

        });
    }

    private fun LimpiarFormulario() {
        //Carga los valores iniciales
        try {
            CargarTipoDoc()

            CargarTipoDoc()
            Textnombre.text.clear()
            TextApellidos.text.clear()
            TextDocumento.text.clear()
            TextDocumento.requestFocus()

        }catch (e:Exception){
            Toast.makeText(
                applicationContext,
                "Error " + e.message.toString() ,
                Toast.LENGTH_SHORT
            ).show()
        }

    }
    private fun ValidarCampos(): Boolean {
        var Bandera = false
        if(TextUtils.isEmpty(TextDocumento.text)){
            TextDocumento.requestFocus()
            TextDocumento.setError("Campo requerido")
            return Bandera
        }
        if(TextUtils.isEmpty(Textnombre.text)){
            Textnombre.requestFocus()
            Textnombre.setError("Campo requerido")
            return Bandera
        }
        if(TextUtils.isEmpty(TextApellidos.text)){
            TextApellidos.requestFocus()
            TextApellidos.setError("Campo requerido")
            return Bandera
        }
        Bandera = true
        return Bandera
    }

    private fun BuscarTipoDoc(IdDoc: String, ListadoDoc:ArrayList<TipoDoc> ): String {

        for (i in 0 until ListadoDoc.size) {
            if(ListadoDoc[i].TipoDocumento.equals(IdDoc)){
                strDoc = ListadoDoc[i].TipoDocumento.toString()
                break
            }

        }
        return strDoc
    }

    //Determina el id del area segun sea el area
    private fun BuscarCodigoArea(IdArea: String, ListadoArea: ArrayList<AreasC>): Int? {

        for (i in 0 until ListadoArea.size) {
            if(ListadoArea[i].NombreArea!!.equals(IdArea)){
                CodIdArea = ListadoArea[i].IdArea?.toInt() ?: 0
                break
            }

        }
        return CodIdArea
    }

    //Determina el id del Subarea segun sea el area
    private fun BuscarCodigoSubArea(IdSubArea: String, ListadoSubArea: ArrayList<SubAreasC>): Int? {

        for (i in 0 until ListadoSubArea.size) {
            if(ListadoSubArea[i].NombreSubAreaC .equals(IdSubArea)){
                CodIdSubArea = ListadoSubArea[i].IdSubAreaC ?.toInt() ?: 0
                break
            }

        }
        return CodIdSubArea
    }
    private fun Buscarposicion(DesArea: String, listadoAreas: ArrayList<AreasC>):Int  {
        for (i in 0 until listadoAreas.size) {
            if(listadoAreas[i].NombreArea?.equals(DesArea) == true){
                pos = i
                break
            }

        }
        return pos
    }

    private fun BuscarposicionSubAreas(DesSucArea: String, listadoSubAreas: ArrayList<SubAreasC>):Int  {
        for (i in 0 until listadoSubAreas.size) {
            if(listadoSubAreas[i].NombreSubAreaC?.equals(DesSucArea) == true){
                pos = i
                break
            }

        }
        return pos
    }


    private fun BuscarposicionDocumento(DesTipoDoc: String, listadoTipo: ArrayList<TipoDoc>):Int  {
        for (i in 0 until listadoTipo.size) {
            if(listadoTipo[i].TipoDocumento ?.equals(DesTipoDoc) == true){
                pos = i
                break
            }

        }
        return pos
    }
}