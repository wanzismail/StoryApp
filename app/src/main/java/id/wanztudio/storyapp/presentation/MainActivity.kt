package id.wanztudio.storyapp.presentation


import dagger.hilt.android.AndroidEntryPoint
import id.wanztudio.storyapp.core.base.BaseActivity
import id.wanztudio.storyapp.databinding.MainActivityBinding


/**
 * @author  Ridwan Ismail <iwanz@pm.me>
 * @version 1
 * @since   Tuesday, 15 Nov 2022
 */

@AndroidEntryPoint
class MainActivity : BaseActivity<MainActivityBinding>(MainActivityBinding::inflate)