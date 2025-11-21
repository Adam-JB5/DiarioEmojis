package com.example.diariodeemojis

import android.view.View
class EventoReproducirAudio(
    private val audioController: AudioController
) : View.OnClickListener {
    override fun onClick(v: View?) {
        audioController.reproducirAudioGrabado()
    }
}
