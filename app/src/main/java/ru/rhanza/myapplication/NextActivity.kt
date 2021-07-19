package ru.rhanza.myapplication

import android.animation.Animator
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import androidx.appcompat.app.AppCompatActivity

class NextActivity : AppCompatActivity() {
    private var background: View? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.do_not_move, R.anim.do_not_move)
        setContentView(R.layout.activity_next)
        background = findViewById(R.id.background)
        if (savedInstanceState == null) {
            background!!.visibility = View.INVISIBLE
            val viewTreeObserver = background!!.viewTreeObserver
            if (viewTreeObserver.isAlive) {
                viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
                    override fun onGlobalLayout() {
                        circularRevealActivity()
                        background!!.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    }
                })
            }
        }
    }

    private fun circularRevealActivity() {
        val cx = background!!.right - getDips(44)
        val cy = background!!.bottom - getDips(44)
        val finalRadius = Math.max(background!!.width, background!!.height).toFloat()
        val circularReveal = ViewAnimationUtils.createCircularReveal(
            background,
            cx,
            cy, 0f,
            finalRadius
        )
        circularReveal.duration = 3000
        background!!.visibility = View.VISIBLE
        circularReveal.start()
    }

    private fun getDips(dps: Int): Int {
        val resources: Resources = resources
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dps.toFloat(),
            resources.displayMetrics
        ).toInt()
    }

    override fun onBackPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val cx = background!!.width - getDips(44)
            val cy = background!!.bottom - getDips(44)
            val finalRadius = background!!.width.coerceAtLeast(background!!.height).toFloat()
            val circularReveal =
                ViewAnimationUtils.createCircularReveal(background, cx, cy, finalRadius, 0f)
            circularReveal.addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animator: Animator) {}
                override fun onAnimationEnd(animator: Animator) {
                    background!!.visibility = View.INVISIBLE
                    finish()
                }

                override fun onAnimationCancel(animator: Animator) {}
                override fun onAnimationRepeat(animator: Animator) {}
            })
            circularReveal.duration = 3000
            circularReveal.start()
        } else {
            super.onBackPressed()
        }
    }
}