package id.wanztudio.storyapp.data.models.request


/**
 * @author  Ridwan Ismail <iwanz@pm.me>
 * @version 1
 * @since   Tuesday, 15 Nov 2022
 */

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
)