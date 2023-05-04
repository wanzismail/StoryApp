package id.wanztudio.storyapp.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


/**
 * @author  Ridwan Ismail <iwanz@pm.me>
 * @version 1
 * @since   Tuesday, 15 Nov 2022
 */

@Parcelize
data class StoryModel(
    val id: String?,
    val photoUrl: String,
    val name: String,
    val description: String,
    val latitude: Double? = null,
    val longitude: Double? = null,
) : Parcelable