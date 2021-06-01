package ru.students.nasaapipics

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnticipateOvershootInterpolator
import androidx.constraintlayout.widget.ConstraintSet
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import ru.students.nasaapipics.databinding.ActivityAnimationsBinding
import ru.students.nasaapipics.databinding.ActivityConstraintSetAnimationStartBinding

class ActivityConstraintSetAnimation : AppCompatActivity() {
    private var show = false
    private lateinit var vb: ActivityConstraintSetAnimationStartBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivityConstraintSetAnimationStartBinding.inflate(layoutInflater)
        setContentView(vb.root)

        vb.backgroundImage.setOnClickListener { if (show) hideComponents() else showComponents() }
    }

    private fun showComponents() {
        show = true

        val constraintSet = ConstraintSet()
        constraintSet.clone(this, R.layout.activity_constraint_set_animation_end)

        val transition = ChangeBounds()
        transition.interpolator = AnticipateOvershootInterpolator(1.0f)
        transition.duration = 1200

        TransitionManager.beginDelayedTransition(vb.constraintContainer, transition)
        constraintSet.applyTo(vb.constraintContainer)
    }

    private fun hideComponents() {
        show = false

        val constraintSet = ConstraintSet()
        constraintSet.clone(this, R.layout.activity_constraint_set_animation_start)

        val transition = ChangeBounds()
        transition.interpolator = AnticipateOvershootInterpolator(1.0f)
        transition.duration = 1200

        TransitionManager.beginDelayedTransition(vb.constraintContainer, transition)
        constraintSet.applyTo(vb.constraintContainer)
    }
}