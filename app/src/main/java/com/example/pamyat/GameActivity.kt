package com.example.pamyat
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog

import android.graphics.Color
import android.os.Bundle
import android.widget.GridLayout
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class GameActivity : AppCompatActivity() {

    private lateinit var gridLayout: GridLayout
    private lateinit var buttons: Array<ImageButton>
    private var images: MutableList<Int> = mutableListOf()
    private var firstSelection: Int? = null
    private var secondSelection: Int? = null
    private var isBusy = false
    private var attempts = 0 // Contador de intentos
    private var matches = 0 // Contador de aciertos


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        // Configurar el GridLayout
        gridLayout = findViewById(R.id.gridLayout)
        val gridSize = intent.getIntExtra("GRID_SIZE", 3) // Obtener tamaño del tablero
        setupGame(gridSize)
    }

    private fun setupGame(gridSize: Int) {
        val totalCards = gridSize * gridSize // Calcula el número total de tarjetas
        val pairs = totalCards / 2 // Calcula el número de pares necesarios

        // Configura el GridLayout
        gridLayout.columnCount = gridSize
        gridLayout.rowCount = gridSize
        buttons = Array(totalCards) { ImageButton(this) } // Crea los botones
        images = loadImages(pairs) // Carga las imágenes necesarias

        // Duplica y mezcla las imágenes
        val shuffledImages = (images + images).shuffled()

        // Configura cada botón del tablero
        for (i in buttons.indices) {
            val button = buttons[i]
            button.layoutParams = GridLayout.LayoutParams().apply {
                width = 0
                height = 0
                columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
            }
            button.scaleType = ImageView.ScaleType.CENTER_CROP // Escala uniforme
            button.setImageResource(shuffledImages[i]) // Muestra temporalmente la imagen
            gridLayout.addView(button)
            buttons[i] = button
        }

        // Después de 3 segundos, voltea todas las tarjetas
        gridLayout.postDelayed({
            for (i in buttons.indices) {
                buttons[i].setImageResource(R.drawable.card_back) // Cambia a la parte trasera
            }
        }, 3000) // 2000 ms = 3 segundos

        // Asigna el evento de clic después de que las tarjetas se hayan volteado
        for (i in buttons.indices) {
            buttons[i].setOnClickListener { onCardClick(i, shuffledImages[i]) }
        }
    }


    private fun loadImages(totalPairs: Int): MutableList<Int> {
        val availableImages = mutableListOf(
            R.drawable.image1,
            R.drawable.image2,
            R.drawable.image3,
            R.drawable.image4,
            R.drawable.image5,
            R.drawable.image6,
            R.drawable.image7,
            R.drawable.image8,
            R.drawable.image9,
            R.drawable.image10,
            R.drawable.image11,
            R.drawable.image12,
            R.drawable.image13,
            R.drawable.image14,
            R.drawable.image15,
            R.drawable.image16,
            R.drawable.image17,
            R.drawable.image18
        )

        if (totalPairs > availableImages.size) {
            throw IllegalArgumentException("No hay suficientes imágenes para $totalPairs pares.")
        }

        return availableImages.take(totalPairs).toMutableList()
    }




    private fun onCardClick(index: Int, image: Int) {
        try {
            if (isBusy || buttons[index].tag == "matched") return

            buttons[index].setImageResource(image)
            if (firstSelection == null) {
                firstSelection = index
            } else if (secondSelection == null) {
                secondSelection = index
                checkForMatch()
            }
        } catch (e: IndexOutOfBoundsException) {
            e.printStackTrace() // Registra el error en Logcat
            resetSelections()   // Limpia las selecciones para evitar inconsistencias
        }
    }


    private fun checkForMatch() {
        isBusy = true
        attempts++ // Incrementa los intentos

        if (buttons[firstSelection!!].drawable.constantState ==
            buttons[secondSelection!!].drawable.constantState) {
            // Si coinciden
            buttons[firstSelection!!].tag = "matched"
            buttons[secondSelection!!].tag = "matched"
            matches++ // Incrementa los aciertos
            // Se Verifica si se completó el juego
            if (matches == images.size) {
                showGameOverDialog()
            }

            resetSelections()
        } else {
            // Aqui si no coinciden, vuelve a mostrar el reverso después de un retraso
            buttons[firstSelection!!].postDelayed({
                buttons[firstSelection!!].setImageResource(R.drawable.card_back)
                buttons[secondSelection!!].setImageResource(R.drawable.card_back)
                resetSelections()
            }, 1000)
        }
    }

    private fun showGameOverDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("¡JUEGO TERMINADO!")
        builder.setMessage("INTENTOS: $attempts\nACIERTOS: $matches")
        builder.setPositiveButton("Te gustaría Jugar de nuevo?") { _, _ ->
            recreate() // Reinicia la actividad para jugar de nuevo
        }
        builder.setNegativeButton("VAMOS AL MENÚ!") { _, _ ->
            finish() // Cierra la actividad
        }
        builder.setCancelable(false)
        builder.show()
    }


    private fun resetSelections() {
        firstSelection = null
        secondSelection = null
        isBusy = false
    }
}
