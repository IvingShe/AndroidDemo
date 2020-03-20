package cn.iving.demo.customView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import cn.iving.demo.androiddemoapp.R;

/**
 * @author Iving
 * @description custom view demo
 * @date on 2019/6/10
 **/
public class CircleView extends View {

    private static final String TAG=CircleView.class.getSimpleName();
    private int mColor;
    private float mReduis;

    public CircleView(Context context,  AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray= context.obtainStyledAttributes(attrs, R.styleable.CircleView);
        mReduis= typedArray.getDimension(R.styleable.CircleView_radius,(float)30.9);
        mColor= typedArray.getColor(R.styleable.CircleView_mycolor,0xf2f0f0);
        typedArray.recycle();
        Log.d(TAG,"CircleView----mReduis="+mReduis+",mColor="+mColor);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d(TAG,"--onMeasure--");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width= getMySize(50,widthMeasureSpec);
        int heigth= getMySize(100,heightMeasureSpec);
        Log.d(TAG,"onMeasure-width="+width+",heigth="+heigth);

        setMeasuredDimension(250,150);
        setBackgroundColor(mColor);

    }

    private int getMySize(int defaultSize, int measureSpec) {
        int mySize = defaultSize;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        Log.d(TAG,"getMySize-->size="+size);
        switch (mode) {
            case MeasureSpec.UNSPECIFIED: {//如果没有指定大小，就设置为默认大小
                Log.d(TAG,"getMySize-->MeasureSpec.UNSPECIFIED="+mode);
                mySize = defaultSize;
                break;
            }
            case MeasureSpec.AT_MOST: {//如果测量模式是最大取值为size
                //我们将大小取最大值,你也可以取其他值
                Log.d(TAG,"getMySize-->MeasureSpec.AT_MOST="+mode);
                mySize = size;
                break;
            }
            case MeasureSpec.EXACTLY: {//如果是固定的大小，那就不要去改变它
                Log.d(TAG,"getMySize-->MeasureSpec.EXACTLY="+mode);
                mySize = size;
                break;
            }
        }
        return mySize;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG,"--onDraw--");
        super.onDraw(canvas);


    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d(TAG,"--onLayout--");
    }
}
