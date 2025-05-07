package com.example.applicacimobilprojfinal

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.applicacimobilprojfinal.api.DataApi
import com.example.applicacimobilprojfinal.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val dataApi = DataApi()
        binding.btnLogin.setOnClickListener() {
            val correuOUsuari = binding.editTextEmail.text.toString()
            val contrasenya = binding.editTextPassword.text.toString()

            if (correuOUsuari.isNotEmpty()) {
                if (contrasenya.isNotEmpty()) {
                    CoroutineScope(Dispatchers.Main).launch {
                        val usuaris = dataApi.getLlistaUsuaris()
                        if (usuaris != null) {
                            val usuariTrobat = usuaris.find { (it.correu == correuOUsuari || it.nomUsuari == correuOUsuari) && it.contrasenya == contrasenya }
                            if (usuariTrobat != null) {
                                val intent = Intent(this@MainActivity, PantallaMaps::class.java)
                                startActivity(intent)
                            } else {
                                Toast.makeText(this@MainActivity, "Usuari o contrasenya incorrectes", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(this@MainActivity, "Error carregant usuaris", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(this, "La contrasenya no existeix", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "El correu o l'usuari no existeix", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnLogin2.setOnClickListener(){

            val intent = Intent(this@MainActivity, PantallaRegistre::class.java)
            startActivity(intent)

        }
    }
}
