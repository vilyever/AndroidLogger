package com.vilyever.logger;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * LoggerDisplayView
 * AndroidLogger <com.vilyever.logger>
 * Created by vilyever on 2016/3/29.
 * Feature:
 */
public class LoggerDisplayView extends FrameLayout implements View.OnLayoutChangeListener {
    final LoggerDisplayView self = this;

    
    /* Constructors */
    public LoggerDisplayView(Activity activity) {
        super(activity);

        setAlpha(0.7f);

        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        addView(getLoggerRecyclerView());

        getLoggerRecyclerView().setVisibility(GONE);
        getLoggerRecyclerView().setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        getLoggerRecyclerView().addItemDecoration(new LoggerDisplayDividerItemDecoration(0, getContext().getResources().getDimensionPixelSize(R.dimen.loggerDisplayRecyclerViewItemDividerHeight)));
        getLoggerRecyclerView().setAdapter(getLoggerDisplayItemAdapter());

        addView(getLoggerButton());

        getLoggerButton().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getLoggerRecyclerView().getVisibility() == VISIBLE) {
                    getLoggerRecyclerView().setVisibility(GONE);
                }
                else {
                    getLoggerRecyclerView().setVisibility(VISIBLE);
                }
            }
        });

        getLoggerButton().setOnTouchListener(new OnTouchListener() {
            boolean dragging = false;

            boolean initialTouchSet = false;
            int initialTouchX;
            int initialTouchY;

            int lastTouchX;
            int lastTouchY;

            @Override
            public boolean onTouch(View v, MotionEvent e) {
                if (!this.dragging) {
                    v.onTouchEvent(e);
                }

                final int action = MotionEventCompat.getActionMasked(e);

                if (!this.initialTouchSet) {
                    this.initialTouchX = this.lastTouchX = (int) (e.getRawX() + 0.5f);
                    this.initialTouchY = this.lastTouchY = (int) (e.getRawY() + 0.5f);
                    this.initialTouchSet = true;
                }

                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_MOVE: {
                        final int x = (int) (e.getRawX() + 0.5f);
                        final int y = (int) (e.getRawY() + 0.5f);

                        if (!this.dragging) {
                            final int dx = x - this.initialTouchX;
                            final int dy = y - this.initialTouchY;

                            int touchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();

                            if (Math.abs(dx) > touchSlop) {
                                this.lastTouchX = this.initialTouchX + touchSlop * (dx < 0 ? -1 : 1);
                                this.dragging = true;
                            }
                            else if (Math.abs(dy) > touchSlop) {
                                this.lastTouchY = this.initialTouchY + touchSlop * (dy < 0 ? -1 : 1);
                                this.dragging = true;
                            }

                            if (this.dragging) {
                                final int oldAction = e.getAction();
                                e.setAction(MotionEvent.ACTION_CANCEL);
                                v.onTouchEvent(e);
                                e.setAction(oldAction);
                            }
                        }
                        else {
                            int dx = x - this.lastTouchX;
                            int dy = y - this.lastTouchY;

                            this.lastTouchX = x;
                            this.lastTouchY = y;

                            int targetX = (int) Math.floor(v.getX() + dx);
                            int targetY = (int) Math.floor(v.getY() + dy);

                            targetX = Math.max(0, targetX);
                            targetX = Math.min(self.getWidth() - v.getWidth(), targetX);
                            targetY = Math.max(0, targetY);
                            targetY = Math.min(self.getHeight() - v.getHeight(), targetY);

                            v.setX(targetX);
                            v.setY(targetY);
                        }
                    }
                    break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL: {
                        if (this.dragging) {
                            this.initialTouchSet = false;
                            this.dragging = false;
                        }
                    }
                }

                return true;
            }
        });
    }

    public void notifyLogChanged() {
        getLoggerRecyclerView().post(new Runnable() {
            @Override
            public void run() {
                self.getLoggerRecyclerView().getAdapter().notifyDataSetChanged();
            }
        });
    }
    
    /* Public Methods */
    public void attachToActivity() {
        Activity activity = (Activity) getContext();
        ViewGroup fitWindowsLinearLayout = (ViewGroup) activity.findViewById(android.R.id.content).getParent();
        fitWindowsLinearLayout.addOnLayoutChangeListener(this);

        if (getParent() != fitWindowsLinearLayout.getParent()) {

            if (getParent() != null) {
                ViewGroup parent = (ViewGroup) getParent();
                parent.removeView(this);
            }

            ((ViewGroup) fitWindowsLinearLayout.getParent()).addView(this);
            self.setLeft(fitWindowsLinearLayout.getLeft());
            self.setTop(fitWindowsLinearLayout.getTop());
            self.setRight(fitWindowsLinearLayout.getRight());
            self.setBottom(fitWindowsLinearLayout.getBottom());

            post(new Runnable() {
                @Override
                public void run() {
                    int margin = self.getContext().getResources().getDimensionPixelSize(R.dimen.loggerDisplayButtonMargin);
                    self.getLoggerButton().setX(margin);
                    self.getLoggerButton().setY(self.getHeight() - margin - getLoggerButton().getHeight());

                    FrameLayout.LayoutParams layoutParams = (LayoutParams) getLoggerRecyclerView().getLayoutParams();
                    layoutParams.height = self.getHeight() / 3;
                    layoutParams.topMargin = self.getHeight() - layoutParams.height;
                    getLoggerRecyclerView().setLayoutParams(layoutParams);
                }
            });
        }
    }

    public void detachFromActivity() {
        if (getParent() != null) {
            ViewGroup parent = (ViewGroup) getParent();
            parent.removeView(this);

            getLoggerRecyclerView().setVisibility(GONE);
        }
    }
    
    
    /* Properties */
    private RecyclerView loggerRecyclerView;
    protected RecyclerView getLoggerRecyclerView() {
        if (this.loggerRecyclerView == null) {
            this.loggerRecyclerView = new RecyclerView(getContext());
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            this.loggerRecyclerView.setLayoutParams(layoutParams);
            this.loggerRecyclerView.setBackgroundColor(Color.LTGRAY);
        }
        return this.loggerRecyclerView;
    }

    private FloatingActionButton loggerButton;
    protected FloatingActionButton getLoggerButton() {
        if (this.loggerButton == null) {
            this.loggerButton = new FloatingActionButton(getContext());
            this.loggerButton.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            this.loggerButton.setImageResource(R.drawable.logger);
            this.loggerButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#1E90FF")));
        }
        return this.loggerButton;
    }

    private LoggerDisplayItemAdapter loggerDisplayItemAdapter;
    protected LoggerDisplayItemAdapter getLoggerDisplayItemAdapter() {
        if (this.loggerDisplayItemAdapter == null) {
            this.loggerDisplayItemAdapter = new LoggerDisplayItemAdapter();
        }
        return this.loggerDisplayItemAdapter;
    }
    
    /* Overrides */

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if (changed) {
            FrameLayout.LayoutParams layoutParams = (LayoutParams) getLoggerRecyclerView().getLayoutParams();
            layoutParams.height = (bottom - top) / 3;
            layoutParams.topMargin = bottom - top - layoutParams.height;
            getLoggerRecyclerView().setLayoutParams(layoutParams);
        }
    }

    /* Delegates */
    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        if (left == oldLeft
                && top == oldTop
                && right == oldRight
                && bottom == oldBottom) {
            return;
        }

        self.setLeft(left);
        self.setTop(top);
        self.setRight(right);
        self.setBottom(bottom);
    }

     
    /* Private Methods */
    
}