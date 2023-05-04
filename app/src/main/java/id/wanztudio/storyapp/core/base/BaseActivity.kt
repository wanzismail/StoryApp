package id.wanztudio.storyapp.core.base

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import id.wanztudio.storyapp.R
import id.wanztudio.storyapp.core.models.CustomError


/**
 * @author  Ridwan Ismail <iwanz@pm.me>
 * @version 1
 * @since   Tuesday, 15 Nov 2022
 */

abstract class BaseActivity<VB : ViewBinding>(
    private val inflate: (LayoutInflater) -> VB,
) : AppCompatActivity() {

    protected val binding: VB by lazy {
        inflate.invoke(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        binding.initialBinding()
    }

    open fun VB.initialBinding() {}

    protected fun checkPermission(permission: String, granted: (Boolean) -> Unit) {
        granted.invoke(
            ContextCompat.checkSelfPermission(
                this,
                permission
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    protected fun checkPermission(permission: String): Boolean = ContextCompat.checkSelfPermission(
        this,
        permission
    ) == PackageManager.PERMISSION_GRANTED

    private fun showMessage(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    protected fun showMessage(error: CustomError) {
        when (error) {
            is CustomError.Text -> {
                showMessage(error.value)
            }
            is CustomError.Resource -> {
                showMessage(getString(error.id))
            }
        }
    }

    protected infix fun View.redirectTo(destination: Class<*>) {
        setOnClickListener {
            startActivity(
                Intent(this@BaseActivity, destination)
            )
            overridePendingTransition(
                R.anim.transition_fade_in, R.anim.transition_fade_out
            )
        }
    }

    protected fun Class<*>.start() {
        startActivity(
            Intent(this@BaseActivity, this).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
        )
        overridePendingTransition(
            R.anim.transition_fade_in, R.anim.transition_fade_out
        )
        finish()
    }

    protected fun Class<*>.startWithStack() {
        startActivity(
            Intent(this@BaseActivity, this)
        )
        overridePendingTransition(
            R.anim.transition_fade_in, R.anim.transition_fade_out
        )
    }
}