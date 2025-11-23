package com.example.diariodeemojis

import android.animation.ValueAnimator
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.diariodeemojis.*
import com.google.android.filament.View

class MainActivity : AppCompatActivity() {

    private lateinit var rootLayout: LinearLayout
    private lateinit var imagenEmocion: ImageView
    private lateinit var inputNota: EditText
    private lateinit var btnGuardar: Button
    private lateinit var btnGrabar: Button
    private lateinit var btnReproducir: Button
    private lateinit var btnFeliz: Button
    private lateinit var btnTriste: Button
    private lateinit var btnRelajado: Button
    private lateinit var btnEnojado: Button
    private lateinit var listaNotas: LinearLayout
    private lateinit var audioController: AudioController
    private lateinit var animacionEmocion: AnimacionEmocion
    private var emocionActual: String = "happy"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // ---- Vincular vistas ----
        rootLayout = findViewById(R.id.rootLayout)
        imagenEmocion = findViewById(R.id.imagenEmocion)
        inputNota = findViewById(R.id.inputNota)
        btnGuardar = findViewById(R.id.btnGuardar)
        btnGrabar = findViewById(R.id.btnGrabar)
        btnReproducir = findViewById(R.id.btnReproducir)
        btnFeliz = findViewById(R.id.btnFeliz)
        btnTriste = findViewById(R.id.btnTriste)
        btnRelajado = findViewById(R.id.btnRelajado)
        btnEnojado = findViewById(R.id.btnEnojado)
        listaNotas = findViewById(R.id.listaNotas)
        // ---- Inicializar controladores ----
        audioController = AudioController(this)
        animacionEmocion = AnimacionEmocion()
        // ---- Listeners ----
        // Animar imagen al tocar
        imagenEmocion.setOnClickListener {
            animacionEmocion.animarImagen(imagenEmocion)
            // Opcional: reproducir sonido de la emoción al tocar la imagen
            audioController.reproducirSonidoEmocion(emocionActual)
        }
        // Cambiar emociones
        btnFeliz.setOnClickListener { cambiarEmocion("happy") }
        btnTriste.setOnClickListener { cambiarEmocion("sad") }
        btnRelajado.setOnClickListener { cambiarEmocion("calm") }
        btnEnojado.setOnClickListener { cambiarEmocion("angry") }
        // Guardar nota
        btnGuardar.setOnClickListener(EventoGuardar(this))
        // Grabar audio
        btnGrabar.setOnClickListener { audioController.grabarAudio() }
        btnGrabar.setOnLongClickListener {
            audioController.detenerGrabacion()
            true
        }
        // Reproducir audio grabado
        btnReproducir.setOnClickListener(EventoReproducirAudio(audioController))
    }
    // ---- Cambiar emoción ----
    private fun cambiarEmocion(emocion: String) {
        emocionActual = emocion

        val bgColor = when(emocion) {
            "happy" -> Color.parseColor("#FFF59D")   // amarillo
            "sad" -> Color.parseColor("#90CAF9")     // azul
            "calm" -> Color.parseColor("#A5D6A7")    // verde suave
            "angry" -> Color.parseColor("#EF9A9A")   // rojo
            else -> Color.WHITE
        }


        val currentColor = (rootLayout.background as? ColorDrawable)?.color ?: Color.WHITE

        val animator = ValueAnimator.ofArgb(currentColor, bgColor).apply {
            duration = 500
            addUpdateListener { valueAnimator ->
                rootLayout  .setBackgroundColor(valueAnimator.animatedValue as Int)
            }
        }
        animator.start()

        val resId = when (emocion) {
            "happy" -> R.drawable.happy
            "sad" -> R.drawable.sad
            "calm" -> R.drawable.calm
            "angry" -> R.drawable.angry
            else -> R.drawable.happy
        }
        imagenEmocion.setImageResource(resId)
        animacionEmocion.animarImagen(imagenEmocion)
        audioController.reproducirSonidoEmocion(emocion)
    }
    // ---- Agregar nota debajo ----
    fun agregarNota(texto: String) {
        val itemView = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            setPadding(8, 8, 8, 8)
        }
        val imgView = ImageView(this).apply {
            val resId = when (emocionActual) {
                "happy" -> R.drawable.happy
                "sad" -> R.drawable.sad
                "calm" -> R.drawable.calm
                "angry" -> R.drawable.angry
                else -> R.drawable.happy
            }
            setImageResource(resId)
            layoutParams = LinearLayout.LayoutParams(120, 120)
        }
        val txtView = TextView(this).apply {
            text = texto
            textSize = 18f
            setPadding(16, 16, 16, 16)
        }
        itemView.addView(imgView)
        itemView.addView(txtView)
        listaNotas.addView(itemView)
    }
}