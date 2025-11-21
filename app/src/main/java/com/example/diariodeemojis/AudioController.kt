package com.example.diariodeemojis

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.diariodeemojis.R
class AudioController(private val activity: Activity) {
    private var grabador: MediaRecorder? = null
    private var reproductor: MediaPlayer? = null
    private var archivoSalida: String = "${activity.externalCacheDir?.absolutePath}/grabacion.3gp"
    companion object {
        private const val REQUEST_MIC_PERMISSION = 101
    }
    // Verificar permiso del micrófono
    private fun verificarPermisoMic(): Boolean {
        return if (ContextCompat.checkSelfPermission(activity, Manifest.permission.RECORD_AUDIO)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.RECORD_AUDIO),
                REQUEST_MIC_PERMISSION
            )
            false
        } else true
    }
    // Reproducir sonido según emoción (happy, sad...)
    fun reproducirSonidoEmocion(emocion: String) {
        val sonidoId = when (emocion) {
            "happy" -> R.raw.happy_sound
            "sad" -> R.raw.sad_sound
            "calm" -> R.raw.calm_sound
            "angry" -> R.raw.angry_sound
            else -> R.raw.happy_sound
        }
        try {
            reproductor?.release()
            reproductor = MediaPlayer.create(activity, sonidoId)
            reproductor?.start()
        } catch (e: Exception) {
            Toast.makeText(activity, "Error al reproducir sonido: ${e.message}",
                Toast.LENGTH_SHORT).show()
        }
    }
    // Grabar audio del usuario
    fun grabarAudio() {
        if (!verificarPermisoMic()) {
            Toast.makeText(activity, "Permiso de micrófono requerido", Toast.LENGTH_SHORT).show()
            return
        }
        try {
            grabador?.release()
            grabador = MediaRecorder().apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
                setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
                setOutputFile(archivoSalida)
                prepare()
                start()
            }
            Toast.makeText(activity, " Grabando...", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(activity, "Error al grabar: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
    // Detener la grabación
    fun detenerGrabacion() {
        try {
            grabador?.apply {
                stop()
                release()
            }
            grabador = null
            Toast.makeText(activity, " Grabación finalizada", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(activity, "Error al detener: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
    // Reproducir grabación guard
    fun reproducirAudioGrabado() {
        try {
            reproductor?.release()
            reproductor = MediaPlayer().apply {
                setDataSource(archivoSalida)
                prepare()
                start()
            }
            Toast.makeText(activity, " Reproduciendo grabación...", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(activity, "Error al reproducir grabación: ${e.message}",
                Toast.LENGTH_SHORT).show()
        }
    }
}