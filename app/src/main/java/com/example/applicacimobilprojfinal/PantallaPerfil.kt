package com.example.applicacimobilprojfinal

import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.applicacimobilprojfinal.api.Usuari
import com.example.applicacimobilprojfinal.databinding.ActivityPantallaPerfilBinding
import de.hdodenhof.circleimageview.CircleImageView

class PantallaPerfil : AppCompatActivity() {
    private lateinit var binding: ActivityPantallaPerfilBinding
    private lateinit var usuari: Usuari
    private lateinit var imageView: CircleImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPantallaPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        usuari = intent.getParcelableExtra("usuari")!!

        binding.UsuTextV.text = usuari.nomUsuari
        binding.CorreuTextV.text = usuari.correu
        binding.ContraTextV.setText(usuari.contrasenya)

        var contrasenyaVisible = false

        imageView = findViewById(R.id.FDP)

        imageView.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        binding.ojo.setOnClickListener {
            contrasenyaVisible = !contrasenyaVisible
            if (contrasenyaVisible) {
                binding.ContraTextV.inputType = android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                binding.ojo.setImageResource(R.drawable.outline_remove_red_eye_24)
            } else {
                binding.ContraTextV.inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
                binding.ojo.setImageResource(R.drawable.baseline_remove_red_eye_24)
            }
        }
    }

    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            imageView.setImageURI(it)
        }
    }
}
