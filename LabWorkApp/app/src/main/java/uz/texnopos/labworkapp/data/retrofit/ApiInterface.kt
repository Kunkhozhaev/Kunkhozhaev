package uz.texnopos.labworkapp.data.retrofit

import retrofit2.Response
import retrofit2.http.*
import uz.texnopos.labworkapp.data.model.Data

interface ApiInterface {

    @GET("/latest/1")
    suspend fun getAllData(
        @Query("json") bool: Boolean
    ): Response<Data>

}