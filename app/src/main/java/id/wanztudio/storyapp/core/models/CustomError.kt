package id.wanztudio.storyapp.core.models

import androidx.annotation.StringRes


/**
 * @author  Ridwan Ismail <iwanz@pm.me>
 * @version 1
 * @since   Tuesday, 15 Nov 2022
 */

sealed class CustomError {
    data class Text(val value: String?) : CustomError()
    data class Resource(@StringRes val id: Int) : CustomError()
}