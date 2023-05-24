package garcia.marco.myinterview.ui.screens.splash

import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import garcia.marco.myinterview.databinding.ActivitySplashBinding
import garcia.marco.myinterview.ui.bases.BaseActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SplashActivity : BaseActivity() {

    private lateinit var binding: ActivitySplashBinding

    var DURATION = 1000
    var TIMER_LATER = 1000

    override fun createView() {
        binding = ActivitySplashBinding.inflate(layoutInflater).apply {
            setContentView(root)
        }
        initUI()
    }

    override fun collectFlows() {

    }

    private fun initUI(){
        var fadeIn = AlphaAnimation(0.0f, 1.0f)
        fadeIn.duration = DURATION.toLong()
        fadeIn.startOffset = TIMER_LATER.toLong()
        fadeIn.fillAfter = true

        var fadeOut = AlphaAnimation(1.0f, 0.0f)
        fadeOut.duration = DURATION.toLong()
        fadeOut.startOffset = TIMER_LATER.toLong()
        fadeOut.fillAfter = true

        with(binding) {
            with(lifecycleScope) {
                launchWhenCreated {
                    fadeIn.setAnimationListener(object : Animation.AnimationListener{
                        override fun onAnimationStart(p0: Animation?) {
                            ivSplash.visibility = View.VISIBLE
                        }
                        override fun onAnimationEnd(p0: Animation?) {
                            ivSplash.startAnimation(fadeOut)
                        }
                        override fun onAnimationRepeat(p0: Animation?) {}
                    })
                }

                launchWhenCreated {
                    fadeOut.setAnimationListener(object : Animation.AnimationListener{
                        override fun onAnimationStart(p0: Animation?) {}
                        override fun onAnimationEnd(p0: Animation?) {
                            goToMainActivity()
                        }
                        override fun onAnimationRepeat(p0: Animation?) {}
                    })
                }

                launchWhenCreated {
                    ivSplash.startAnimation(fadeIn)
                }
            }
        }
    }

}