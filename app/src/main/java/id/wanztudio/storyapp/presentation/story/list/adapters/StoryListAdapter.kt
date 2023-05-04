package id.wanztudio.storyapp.presentation.story.list.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.wanztudio.storyapp.data.models.StoryModel
import id.wanztudio.storyapp.databinding.StoryListItemBinding


/**
 * @author  Ridwan Ismail <iwanz@pm.me>
 * @version 1
 * @since   Tuesday, 15 Nov 2022
 */

class StoryListAdapter(
    private val onDetailItem: (StoryListItemBinding, StoryModel) -> Unit,
) : PagingDataAdapter<StoryModel, StoryListAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<StoryModel>() {
            override fun areItemsTheSame(oldItem: StoryModel, newItem: StoryModel): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: StoryModel, newItem: StoryModel): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            StoryListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onDetailItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { data ->
            holder.bind(data)
        }
    }

    class ViewHolder(
        private val binding: StoryListItemBinding,
        private val onItemClick: (StoryListItemBinding, StoryModel) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: StoryModel) = with(binding) {
            itemView.setOnClickListener { onItemClick.invoke(binding, data) }

            Glide.with(itemView).load(data.photoUrl).into(imagePhoto)
            textName.text = data.name
        }

    }


}