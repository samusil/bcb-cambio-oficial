package bo.samuel.bcbmobile.repository

import bo.samuel.bcbmobile.model.TipoCambio
import bo.samuel.bcbmobile.network.RetrofitClient
import retrofit2.Call

class TipoCambioRepository {

    fun obtenerTipoCambio(): Call<TipoCambio> {
        return RetrofitClient.api.obtenerTipoCambio()
    }

}