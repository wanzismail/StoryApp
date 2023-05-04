package id.wanztudio.storyapp.core.widgets

import android.content.Context
import android.util.AttributeSet
import android.util.Patterns
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.widget.addTextChangedListener
import id.wanztudio.storyapp.R


/**
 * @author  Ridwan Ismail <iwanz@pm.me>
 * @version 1
 * @since   Tuesday, 15 Nov 2022
 */

class EmailEditText(context: Context, attributeSet: AttributeSet) :
    AppCompatEditText(context, attributeSet) {

    init {
        addTextChangedListener {
            it?.let { text ->
                if (text.isNotEmpty()) {
                    this.error = if (!Patterns.EMAIL_ADDRESS.matcher(text).matches()) {
                        context.getString(R.string.email_not_valid_error_caption)
                    } else null
                }
            }
        }
    }

}