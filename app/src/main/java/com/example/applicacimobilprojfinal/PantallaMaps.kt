package com.example.applicacimobilprojfinal

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.applicacimobilprojfinal.api.Comerc
import com.example.applicacimobilprojfinal.api.CoordenadesResposta
import com.example.applicacimobilprojfinal.api.DataApi
import com.example.applicacimobilprojfinal.api.DataApiLocations
import com.example.applicacimobilprojfinal.api.Sucursal
import com.example.applicacimobilprojfinal.databinding.ActivityPantallaMapsBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class PantallaMaps : AppCompatActivity(), OnMapReadyCallback, CoroutineScope {
    private var job = Job()
    lateinit var mMap: GoogleMap
    lateinit var binding : ActivityPantallaMapsBinding
    private val dataApiLocations: DataApiLocations = DataApiLocations()
    private val dataApi: DataApi = DataApi()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPantallaMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val comerc = dataApi.getLlistaSucursals()
        val comercs = Comerc("Vallbona", "Carrer del Camp de les Moreres, 14, 08401 Granollers, Barcelona")


        launch {
            val resposta: List<CoordenadesResposta>? = dataApiLocations.obtenirCoordenades(
                "pk.13559dfb90dd28ac41e388d891dd25e8",
                comerc!![0].direccio.toString()
            )

            if (!resposta.isNullOrEmpty()) {
                val coordenada = resposta[0]

                val lat = coordenada.lat.toDoubleOrNull()
                val lon = coordenada.lon.toDoubleOrNull()

                if (lat != null && lon != null) {
                    val ubicacio = LatLng(lat, lon)

                    val marcador = mMap.addMarker(
                        MarkerOptions()
                            .position(ubicacio)
                            .title("Prova")
                    )
                    marcador?.tag = comerc[0]

                    mMap.moveCamera(
                        com.google.android.gms.maps.CameraUpdateFactory.newLatLngZoom(
                            ubicacio,
                            15f
                        )
                    )

                    mMap.setOnMarkerClickListener { marker ->
                        val comerci = marker.tag as? Sucursal
                        if (comerci != null) {
                            mostrarBottomSheet(comerci)
                            true
                        } else {
                            false
                        }
                    }
                } else {
                    Log.e("MAPA", "Lat o Lon no són vàlids")
                }
            } else {
                Log.e("MAPA", "No s'han obtingut coordenades")
            }
        }
    }


        private fun mostrarBottomSheet(sucu: Sucursal) {
        val bottomSheetView = layoutInflater.inflate(R.layout.bottom_sheet, null, false)
        bottomSheetView.findViewById<TextView>(R.id.titol).text = "Prova"
        bottomSheetView.findViewById<TextView>(R.id.carrer).text = sucu.direccio

        val bottomSheetDialog = com.google.android.material.bottomsheet.BottomSheetDialog(this)
        bottomSheetDialog.setContentView(bottomSheetView)
        bottomSheetDialog.show()
    }




    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job
}