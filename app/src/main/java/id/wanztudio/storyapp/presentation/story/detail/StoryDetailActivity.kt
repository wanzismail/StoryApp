package id.wanztudio.storyapp.presentation.story.detail

import com.bumptech.glide.Glide
import id.wanztudio.storyapp.core.base.BaseActivity
import id.wanztudio.storyapp.data.models.StoryModel
import id.wanztudio.storyapp.databinding.StoryDetailActivityBinding


/**
 * @author  Ridwan Ismail <iwanz@pm.me>
 * @version 1
 * @since   Tuesday, 15 Nov 2022
 */

class StoryDetailActivity : BaseActivity<StoryDetailActivityBinding>(
    StoryDetailActivityBinding::inflate
) {

    override fun StoryDetailActivityBinding.initialBinding() {
        val data = intent.getParcelableExtra<StoryModel>(EXTRA_STORY)
        data?.let {
            Glide.with(this@StoryDetailActivity)
                .load(data.photoUrl)
                .into(imageDetailPhoto)
            textDetailName.text = data.name
            textDetailDescription.text = data.description
        }
    }

    companion object {
        const val EXTRA_STORY = "EXTRA_STORY_KEY"
    }
}