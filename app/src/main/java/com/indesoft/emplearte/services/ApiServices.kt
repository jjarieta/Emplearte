package  com.indesoft.emplearte.services

import com.google.gson.JsonObject
import com.indesoft.emplearte.model.Empleados
import com.indesoft.emplearte.model.*


import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*


interface ApiService {



    @GET("/empleados")
    fun fetchAllEmpleadoss() : Call <List<Empleados>>

    @GET("/areas")
    fun getAllAreas() : Call <List<Areas>>

    @Headers("Content-Type: application/json", "Accept: */*")
    @POST("subareas")
    fun getAllSubAreas(@Body body: JsonObject): Call <List<SubAreas>>

    @Headers("Content-Type: application/json", "Accept: */*")
    @POST("empleados")
    fun SendDataEmpleados(@Body body: JsonObject): Call<Empleados>


    @Headers("Content-Type: application/json", "Accept: */*")
    @PUT("actualizarempleado/{id}")
    fun updateEmpleados(@Path("id") id: Int, @Body body: JsonObject?):  Call<ResponseBody>

    @Headers("Content-Type: application/json", "Accept: */*")

    @HTTP(method = "DELETE", path = "/delete/{id}",hasBody = true)
    fun DeleteEmpleados(@Path("id") id: Int, @Body body: JsonObject?):  Call<ResponseBody>


}