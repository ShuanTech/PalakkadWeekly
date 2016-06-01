
package com.shuan.palakkadweekly.photoView.gestures;

import android.view.MotionEvent;

public interface GestureDetector {

    public boolean onTouchEvent(MotionEvent ev);

    public boolean isScaling();

    public boolean isDragging();

    public void setOnGestureListener(OnGestureListener listener);

}
