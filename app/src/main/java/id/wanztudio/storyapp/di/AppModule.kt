package id.wanztudio.storyapp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.wanztudio.storyapp.BuildConfig
import id.wanztudio.storyapp.core.network.ApiServiceProvider
import id.wanztudio.storyapp.core.network.OkHttpClientFactory
import id.wanztudio.storyapp.core.network.RestApiConfig
import id.wanztudio.storyapp.data.datasources.local.SharedPreferences
import id.wanztudio.storyapp.data.datasources.local.SharedPreferencesImpl
import id.wanztudio.storyapp.data.datasources.remote.ApiServices


/**
 * @author  Ridwan Ismail <iwanz@pm.me>
 * @version 1
 * @since   Tuesday, 15 Nov 2022
 */

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideRestApiConfig(): RestApiConfig {
        return RestApiConfig(
            baseUrl = BuildConfig.BASE_URL,
            debugMode = BuildConfig.DEBUG
        )
    }

    @Provides
    fun provideDataPreferences(
        @ApplicationContext appContext: Context,
    ): SharedPreferences {
        return SharedPreferencesImpl(appContext)
    }

    @Provides
    fun provideApi(
        apiConfig: RestApiConfig,
    ): ApiServices {
        return ApiServiceProvider(
            baseURL = apiConfig.baseUrl,
            client = OkHttpClientFactory.create(apiConfig)

        ).createService(ApiServices::class.java)
    }

}