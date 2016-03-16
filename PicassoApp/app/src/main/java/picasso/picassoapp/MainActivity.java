package picasso.picassoapp;

import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.os.Build.VERSION;

//app imports
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity
{
    private DrawingView drawView;
    private ImageButton currPaint;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //this instantiates our drawing view class on our actual gui view named drawing in this case
        drawView = (DrawingView)findViewById(R.id.drawing);

        //retrieves the first paint color thingy
        LinearLayout paintLayout = (LinearLayout)findViewById(R.id.paint_colors);

        //getting the first button and storing it in a button instance
        currPaint = (ImageButton)paintLayout.getChildAt(0);

        //this changes the icon to selected(to the first icon)
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
        {
            currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
        }
        else
        {
            currPaint.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.paint_pressed));

        }
    }

    public void paintClicked(View view)
    {
        //use chosen color
        if(view != currPaint)
        {
            //update color
            ImageButton imgView = (ImageButton)view;
            String color = view.getTag().toString();
            drawView.setColor(color);

            //this changes the icon selected to the one the user selects
            if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
            {
                imgView.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
                currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint));
            }
            else
            {
                imgView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.paint_pressed));
                currPaint.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.paint));
            }
            currPaint=(ImageButton)view;
        }



    }
}
