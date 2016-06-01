package m117.cs.foodspinner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.GestureDetector.OnGestureListener;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;
import android.widget.ViewFlipper;


import java.util.ArrayList;

public class SlotActivity extends AppCompatActivity implements OnGestureListener {

    private ViewFlipper mViewFlipper;
    private GestureDetector mDetector;
    private int mSpeed;
    private int mCount;
    private int mFactor;
    private boolean mAnimating;
    private boolean mHasFlung;
    private ArrayList result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slot);

        mViewFlipper = (ViewFlipper)findViewById(R.id.view_flipper);
        mDetector = new GestureDetector(this);
        mCount = 0;
        mSpeed = 0;
        mHasFlung = false;

        Intent intent = getIntent();
        result = intent.getParcelableArrayListExtra(ListActivity.SPIN_RESULT);
    }

    /** Required for compilation **/
    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }
    /** End req'd **/

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //return super.onTouchEvent(event);
        return mDetector.onTouchEvent(event);
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        try {
            if(!mHasFlung) {
                if (mAnimating) return true;
                mAnimating = true;
                mHasFlung = true;
                mCount = (int) Math.abs(velocityY) / 300;
                mFactor = 300 / mCount;
                mSpeed = mFactor;
                if (velocityY > 0) {
                    // down
                    Handler h = new Handler();
                    h.postDelayed(r2, mSpeed);
                } else {
                    // up
                    Handler h = new Handler();
                    h.postDelayed(r1, mSpeed);
                }
            }

            //((TextView)findViewById(R.id.velocity)).setText("VELOCITY => " + Float.toString(velocityY));
        } catch (ArithmeticException e) {
            // swiped too slow
            mAnimating = false;
        }

        return true;
    }

    private Runnable r1 = new Runnable() {
        @Override
        public void run() {
            up();
            if(mCount < 1) {
                mAnimating = false;

                TextView tv = (TextView)findViewById(R.id.textview_slot_result_display);
                tv.setText(result.get(2).toString());
                tv.setVisibility(View.VISIBLE);
                findViewById(R.id.button_slot_continue).setVisibility(View.VISIBLE);
            } else {
                Handler h = new Handler();
                h.postDelayed(r1, mSpeed);
            }
        }
    };

    private void up() {
        mCount--;
        mSpeed += mFactor;
        Animation inFromBottom = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        inFromBottom.setInterpolator(new AccelerateInterpolator());
        inFromBottom.setDuration(mSpeed);

        Animation outToTop = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        outToTop.setInterpolator(new AccelerateInterpolator());
        outToTop.setDuration(mSpeed);

        mViewFlipper.clearAnimation();
        mViewFlipper.setInAnimation(inFromBottom);
        mViewFlipper.setOutAnimation(outToTop);
        if(mViewFlipper.getDisplayedChild() == 0)
            mViewFlipper.setDisplayedChild(2);
        else
            mViewFlipper.showPrevious();

        //((TextView)findViewById(R.id.counter)).setText("COUNTER => "+Integer.toString(mCount));
        //((TextView)findViewById(R.id.speed)).setText("SPEED => "+Integer.toString(mSpeed));

    }

    private Runnable r2 = new Runnable() {
        @Override
        public void run() {
            down();
            if (mCount<1) {
                mAnimating = false;
                TextView tv = (TextView)findViewById(R.id.textview_slot_result_display);
                tv.setText(result.get(2).toString());
                tv.setVisibility(View.VISIBLE);
                findViewById(R.id.button_slot_continue).setVisibility(View.VISIBLE);
            } else {
                Handler h = new Handler();
                h.postDelayed(r2, mSpeed);
            }
        }
    };

    private void down() {
        mCount--;
        mSpeed+=mFactor;
        Animation outToBottom = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 1.0f);
        outToBottom.setInterpolator(new AccelerateInterpolator());
        outToBottom.setDuration(mSpeed);
        Animation inFromTop = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, -1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        inFromTop.setInterpolator(new AccelerateInterpolator());
        inFromTop.setDuration(mSpeed);
        mViewFlipper.clearAnimation();
        mViewFlipper.setInAnimation(inFromTop);
        mViewFlipper.setOutAnimation(outToBottom);
        if (mViewFlipper.getDisplayedChild()==0) {
            mViewFlipper.setDisplayedChild(2);
        } else {
            mViewFlipper.showPrevious();
        }
        //((TextView)findViewById(R.id.counter)).setText("COUNTER => "+Integer.toString(mCount));
        //((TextView)findViewById(R.id90024.speed)).setText("SPEED => "+Integer.toString(mSpeed));
    }


    public void continueButton(View view) {
        if(!mAnimating && mHasFlung) {
            Intent intent = new Intent(this, ResultActivity.class);
            intent.putExtra(ListActivity.SPIN_RESULT, result);
            startActivity(intent);
        }
    }

}
