package id.wanztudio.storyapp.core.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * @author  Ridwan Ismail <iwanz@pm.me>
 * @version 1
 * @since   Tuesday, 15 Nov 2022
 */

class ApiServiceProvider(
    private val baseURL: String, // do implementation multiple base url
    private val client: OkHttpClient,
) {
    fun <T> createService(clazz: Class<T>): T {
        return Retrofit.Builder()
            .baseUrl(baseURL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(clazz)
    }
}