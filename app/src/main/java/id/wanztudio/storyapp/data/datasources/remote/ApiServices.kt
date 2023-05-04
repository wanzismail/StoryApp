package id.wanztudio.storyapp.data.datasources.remote

import id.wanztudio.storyapp.core.network.ApiResponse
import id.wanztudio.storyapp.data.models.response.login.LoginResponse
import id.wanztudio.storyapp.data.models.response.story.StoryResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

/**
 * @author  Ridwan Ismail <iwanz@pm.me>
 * @version 1
 * @since   Tuesday, 15 Nov 2022
 */

interface ApiServices {

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String,
    ): Response<LoginResponse>

    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
    ): Response<ApiResponse>

    @GET("stories")
    suspend fun getStories(
        @Header("Authorization") bearerToken: String,
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): Response<StoryResponse>

    @Multipart
    @POST("stories")
    suspend fun addStory(
        @Header("Authorization") bearerToken: String,
        @Part photo: MultipartBody.Part,
        @Part("description") description: RequestBody,
    ): Response<ApiResponse>

}