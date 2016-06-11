package picasso.picassoapp;

import android.graphics.Color;
import android.view.View;
import android.content.Context;
import android.util.AttributeSet;

//second part of the imports
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;

import picasso.picassoapp.models.Coordinates;
import picasso.picassoapp.models.Drawing;

/**
 * Created by jl on 3/9/16.
 */
public class DrawingView extends View
{
    //drawing path
    private Path drawPath;
    //drawing and canvas paint
    private Paint drawPaint, canvasPaint;
    //initial color
    private int paintColor = 0xFF660000;
    //canvas
    private Canvas drawCanvas;
    //canvas bitmap
    private Bitmap canvasBitmap;
    //the drawing that will be sent
    public Drawing saved;

    public DrawingView(Context context, AttributeSet attribute)
    {
        super(context,attribute);
        setupDrawing();
    }

    public void setupDrawing()
    {
        //get drawing area setup for interaction
        drawPath = new Path();
        drawPaint = new Paint();
        drawPaint.setColor(paintColor);

        //initial path properties research more on this, this might be important later
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(20);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);

        //instantiating canvas paint object
        canvasPaint = new Paint(Paint.DITHER_FLAG);

        //setup the saved drawing
        saved = new Drawing();


    }

    //this method is not used for now
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        //view given size
        super.onSizeChanged(w, h, oldw, oldh);
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);
    }


    @Override
    protected void onDraw(Canvas canvas)
    {
        //draw view
        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
        canvas.drawPath(drawPath, drawPaint);
    }

    //this method receives the input from the user and makes paths and draws said path on the screen
    //it also saves the paths on a list of coordinates
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        //detect user touch
        float touchX = event.getX();
        float touchY = event.getY();
        //TODO: IMPLEMENT A REAL TIME SENDING OF THE COORDINATES
        //IT IS RECOMENDED TO USE RETROFIT HERE FOR THAT CASE
        saved.addCord(new Coordinates(touchX,touchY));
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                drawPath.moveTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                drawPath.lineTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_UP:
                drawCanvas.drawPath(drawPath, drawPaint);
                drawPath.reset();
                break;
            default:
                return false;
        }
        //invalidate redraws the screen so that the path appears
        invalidate();
        return true;
    }

    //this clears the drawing
    public void deleteEverything()
    {
        saved.delete();
        //draws the whole canvas white again
        drawCanvas.drawColor(Color.WHITE);
        invalidate();  
    }

    //changes color for the paint
    public void setColor(String newColor)
    {
        //sets color
        invalidate();
        paintColor = Color.parseColor(newColor);
        drawPaint.setColor(paintColor);
    }
}

