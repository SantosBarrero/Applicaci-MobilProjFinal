package com.example.applicacimobilprojfinal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
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
import com.example.applicacimobilprojfinal.api.Usuari
import com.example.applicacimobilprojfinal.databinding.ActivityPantallaMapsBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.button.MaterialButton
import de.hdodenhof.circleimageview.CircleImageView
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
    private var usuId : Int = 0
    private lateinit var usuari : Usuari
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
        usuari = intent.getParcelableExtra<Usuari>("usuari")!!
        usuId = usuari.usuId!!
        val imatge = findViewById<CircleImageView>(R.id.Imatje)

        val bottomNavigation = findViewById<com.google.android.material.bottomnavigation.BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_location -> {
                    true
                }
                R.id.nav_reserv -> {
                    val intent = Intent(this, PantallaEncargos::class.java)
                    intent.putExtra("usuari", usuari)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

        imatge.setOnClickListener {
            val intent = Intent(this, PantallaPerfil::class.java)
            intent.putExtra("usuari", usuari)
            startActivity(intent)
        }


    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val llistaSucursals = dataApi.getLlistaSucursals()

        launch {
            if (llistaSucursals != null) {
                llistaSucursals.forEach { sucursal ->
                    val resposta: List<CoordenadesResposta>? = dataApiLocations.obtenirCoordenades(
                        "pk.13559dfb90dd28ac41e388d891dd25e8",
                        sucursal.direccio.toString()
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
                            marcador?.tag = sucursal
                        }
                    }
                }
            }
            mMap.setOnMarkerClickListener { marker ->
                val sucu = marker.tag as? Sucursal
                Log.i("Sucurrsal", sucu.toString())
                val sucuId = sucu!!.sucursalId
                val com = dataApi.getComerçId(sucu!!.comerçId)
                if (sucu != null) {
                    mostrarBottomSheet(sucu,com!!.nom.toString(), sucuId, usuId)
                    true
                } else {
                    false
                }
            }
        }
    }
    private fun mostrarBottomSheet(sucu: Sucursal, Nom : String, Id : Int?, usuId: Int) {
        val bottomSheetView = layoutInflater.inflate(R.layout.bottom_sheet, null, false)
        bottomSheetView.findViewById<TextView>(R.id.titol).text = Nom
        bottomSheetView.findViewById<TextView>(R.id.carrer).text = sucu.direccio

        val bottomSheetDialog = com.google.android.material.bottomsheet.BottomSheetDialog(this)
        bottomSheetDialog.setContentView(bottomSheetView)
        bottomSheetDialog.show()

        val boto = bottomSheetView.findViewById<Button>(R.id.boto_productes)
        if (boto == null) {
            Log.e("PantallaMaps", "boto_productes és null!")
        } else {
            boto.setOnClickListener {
                val intent = Intent(this, PantallaProductos::class.java)
                intent.putExtra("sucursalId", Id)
                intent.putExtra("usuari", usuId)
                startActivity(intent)
                bottomSheetDialog.dismiss()
            }
        }

    }
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job
}