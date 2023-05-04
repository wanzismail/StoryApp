package id.wanztudio.storyapp.presentation.story.list.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import id.wanztudio.storyapp.databinding.LoadingListItemBinding


/**
 * @author  Ridwan Ismail <iwanz@pm.me>
 * @version 1
 * @since   Tuesday, 15 Nov 2022
 */

class LoadMoreAdapter(
    private val onRetry: () -> Unit,
) : LoadStateAdapter<LoadMoreAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        val binding = LoadingListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    inner class ViewHolder(
        private val binding: LoadingListItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.buttonRetry.setOnClickListener { onRetry.invoke() }
        }

        fun bind(loadState: LoadState) = with(binding) {
            if (loadState is LoadState.Error) {
                textError.text = loadState.error.localizedMessage
            }

            progressBar.isVisible = loadState is LoadState.Loading
            buttonRetry.isVisible = loadState is LoadState.Error
            textError.isVisible = loadState is LoadState.Error
        }

    }

}