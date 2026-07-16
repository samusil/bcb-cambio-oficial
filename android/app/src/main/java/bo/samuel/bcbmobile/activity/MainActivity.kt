package bo.samuel.bcbmobile.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import bo.samuel.bcbmobile.R
import bo.samuel.bcbmobile.model.TipoCambio
import bo.samuel.bcbmobile.repository.TipoCambioRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val repository = TipoCambioRepository()

    private lateinit var txtTitulo: TextView
    private lateinit var txtValor: TextView
    private lateinit var txtFecha: TextView
    private lateinit var txtFuente: TextView

    private lateinit var btnActualizar: Button
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )
            insets
        }

        txtTitulo = findViewById(R.id.txtTitulo)
        txtValor = findViewById(R.id.txtValor)
        txtFecha = findViewById(R.id.txtFecha)
        txtFuente = findViewById(R.id.txtFuente)

        btnActualizar = findViewById(R.id.btnActualizar)
        progressBar = findViewById(R.id.progressBar)

        cargarTipoCambio()

        btnActualizar.setOnClickListener {
            cargarTipoCambio()
        }
    }

    private fun cargarTipoCambio() {

        progressBar.visibility = View.VISIBLE
        btnActualizar.isEnabled = false

        repository.obtenerTipoCambio().enqueue(object : Callback<TipoCambio> {

            override fun onResponse(
                call: Call<TipoCambio>,
                response: Response<TipoCambio>
            ) {

                progressBar.visibility = View.GONE
                btnActualizar.isEnabled = true

                if (response.isSuccessful && response.body() != null) {

                    val tipoCambio = response.body()!!

                    txtTitulo.text = tipoCambio.titulo
                    txtValor.text = "Bs ${tipoCambio.valor}"
                    txtFecha.text = "📅 Última actualización\n${tipoCambio.fecha}"
                    txtFuente.text = "🏦 Fuente\n${tipoCambio.fuente}"

                    Log.i("BCB", "Tipo de cambio actualizado correctamente.")

                } else {

                    txtValor.text = "--,--"
                    txtFecha.text = ""
                    txtFuente.text = ""

                    Log.e("BCB", "Respuesta inválida del servidor.")
                }
            }

            override fun onFailure(
                call: Call<TipoCambio>,
                t: Throwable
            ) {
                progressBar.visibility = View.GONE
                btnActualizar.isEnabled = true

                btnActualizar.text = "Actualizar tipo de cambio"

                txtTitulo.text = "No fue posible obtener información"
                txtValor.text = "--,--"
                txtFecha.text = "Verifique su conexión"
                txtFuente.text = "Intente nuevamente"

                Log.e("BCB", "Error: ${t.message}")
            }
        })
    }
}