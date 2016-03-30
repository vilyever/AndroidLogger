package com.vilyever.logger;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * LoggerDisplayDividerItemDecoration
 * ESchoolbag <com.ftet.recyclerdisplay>
 * Created by vilyever on 2016/3/4.
 * Feature:
 * item间隙
 * 注意：暂时只支持{@link LinearLayoutManager},{@link GridLayoutManager}
 * 注意：仅保证每个item间距为设定值，不保证{@link android.support.v7.widget.RecyclerView.LayoutManager#getLeftDecorationWidth(View)}之类方法的具体数值
 */
public class LoggerDisplayDividerItemDecoration extends RecyclerView.ItemDecoration {
    final LoggerDisplayDividerItemDecoration self = this;

    
    /* Constructors */
    public LoggerDisplayDividerItemDecoration() {
        this(0, 0);
    }

    public LoggerDisplayDividerItemDecoration(int horizontalSpace, int verticalSpace) {
        this(horizontalSpace, verticalSpace, true);
    }

    public LoggerDisplayDividerItemDecoration(int horizontalSpace, int verticalSpace, boolean edgeSpacing) {
        this.horizontalSpace = horizontalSpace;
        this.verticalSpace = verticalSpace;
        this.edgeSpacing = edgeSpacing;
    }
    
    
    /* Public Methods */
    
    
    /* Properties */
    /**
     * 横向间隔空间
     */
    private int horizontalSpace;
    public int getHorizontalSpace() {
        return horizontalSpace;
    }
    public LoggerDisplayDividerItemDecoration setHorizontalSpace(int horizontalSpace) {
        this.horizontalSpace = horizontalSpace;
        return this;
    }

    /**
     * 纵向间隔空间
     */
    private int verticalSpace;
    public int getVerticalSpace() {
        return verticalSpace;
    }
    public LoggerDisplayDividerItemDecoration setVerticalSpace(int verticalSpace) {
        this.verticalSpace = verticalSpace;
        return this;
    }

    /**
     * 边缘是否有间隔空间
     */
    private boolean edgeSpacing;
    public boolean isEdgeSpacing() {
        return edgeSpacing;
    }
    public LoggerDisplayDividerItemDecoration setEdgeSpacing(boolean edgeSpacing) {
        this.edgeSpacing = edgeSpacing;
        return this;
    }

    
    /* Overrides */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //We can supply forced insets for each item view here in the Rect
        int position = parent.getChildAdapterPosition(view);
        int itemCount = parent.getAdapter().getItemCount();

        int left, top, right, bottom;
        left = top = right = bottom = 0;

        if (parent.getLayoutManager() instanceof GridLayoutManager) {
            GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
            int spanCount = layoutManager.getSpanCount();
            int spanIndex = position % spanCount;

            int crossCount = itemCount / spanCount + 1;
            int crossIndex = position / spanCount;

            if (layoutManager.getOrientation() == LinearLayoutManager.HORIZONTAL) {
                if (isEdgeSpacing()) {
                    top = getVerticalSpace() * (spanCount - spanIndex) / spanCount;
                    bottom = getVerticalSpace() * (spanIndex + 1) / spanCount;

                    if (!layoutManager.getReverseLayout()) {
                        left = getHorizontalSpace() * (crossCount - crossIndex) / crossCount;
                        right = getHorizontalSpace() * (crossIndex + 1) / crossCount;
                    }
                    else {
                        right = getHorizontalSpace() * (crossCount - crossIndex) / crossCount;
                        left = getHorizontalSpace() * (crossIndex + 1) / crossCount;
                    }
                }
                else {
                    top = getVerticalSpace() * spanIndex / spanCount;
                    bottom = getVerticalSpace() * (spanCount - spanIndex - 1) / spanCount;

                    if (!layoutManager.getReverseLayout()) {
                        left = getHorizontalSpace() * crossIndex / crossCount;
                        right = getHorizontalSpace() * (crossCount - crossIndex - 1) / crossCount;
                    }
                    else {
                        right = getHorizontalSpace() * crossIndex / crossCount;
                        left = getHorizontalSpace() * (crossCount - crossIndex - 1) / crossCount;
                    }
                }
            }
            else {
                if (isEdgeSpacing()) {
                    left = getHorizontalSpace() * (spanCount - spanIndex) / spanCount;
                    right = getHorizontalSpace() * (spanIndex + 1) / spanCount;

                    if (!layoutManager.getReverseLayout()) {
                        top = getVerticalSpace() * (crossCount - crossIndex) / crossCount;
                        bottom = getVerticalSpace() * (crossIndex + 1) / crossCount;
                    }
                    else {
                        bottom = getVerticalSpace() * (crossCount - crossIndex) / crossCount;
                        top = getVerticalSpace() * (crossIndex + 1) / crossCount;
                    }
                }
                else {
                    left = getHorizontalSpace() * spanIndex / spanCount;
                    right = getHorizontalSpace() * (spanCount - spanIndex - 1) / spanCount;

                    if (!layoutManager.getReverseLayout()) {
                        top = getVerticalSpace() * crossIndex / crossCount;
                        bottom = getVerticalSpace() * (crossCount - crossIndex - 1) / crossCount;
                    }
                    else {
                        bottom = getVerticalSpace() * crossIndex / crossCount;
                        top = getVerticalSpace() * (crossCount - crossIndex - 1) / crossCount;
                    }
                }
            }
        }
        else if (parent.getLayoutManager() instanceof LinearLayoutManager) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();

            if (layoutManager.getOrientation() == LinearLayoutManager.HORIZONTAL) {
                if (isEdgeSpacing()) {
                    top = bottom = getVerticalSpace();

                    if (!layoutManager.getReverseLayout()) {
                        left = getHorizontalSpace() * (itemCount - position) / itemCount;
                        right = getHorizontalSpace() * (position + 1) / itemCount;
                    }
                    else {
                        right = getHorizontalSpace() * (itemCount - position) / itemCount;
                        left = getHorizontalSpace() * (position + 1) / itemCount;
                    }
                }
                else {
                    if (!layoutManager.getReverseLayout()) {
                        left = getHorizontalSpace() * position / itemCount;
                        right = getHorizontalSpace() * (itemCount - position - 1) / itemCount;
                    }
                    else {
                        right = getHorizontalSpace() * position / itemCount;
                        left = getHorizontalSpace() * (itemCount - position - 1) / itemCount;
                    }
                }
            }
            else {
                if (isEdgeSpacing()) {
                    left = right = getHorizontalSpace();

                    if (!layoutManager.getReverseLayout()) {
                        top = getVerticalSpace() * (itemCount - position) / itemCount;
                        bottom = getVerticalSpace() * (position + 1) / itemCount;
                    }
                    else {
                        bottom = getVerticalSpace() * (itemCount - position) / itemCount;
                        top = getVerticalSpace() * (position + 1) / itemCount;
                    }
                }
                else {
                    if (!layoutManager.getReverseLayout()) {
                        top = getVerticalSpace() * position / itemCount;
                        bottom = getVerticalSpace() * (itemCount - position - 1) / itemCount;
                    }
                    else {
                        bottom = getVerticalSpace() * position / itemCount;
                        top = getVerticalSpace() * (itemCount - position - 1) / itemCount;
                    }
                }
            }
        }

        outRect.set(left, top, right, bottom);
    }


    /* Delegates */
     
     
    /* Private Methods */
    
}