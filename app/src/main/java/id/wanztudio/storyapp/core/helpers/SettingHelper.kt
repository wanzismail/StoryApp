package id.wanztudio.storyapp.core.helpers

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.appcompat.widget.AppCompatButton
import androidx.core.widget.PopupWindowCompat
import id.wanztudio.storyapp.R


/**
 * @author  Ridwan Ismail <iwanz@pm.me>
 * @version 1
 * @since   Wednesday, 16 Nov 2022
 */

object SettingHelper {

    fun open(context: Context, anchor: View, callback: Callback) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = inflater.inflate(R.layout.settings_popup_menu, null).apply {
            findViewById<AppCompatButton>(R.id.button_language).setOnClickListener { callback.onLanguageSelected() }
            findViewById<AppCompatButton>(R.id.button_logout).setOnClickListener { callback.onLogoutSelected() }
        }

        val popupWindow = PopupWindow(popupView,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            true)
        popupWindow.elevation = 10f

        PopupWindowCompat.showAsDropDown(popupWindow, anchor, 0, 0, Gravity.CENTER)
        PopupWindowCompat.setWindowLayoutType(popupWindow,
            WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN)
    }

    interface Callback {
        fun onLanguageSelected()
        fun onLogoutSelected()
    }
}


