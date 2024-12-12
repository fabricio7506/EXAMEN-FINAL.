//Esta aplicación diseñada para poder ver si tu capacidad mental cuanto puede almacenar
// en imagenes, aparte de ello se agregó una canción para cada partida llamada "coincidence"
// ya que aparte de ser algo relajante trae algo de sentido con el juego, se eligio esa
// canción para salir de lo habitual como lo son jazz o canciones de puro sonido instrumental.
// para este proyecto el equipo encargado es:
// - FABRICIO GABRIEL CARRAZCO ARANA
// - ARNOLD DANIEL SAYA RAMOS
// - LUIGUI ALEXANDER HUANCA CAPIRA
// FECHA DE INICIO: 05/12/2024 HORA: 4:15am.
// FECHA DE MODIFICACION: 12/12/2024 HORA: 1:20am.

package com.example.pamyat

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Configura el layout principal

        // Referencias a los botones en el layout
        val btn2x2 = findViewById<Button>(R.id.btn2x2)
        val btn4x4 = findViewById<Button>(R.id.btn4x4)
        val btn6x6 = findViewById<Button>(R.id.btn6x6)

        // Inicializa el reproductor de sonido
        val mediaPlayer = MediaPlayer.create(this, R.raw.button_click)

        // Listener para el botón "Tablero 2x2"
        btn2x2.setOnClickListener {
            mediaPlayer.start() // Reproduce el sonido al hacer clic
            it.animate().scaleX(0.9f).scaleY(0.9f).setDuration(100).withEndAction { // Animación de escalado
                it.animate().scaleX(1f).scaleY(1f).setDuration(100).start() // Vuelve al tamaño original
                val intent = Intent(this, GameActivity::class.java) // Crea un intent para abrir GameActivity
                intent.putExtra("GRID_SIZE", 2) // Pasa el tamaño de la cuadrícula (2x2)
                startActivity(intent) // Inicia GameActivity
            }.start()
        }

        // Listener para el botón "Tablero 4x4"
        btn4x4.setOnClickListener {
            mediaPlayer.start() // Reproduce el sonido al hacer clic
            it.animate().scaleX(0.9f).scaleY(0.9f).setDuration(100).withEndAction { // Animación de escalado
                it.animate().scaleX(1f).scaleY(1f).setDuration(100).start() // Vuelve al tamaño original
                val intent = Intent(this, GameActivity::class.java) // Crea un intent para abrir GameActivity
                intent.putExtra("GRID_SIZE", 4) // Pasa el tamaño de la cuadrícula (4x4)
                startActivity(intent) // Inicia GameActivity
            }.start()
        }

        // Listener para el botón "Tablero 6x6"
        btn6x6.setOnClickListener {
            mediaPlayer.start() // Reproduce el sonido al hacer clic
            it.animate().scaleX(0.9f).scaleY(0.9f).setDuration(100).withEndAction { // Animación de escalado
                it.animate().scaleX(1f).scaleY(1f).setDuration(100).start() // Vuelve al tamaño original
                val intent = Intent(this, GameActivity::class.java) // Crea un intent para abrir GameActivity
                intent.putExtra("GRID_SIZE", 6) // Pasa el tamaño de la cuadrícula (6x6)
                startActivity(intent) // Inicia GameActivity
            }.start()
        }
    }
}
