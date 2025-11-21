package com.example.diariodeemojis

import android.view.View
import android.view.animation.AnimationUtils

class AnimacionEmocion {
    fun animarImagen(view: View) {
        val anim = AnimationUtils.loadAnimation(view.context, android.R.anim.fade_in)
        view.startAnimation(anim)
    }
}
