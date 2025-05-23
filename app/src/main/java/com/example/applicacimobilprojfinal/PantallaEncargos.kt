package com.example.applicacimobilprojfinal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.applicacimobilprojfinal.adapters.ProducteAdapter
import com.example.applicacimobilprojfinal.api.DataApi
import com.example.applicacimobilprojfinal.api.Usuari
import com.example.applicacimobilprojfinal.databinding.ActivityPantallaEncargosBinding

class PantallaEncargos : AppCompatActivity() {
    lateinit var binding: ActivityPantallaEncargosBinding
    private lateinit var usuari : Usuari
    private var dataApi : DataApi = DataApi()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPantallaEncargosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        usuari = intent.getParcelableExtra<Usuari>("usuari")!!

        var encarrecs = dataApi.getEncarrecsId(usuari.usuId)


        val adapter = EncarrecsAdaptador(encarrecs)
        binding.rcvEncarrg.layoutManager = LinearLayoutManager(this)
        binding.rcvEncarrg.adapter = adapter

        val bottomNavigation = findViewById<com.google.android.material.bottomnavigation.BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_location -> {
                    val intent = Intent(this, PantallaMaps::class.java)
                    intent.putExtra("usuari", usuari)
                    startActivity(intent)
                    true
                }
                R.id.nav_reserv -> {
                    true
                }
                else -> false
            }
        }
    }
}