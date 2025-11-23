package com.example.diariodeemojis

import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.diariodeemojis.MainActivity
class EventoGuardar(private val activity: MainActivity) : View.OnClickListener {
    override fun onClick(v: View?) {

        val input = activity.findViewById<EditText>(com.example.diariodeemojis.R.id.inputNota)
        val texto = input.text.toString().trim()
        if (texto.isEmpty()) {
            Toast.makeText(activity, "Escribe algo antes de guardar", Toast.LENGTH_SHORT).show()
            return
        }
        activity.agregarNota(texto)
        input.text.clear()

        val root = activity.findViewById<View>(android.R.id.content)

        root.animate()
            .scaleX(1.2f).scaleY(1.5f)
            .setDuration(250)
            .withEndAction {
                root.animate().scaleX(1f).scaleY(1f).start()
            }
    }
}
