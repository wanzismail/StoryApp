package id.wanztudio.storyapp.presentation.register

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import id.wanztudio.storyapp.R
import id.wanztudio.storyapp.core.base.BaseFragment
import id.wanztudio.storyapp.core.extensions.*
import id.wanztudio.storyapp.data.models.request.RegisterRequest
import id.wanztudio.storyapp.databinding.RegisterFragmentBinding
import kotlinx.coroutines.launch


/**
 * @author  Ridwan Ismail <iwanz@pm.me>
 * @version 1
 * @since   Tuesday, 15 Nov 2022
 */

@AndroidEntryPoint
class RegisterFragment :
    BaseFragment<RegisterViewModel, RegisterFragmentBinding>(RegisterFragmentBinding::inflate) {

    override val viewModel: RegisterViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData(viewModel.isLoading) {
            binding.apply {
                editRegisterName.isEnabled = !it
                editRegisterEmail.isEnabled = !it
                editRegisterPassword.isEnabled = !it
                buttonRegister.isEnabled = !it
                buttonRegister.text = if (it) "" else getString(R.string.register_caption)
                progressBar.visibleOrInvisible(it)
            }
        }

        observeData(viewModel.isRegistered) {
            it.ifTrue {
                back()
            }
        }

        observeData(viewModel.errorMessage) {
            showMessage(it)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isRegisterEnabled.collect {
                binding.buttonRegister.isEnabled = it
            }
        }

        binding.editRegisterName.addTextChangedListener {
            viewModel.setName(it.toString())
        }

        binding.editRegisterEmail.addTextChangedListener {
            viewModel.setEmail(it.toString())
        }

        binding.editRegisterPassword.addTextChangedListener {
            viewModel.setPassword(it.toString())
        }

        binding.buttonRegister.setOnClickListener {

            viewModel.register(RegisterRequest(
                name = binding.editRegisterName.value,
                email = binding.editRegisterEmail.value,
                password = binding.editRegisterPassword.value,
            ))
        }
    }

}