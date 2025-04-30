package com.example.applicacimobilprojfinal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.applicacimobilprojfinal.api.DataApi
import com.example.applicacimobilprojfinal.api.Usuari
import com.example.applicacimobilprojfinal.databinding.ActivityPantallaRegistreBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PantallaRegistre : AppCompatActivity() {
    lateinit var binding: ActivityPantallaRegistreBinding
    lateinit var nouUsuari: Usuari
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPantallaRegistreBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        nouUsuari = Usuari(null,null,null,null,null)

        binding.btnRegistre.setOnClickListener {
            val nomUsu = binding.editTextNomUsuari.text.toString()
            val correu = binding.editTextCorreu.text.toString()
            val password = binding.editTextPassword.text.toString()
            val passwordConfirmacio = binding.editTextConfirmPassword.text.toString()


            if (nomUsu.isNotEmpty()) {
                if (correu.isNotEmpty()) {
                    if (password.isNotEmpty()) {
                        if (passwordConfirmacio.isNotEmpty()) {
                            if (password == passwordConfirmacio) {
                                nouUsuari = Usuari(
                                    usuId = 0,
                                    nomUsuari = nomUsu,
                                    correu = correu,
                                    contrasenya = password,
                                    rol = "Client",
                                )

                                val dataApi = DataApi()
                                CoroutineScope(Dispatchers.Main).launch {
                                    val resultat = dataApi.insertarUsuari(nouUsuari)
                                    if (resultat) {
                                        Toast.makeText(this@PantallaRegistre, "Usuari registrat correctament!", Toast.LENGTH_SHORT).show()
                                        val intent = Intent(this@PantallaRegistre, PantallaMaps::class.java)
                                        startActivity(intent)
                                        Log.i("Usuari", nouUsuari.toString())
                                    } else {
                                        Toast.makeText(this@PantallaRegistre, "Error en registrar l'usuari", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            } else {
                                Toast.makeText(this, "Les contrasenyes no són iguals", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(this, "Confirma la contrasenya", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this, "Introdueix una contrasenya", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText( this, "Introdueix un correu", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Introdueix un nom d'usuari", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnLogin.setOnClickListener {
            val intent = Intent(this@PantallaRegistre, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
