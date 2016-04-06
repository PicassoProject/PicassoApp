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

import picasso.picassoapp.models.Cordinates;
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

    private void setupDrawing()
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

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        //detect user touch
        float touchX = event.getX();
        float touchY = event.getY();
        //TODO: SAVE COORDINATES HERE AND FIND A WAY TO ACCESS IT ON THE MAIN ACTIVITY
        saved.addCord(new Cordinates(touchX,touchY));
        //okay this is the tricky part, FROM THIS I HAVE TO GET ARRAY OF COORDINATES
        // YEAH I KNOW FUCK MY LIFE, but i have to do this properly, so probably retrofit will go
        // here RIP ME LOL
        //okay i found something interesting that ill try later
        //basically i have my array right?
        // everytime i get an event i get the coordinates so before i do drawPath.lineTo
        // i save those coordinates
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

        invalidate();
        return true;
    }

    public void setColor(String newColor)
    {
        //sets color
        invalidate();
        paintColor = Color.parseColor(newColor);
        drawPaint.setColor(paintColor);
    }
}

