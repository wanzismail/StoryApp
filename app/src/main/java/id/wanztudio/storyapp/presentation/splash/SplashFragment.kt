package id.wanztudio.storyapp.presentation.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import id.wanztudio.storyapp.R
import id.wanztudio.storyapp.core.base.BaseFragment
import id.wanztudio.storyapp.core.extensions.navigateTo
import id.wanztudio.storyapp.core.extensions.observeData
import id.wanztudio.storyapp.databinding.SplashFragmentBinding
import id.wanztudio.storyapp.presentation.story.list.StoryListActivity


/**
 * @author  Ridwan Ismail <iwanz@pm.me>
 * @version 1
 * @since   Tuesday, 15 Nov 2022
 */


@AndroidEntryPoint
internal class SplashFragment : BaseFragment<SplashViewModel, SplashFragmentBinding>(
    SplashFragmentBinding::inflate
) {

    override val viewModel: SplashViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData(viewModel.isLoggedIn) {
            when (it) {
                true -> {
                    StoryListActivity::class.java.start()
                }
                false -> {
                    navigateTo(R.id.action_splashFragment_to_loginFragment)
                }
            }
        }
    }
}

