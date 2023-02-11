package uz.gita.my2048game.utils

import android.widget.Toast
import androidx.fragment.app.Fragment
import timber.log.Timber

fun timber(message: String, tag: String = "TTT") {
    Timber.tag(tag).d(message)
}

fun Fragment.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(requireContext(), message, duration).show()
}

fun List<Int>.show(): String {
    val sb = StringBuilder()
    for (i in this.indices)
        sb.append("${this[i]}")
    return sb.toString()
}