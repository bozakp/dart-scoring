package com.philbozak.dartscoring;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;

import butterknife.ButterKnife;

public class DartboardView extends View {
    private static final String TAG = DartboardView.class.getSimpleName();
    private static final int[] LABELS = { 6, 10, 15, 2, 17, 3, 19, 7, 16, 8, 11, 14, 9, 12, 5,
            20, 1, 18, 4, 13 };
    private static final float BULLSEYE_RADIUS = 0.3f;

    public DartboardView(Context context) {
        super(context);
    }

    public DartboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DartboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Point center = new Point(this.getWidth() / 2, this.getHeight() / 2);
        int maxRadius = Math.min(center.x, center.y);

        RectF r = new RectF(0, 0, center.x * 2, center.y * 2);
        for (int i = 0; i < 20; i++) {
            int color = i % 2 == 0 ? Color.GRAY : Color.BLACK;
            Paint p = new Paint();
            p.setColor(color);
            p.setAntiAlias(true);
            canvas.drawArc(r, 18*i - 9, 18, true, p);
        }

        Paint green = new Paint();
        green.setColor(Color.GREEN);
        green.setAntiAlias(true);
        canvas.drawCircle(center.x, center.y, maxRadius * BULLSEYE_RADIUS, green);

        this.drawText(canvas, "25", center.x, center.y, Color.BLACK);


        for (int i = 0; i < 20; i++) {
            float numberRadius = maxRadius * ( i % 2 == 0 ? 0.75f : 0.85f);
            float x = (float) (center.x + numberRadius * Math.cos(Math.toRadians(18.0 * i)));
            float y = (float) (center.y + numberRadius * Math.sin(Math.toRadians(18.0 * i)));

            this.drawText(canvas, String.valueOf(LABELS[i]), x, y, Color.WHITE);
        }
    }

    private void drawText(Canvas canvas, String text, float x, float y, int color) {
        Paint c = new Paint();
        c.setColor(color);
        c.setTextSize(40.0f);
        c.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(text, x, y - (c.ascent() + c.descent()) / 2, c);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        this.setMeasuredDimension(heightMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() != MotionEvent.ACTION_DOWN) {
            return false;
        }
        Point center = new Point(this.getWidth() / 2, this.getHeight() / 2);
        int maxRadius = Math.min(center.x, center.y);

        PointF touchPoint = new PointF(event.getX() - center.x, event.getY() - center.y);
        Log.d(TAG, String.format("Got touched at (%f, %f)", touchPoint.x, touchPoint.y));

        final int selectedNum;
        if (touchPoint.length() < BULLSEYE_RADIUS * maxRadius) {
            selectedNum = 25;
        } else if (touchPoint.length() > maxRadius) {
            selectedNum = 0;
        } else {
            float angle = (float) Math.toDegrees(Math.atan2(touchPoint.y, touchPoint.x));
            angle += 9;
            if (angle < 0) { angle += 360.0f; }
            int index = (int) angle / 18;
            selectedNum = LABELS[index];
        }
        Log.d(TAG, String.format("Selected %d", selectedNum));
        TextView t = ButterKnife.findById((ViewGroup) getParent(), R.id.hitNumberTextView);
        t.setText(String.valueOf(selectedNum));

        return true;
    }
}
