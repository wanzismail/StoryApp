package id.wanztudio.storyapp.data.datasources.local

import kotlinx.coroutines.flow.Flow


/**
 * @author  Ridwan Ismail <iwanz@pm.me>
 * @version 1
 * @since   Tuesday, 15 Nov 2022
 */

interface SharedPreferences {

    val token: Flow<String>

    suspend fun saveToken(token: String)
}