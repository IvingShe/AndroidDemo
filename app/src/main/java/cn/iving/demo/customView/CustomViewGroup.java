package cn.iving.demo.customView;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author Iving
 * @description custom view group demo
 * @date on 2019/6/10
 **/
public class CustomViewGroup extends ViewGroup {

    private static  final  String TAG=CustomViewGroup.class.getSimpleName();

    public CustomViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    private void print(int mode){
        switch (mode){
            case MeasureSpec.AT_MOST:
                Log.d(TAG,"print-->MeasureSpec.AT_MOST=");
                break;
            case MeasureSpec.EXACTLY:
                Log.d(TAG,"print-->MeasureSpec.EXACTLY");

                break;
            case MeasureSpec.UNSPECIFIED:
                Log.d(TAG,"print-->MeasureSpec.UNSPECIFIED");
                break;
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int with = MeasureSpec.getSize(widthMeasureSpec);
        int height =  MeasureSpec.getSize(heightMeasureSpec);
        int withMode = MeasureSpec.getMode(widthMeasureSpec);
        int heigthMode = MeasureSpec.getMode(heightMeasureSpec);
        if (getChildCount() == 0) {//如果没有子View,当前ViewGroup没有存在的意义，不用占用空间
            setMeasuredDimension(0, 0);
            return;
        }
        print(withMode);
        print(heigthMode);

        if (withMode == MeasureSpec.AT_MOST && heigthMode == MeasureSpec.AT_MOST){
            //高度累加，宽度取最大
            setMeasuredDimension(getMaxChildWidth(),getTotleHeight());
        }else if (heigthMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(with,getTotleHeight());
        }else if (withMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(getMaxChildWidth(),height);
        }
    }
    /***
     * 获取子View中宽度最大的值
     */
    private int getMaxChildWidth() {
        int childCount = getChildCount();
        int maxWidth = 0;
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            if (childView.getMeasuredWidth() > maxWidth) {
                maxWidth = childView.getMeasuredWidth();
            }
        }
        return maxWidth;
    }

    /***
     * 将所有子View的高度相加
     **/
    private int getTotleHeight() {
        int childCount = getChildCount();
        int height = 0;
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            height += childView.getMeasuredHeight();
        }
        return height;
    }






    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        Log.d(TAG,"onLayout-->childCount="+childCount);
        int currentHeigth=0;
        for(int i=0;i<childCount;i++){
            View childView= getChildAt(i);
            int childWidth= childView.getMeasuredWidth();
            int childHeigth= childView.getMeasuredHeight();
            childView.layout(l, currentHeigth, r + childHeigth, currentHeigth + childHeigth);
            currentHeigth += childHeigth;
        }
    }



}
