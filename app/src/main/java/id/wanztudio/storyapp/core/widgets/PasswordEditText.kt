package id.wanztudio.storyapp.core.widgets

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.widget.addTextChangedListener
import id.wanztudio.storyapp.R


/**
 * @author  Ridwan Ismail <iwanz@pm.me>
 * @version 1
 * @since   Tuesday, 15 Nov 2022
 */

class PasswordEditText(context: Context, attributeSet: AttributeSet) :
    AppCompatEditText(context, attributeSet) {

    init {
        addTextChangedListener {
            it?.let { text ->
                if (text.isNotEmpty()) {
                    this.error = if (text.length < MIN_LENGTH) {
                        context.getString(R.string.password_error_caption)
                    } else null
                }
            }
        }
    }

    companion object {
        const val MIN_LENGTH = 8
    }
}