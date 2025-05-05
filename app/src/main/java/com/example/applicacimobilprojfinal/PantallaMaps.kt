package com.example.applicacimobilprojfinal

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.applicacimobilprojfinal.databinding.ActivityPantallaMapsBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class PantallaMaps : AppCompatActivity(), OnMapReadyCallback, CoroutineScope {
    private var job = Job()
    lateinit var mMap: GoogleMap
    lateinit var binding : ActivityPantallaMapsBinding
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

        val ubicacio = LatLng(41.60034274245597, 2.2827648723099085)

        val marcador=  mMap.addMarker(
            MarkerOptions()
                .position(ubicacio)
                .title("Tres Torres")
        )


        mMap.moveCamera(com.google.android.gms.maps.CameraUpdateFactory.newLatLngZoom(ubicacio, 15f))
        mMap.setOnMarkerClickListener { marker ->
            if (marker == marcador) {
                mostrarBottomSheet(marker.title ?: "")
                true
            } else {
                false
            }
        }
    }

    private fun mostrarBottomSheet(titol: String) {
        val bottomSheetView = layoutInflater.inflate(R.layout.bottom_sheet, null, false)
        bottomSheetView.findViewById<TextView>(R.id.titol).text = titol
        val bottomSheetDialog = com.google.android.material.bottomsheet.BottomSheetDialog(this)
        bottomSheetDialog.setContentView(bottomSheetView)
        bottomSheetDialog.show()
    }



    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job
}