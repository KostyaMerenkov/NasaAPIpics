package ru.students.nasaapipics

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.transition.ArcMotion
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import ru.students.nasaapipics.databinding.ActivityAnimationsBinding

class AnimationsActivity : AppCompatActivity() {
    private var isExpanded = false
    private var toRightAnimation = false

    private lateinit var vb: ActivityAnimationsBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivityAnimationsBinding.inflate(layoutInflater)
        setContentView(vb.root)
        setFAB()
        vb.scrollView.setOnScrollChangeListener { _, _, _, _, _ ->
            vb.header.isSelected = vb.scrollView.canScrollVertically(-1)
        }
    }

    private fun setFAB() {
        setInitialState()

        vb.fab.setOnClickListener {
            if (isExpanded) {
                collapseFab()
            } else {
                expandFAB()
            }
        }
    }

    private fun setInitialState() {
        vb.transparentBackground.apply {
            alpha = 0f
        }
        vb.optionOneContainer.apply {
            alpha = 0f
            isClickable = false
        }
        vb.optionTwoContainer.apply {
            alpha = 0f
            isClickable = false
        }
    }

    private fun expandFAB() {
        isExpanded = true
        ObjectAnimator.ofFloat(vb.plusImageview, "rotation", 0f, 225f).start()
        ObjectAnimator.ofFloat(vb.optionTwoContainer, "translationY", -130f).start()
        ObjectAnimator.ofFloat(vb.optionOneContainer, "translationY", -250f).start()

        vb.optionTwoContainer.animate()
            .alpha(1f)
            .setDuration(300)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    vb.optionTwoContainer.isClickable = true
                    vb.optionTwoContainer.setOnClickListener {
                        Toast.makeText(this@AnimationsActivity, "Option 2", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            })
        vb.optionOneContainer.animate()
            .alpha(1f)
            .setDuration(300)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    vb.optionOneContainer.isClickable = true
                    vb.optionOneContainer.setOnClickListener {
                        Toast.makeText(this@AnimationsActivity, "Option 1", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            })
        vb.transparentBackground.animate()
            .alpha(0.9f)
            .setDuration(300)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    vb.transparentBackground.isClickable = true
                }
            })
    }

    private fun collapseFab() {
        isExpanded = false
        ObjectAnimator.ofFloat(vb.plusImageview, "rotation", 0f, -180f).start()
        ObjectAnimator.ofFloat(vb.optionTwoContainer, "translationY", 0f).start()
        ObjectAnimator.ofFloat(vb.optionOneContainer, "translationY", 0f).start()

        vb.optionTwoContainer.animate()
            .alpha(0f)
            .setDuration(300)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    vb.optionTwoContainer.isClickable = false
                    vb.optionOneContainer.setOnClickListener(null)
                }
            })
        vb.optionOneContainer.animate()
            .alpha(0f)
            .setDuration(300)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    vb.optionOneContainer.isClickable = false
                }
            })
        vb.transparentBackground.animate()
            .alpha(0f)
            .setDuration(300)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    vb.transparentBackground.isClickable = false
                }
            })
    }
}