package bo.samuel.bcbmobile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import bo.samuel.bcbmobile.model.TipoCambio
import bo.samuel.bcbmobile.repository.TipoCambioRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TipoCambioViewModel : ViewModel() {

    private val repository = TipoCambioRepository()

    private val _tipoCambio = MutableLiveData<TipoCambio>()
    val tipoCambio: LiveData<TipoCambio> = _tipoCambio

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun actualizarTipoCambio() {

        _loading.value = true

        repository.obtenerTipoCambio().enqueue(object : Callback<TipoCambio> {

            override fun onResponse(
                call: Call<TipoCambio>,
                response: Response<TipoCambio>
            ) {

                _loading.value = false

                if (response.isSuccessful && response.body() != null) {

                    _tipoCambio.value = response.body()

                } else {

                    _error.value = "Error obteniendo datos"

                }

            }

            override fun onFailure(call: Call<TipoCambio>, t: Throwable) {

                _loading.value = false

                _error.value = "Sin conexión"

            }

        })
    }

}