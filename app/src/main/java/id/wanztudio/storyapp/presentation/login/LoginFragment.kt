package id.wanztudio.storyapp.presentation.login

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import id.wanztudio.storyapp.R
import id.wanztudio.storyapp.core.base.BaseFragment
import id.wanztudio.storyapp.core.extensions.ifTrue
import id.wanztudio.storyapp.core.extensions.observeData
import id.wanztudio.storyapp.core.extensions.value
import id.wanztudio.storyapp.core.extensions.visibleOrInvisible
import id.wanztudio.storyapp.databinding.LoginFragmentBinding
import id.wanztudio.storyapp.presentation.story.list.StoryListActivity
import kotlinx.coroutines.launch


/**
 * @author  Ridwan Ismail <iwanz@pm.me>
 * @version 1
 * @since   Tuesday, 15 Nov 2022
 */

@AndroidEntryPoint
class LoginFragment :
    BaseFragment<LoginViewModel, LoginFragmentBinding>(LoginFragmentBinding::inflate) {

    override val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewsListener()
        initObserveData()

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isLoginEnabled.collect {
                binding.buttonLogin.isEnabled = it
            }
        }
    }

    private fun initViewsListener() {
        binding.editLoginEmail.addTextChangedListener { viewModel.setEmail(it.toString()) }
        binding.editLoginPassword.addTextChangedListener { viewModel.setPassword(it.toString()) }

        binding.buttonLogin.setOnClickListener {
            viewModel.login(email = binding.editLoginEmail.value,
                password = binding.editLoginPassword.value)
        }

        binding.btnRegister redirectTo R.id.action_loginFragment_to_registerFragment
    }

    private fun initObserveData() {
        observeData(viewModel.isLoggedIn) {
            it.ifTrue {
                StoryListActivity::class.java.start()
            }
        }

        observeData(viewModel.isLoading) {
            binding.editLoginEmail.isEnabled = !it
            binding.editLoginPassword.isEnabled = !it
            binding.buttonLogin.isEnabled = true
            binding.buttonLogin.text = if (it) "" else getString(R.string.login_caption)
            binding.progressBarLogin.visibleOrInvisible(it)
        }

        observeData(viewModel.errorMessage) {
            showMessage(it)
        }
    }
}