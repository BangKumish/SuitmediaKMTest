package id.bangkumis.suitmediakmtest.network

import id.bangkumis.suitmediakmtest.model.Response
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ApiService {
    @GET("api/users")
    fun getAllUser(
        @QueryMap parameter: HashMap<String, String>
    ): Call<Response>
}