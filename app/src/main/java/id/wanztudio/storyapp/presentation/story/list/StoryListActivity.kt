package id.wanztudio.storyapp.presentation.story.list

import android.content.Intent
import android.provider.Settings
import androidx.activity.viewModels
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import dagger.hilt.android.AndroidEntryPoint
import id.wanztudio.storyapp.R
import id.wanztudio.storyapp.core.base.BaseActivity
import id.wanztudio.storyapp.core.extensions.ifTrue
import id.wanztudio.storyapp.core.extensions.observeData
import id.wanztudio.storyapp.core.helpers.SettingHelper
import id.wanztudio.storyapp.data.models.StoryModel
import id.wanztudio.storyapp.databinding.StoryListActivityBinding
import id.wanztudio.storyapp.databinding.StoryListItemBinding
import id.wanztudio.storyapp.presentation.MainActivity
import id.wanztudio.storyapp.presentation.story.detail.StoryDetailActivity
import id.wanztudio.storyapp.presentation.story.list.adapters.LoadMoreAdapter
import id.wanztudio.storyapp.presentation.story.list.adapters.StoryListAdapter
import id.wanztudio.storyapp.presentation.story.post.StoryPostActivity


/**
 * @author  Ridwan Ismail <iwanz@pm.me>
 * @version 1
 * @since   Tuesday, 15 Nov 2022
 */

@AndroidEntryPoint
class StoryListActivity :
    BaseActivity<StoryListActivityBinding>(StoryListActivityBinding::inflate) {

    val viewModel: StoryListViewModel by viewModels()

    private val storiesAdapter = StoryListAdapter(::onItemClicked)

    override fun StoryListActivityBinding.initialBinding() {
        initViews()
        initObserveData()
    }

    private fun StoryListActivityBinding.initViews() {

        buttonSettings.setOnClickListener { openSettings() }

        val loadStateAdapter = LoadMoreAdapter { storiesAdapter.retry() }
        recyclerStories.adapter = storiesAdapter.withLoadStateFooter(loadStateAdapter)
        fabPostStory redirectTo StoryPostActivity::class.java
    }

    private fun initObserveData() {
        observeData(viewModel.storiesList) { pagingData ->
            storiesAdapter.submitData(lifecycle, pagingData)
        }
        observeData(viewModel.isLogout) { state ->
            state.ifTrue {
                MainActivity::class.java.start()
            }
        }
    }

    private fun onItemClicked(view: StoryListItemBinding, data: StoryModel) {
        val animationBundle = ActivityOptionsCompat.makeSceneTransitionAnimation(this,
            Pair(view.imagePhoto, getString(R.string.transition_imageview_detail_photo)),
            Pair(view.textName, getString(R.string.transition_textview_detail_name))).toBundle()

        Intent(this, StoryDetailActivity::class.java).apply {
            putExtra(StoryDetailActivity.EXTRA_STORY, data)
            startActivity(this, animationBundle)
        }
    }

    private fun openSettings() {
        SettingHelper.open(this, binding.buttonSettings, object : SettingHelper.Callback {
            override fun onLanguageSelected() {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            }

            override fun onLogoutSelected() {
                viewModel.logout()
            }
        })
    }

}