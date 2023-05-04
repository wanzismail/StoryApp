package id.wanztudio.storyapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import id.wanztudio.storyapp.data.datasources.local.SharedPreferences
import id.wanztudio.storyapp.data.datasources.paging.StoryPagingSource
import id.wanztudio.storyapp.data.datasources.remote.ApiServices
import id.wanztudio.storyapp.data.repositories.AuthRepositoryImpl
import id.wanztudio.storyapp.data.repositories.StoryRepositoryImpl
import id.wanztudio.storyapp.domain.repositories.AuthRepository
import id.wanztudio.storyapp.domain.repositories.StoryRepository


/**
 * @author  Ridwan Ismail <iwanz@pm.me>
 * @version 1
 * @since   Tuesday, 15 Nov 2022
 */

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    fun provideAuthRepository(
        api: ApiServices,
        preferences: SharedPreferences,
    ): AuthRepository {
        return AuthRepositoryImpl(
            api = api,
            dataStore = preferences,
        )
    }

    @Provides
    fun providePaging(
        api: ApiServices,
        preferences: SharedPreferences,
    ): StoryPagingSource {
        return StoryPagingSource(
            api = api,
            dataStore = preferences
        )
    }

    @Provides
    fun provideStoryRepository(
        api: ApiServices,
        preferences: SharedPreferences,
        pagingSource: StoryPagingSource,
    ): StoryRepository {
        return StoryRepositoryImpl(
            api = api,
            dataStore = preferences,
            pagingSource = pagingSource
        )
    }


}