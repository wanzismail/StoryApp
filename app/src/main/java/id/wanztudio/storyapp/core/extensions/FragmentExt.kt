package id.wanztudio.storyapp.core.extensions

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment


/**
 * @author  Ridwan Ismail <iwanz@pm.me>
 * @version 1
 * @since   Tuesday, 15 Nov 2022
 */

fun Fragment.navigateTo(destination: Int) {
    try {
        NavHostFragment.findNavController(this).navigate(destination)
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
    }
}

fun Fragment.back() {
    NavHostFragment.findNavController(this).popBackStack()
}
