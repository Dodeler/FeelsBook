package cdodic.feelsbook;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

// information on how to implement the touch listener was gathered from:
// https://stackoverflow.com/questions/41200876/how-to-set-onitemclicklistener-for-recyclerview
public class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {
    //Class for detecting item selection by listening for clicks on the screen
    private GestureDetector gesture_detector;
    private ClickListener click_listener;

    public RecyclerTouchListener(Context context, RecyclerView recycler_view, final ClickListener click_listener) {
        this.click_listener = click_listener;

        gesture_detector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                // prevent further taps from registering before next activity is loaded
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                //Do nothing on long press
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

        // collects view from location of selection
        View child = rv.findChildViewUnder(e.getX(), e.getY());
        if ( (child != null) && (click_listener != null) && gesture_detector.onTouchEvent(e) ) {
            click_listener.onClick(child, rv.getChildPosition(child));
        }

        return true;
    }

    //dummy overrides - do nothing
    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    //Dummy interface forcing implementers to fill in what happens on clicks (or long clicks)
    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }
}