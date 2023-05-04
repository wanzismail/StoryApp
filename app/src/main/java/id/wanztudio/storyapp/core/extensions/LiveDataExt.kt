package id.wanztudio.storyapp.core.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData


/**
 * @author  Ridwan Ismail <iwanz@pm.me>
 * @version 1
 * @since   Tuesday, 15 Nov 2022
 */

fun <T> LifecycleOwner.observeData(observable: LiveData<T>, observer: (T) -> Unit) {
    observable.observe(this) {
        observer(it)
    }
}

inline fun Boolean.ifTrue(block: () -> Unit) {
    if (this) {
        block()
    }
}