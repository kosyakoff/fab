package test.app.fabapplication.views

import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup.MarginLayoutParams
import android.view.animation.DecelerateInterpolator
import com.google.android.material.floatingactionbutton.FloatingActionButton
import test.app.fabapplication.common.Constants
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class MovableFloatingActionButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FloatingActionButton(context, attrs), OnTouchListener {
    private var downRawX = 0f
    private var downRawY = 0f
    private var dX = 0f
    private var dY = 0f
    private var barSize = 0
    private var navSize = 0

    init {
        init()
    }

    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        val layoutParams = view.layoutParams as MarginLayoutParams
        return when (motionEvent.action) {
            MotionEvent.ACTION_DOWN -> {
                downRawX = motionEvent.rawX
                downRawY = motionEvent.rawY
                dX = view.x - downRawX
                dY = view.y - downRawY
                true // Consumed
            }

            MotionEvent.ACTION_MOVE -> {
                val viewWidth = view.width
                val viewHeight = view.height
                val viewParent = view.parent as View
                val parentWidth = viewParent.width
                val parentHeight = viewParent.height
                var newX = motionEvent.rawX + dX
                newX = max(
                    layoutParams.leftMargin.toFloat(), newX
                ) // Don't allow the FAB past the left hand side of the parent
                newX = min(
                    (parentWidth - viewWidth - layoutParams.rightMargin).toFloat(), newX
                ) // Don't allow the FAB past the right hand side of the parent
                var newY = motionEvent.rawY + dY
                newY = max(
                    layoutParams.topMargin.toFloat() + barSize, newY
                ) // Don't allow the FAB past the top of the parent
                newY = min(
                    (parentHeight - navSize - viewHeight - layoutParams.bottomMargin).toFloat(),
                    newY
                ) // Don't allow the FAB past the bottom of the parent
                view.animate().x(newX).y(newY).setDuration(0).start()
                true // Consumed
            }

            MotionEvent.ACTION_UP -> {
                val viewWidth = view.width
                val viewHeight = view.height
                val viewParent = view.parent as View
                val parentWidth = viewParent.width
                val parentHeight = viewParent.height

                val upRawX = motionEvent.rawX
                val upRawY = motionEvent.rawY
                val upDX = upRawX - downRawX
                val upDY = upRawY - downRawY
                if (abs(upDX) < CLICK_DRAG_TOLERANCE && abs(upDY) < CLICK_DRAG_TOLERANCE) { // A click
                    performClick()
                } else { // A drag

                    var updatedX = x
                    var updatedY = y
                    var shouldBeUpdated = false

                    if (upRawY - (layoutParams.topMargin.toFloat() + barSize) < VERTICAL_SNAP_BORDER) {
                        shouldBeUpdated = true
                        updatedY = layoutParams.topMargin.toFloat() + barSize
                    }
                    if ((parentHeight - navSize - viewHeight - layoutParams.bottomMargin).toFloat() - upRawY < VERTICAL_SNAP_BORDER) {
                        shouldBeUpdated = true
                        updatedY =
                            (parentHeight - navSize - viewHeight - layoutParams.bottomMargin).toFloat()
                    }
                    if (upRawX - (viewWidth + layoutParams.leftMargin.toFloat()) < HORIZONTAL_SNAP_BORDER) {
                        shouldBeUpdated = true
                        updatedX = layoutParams.leftMargin.toFloat()
                    }
                    if ((parentWidth - viewWidth - layoutParams.rightMargin).toFloat() - upRawX < HORIZONTAL_SNAP_BORDER) {
                        shouldBeUpdated = true
                        updatedX = (parentWidth - viewWidth - layoutParams.rightMargin).toFloat()
                    }

                    if (shouldBeUpdated) {
                        val xAnimator = ObjectAnimator.ofFloat(this, "x", updatedX)
                        xAnimator.interpolator = DecelerateInterpolator()
                        xAnimator.duration = Constants.ANIMATION_DURATION
                        xAnimator.start()
                        val yAnimator = ObjectAnimator.ofFloat(this, "y", updatedY)
                        yAnimator.interpolator = DecelerateInterpolator()
                        yAnimator.duration = Constants.ANIMATION_DURATION
                        yAnimator.start()
                    }

                    true // Consumed
                }
            }

            else -> {
                super.onTouchEvent(motionEvent)
            }
        }
    }

    private fun init() {
        setOnTouchListener(this)

        this.setOnApplyWindowInsetsListener { _, insets ->
            navSize = insets.systemWindowInsetBottom
            barSize = insets.systemWindowInsetTop
            insets
        }
    }

    companion object {
        private const val CLICK_DRAG_TOLERANCE =
            10f // Often, there will be a slight, unintentional, drag when the user taps the FAB, so we need to account for this.
        private const val VERTICAL_SNAP_BORDER = 150
        private const val HORIZONTAL_SNAP_BORDER = 70
    }
}