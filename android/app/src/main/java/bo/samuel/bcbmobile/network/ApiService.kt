package bo.samuel.bcbmobile.network

import bo.samuel.bcbmobile.model.TipoCambio
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("api/tipo-cambio")
    fun obtenerTipoCambio(): Call<TipoCambio>

}