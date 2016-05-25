package picasso.picassoapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.os.Build.VERSION;

//app imports
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import picasso.picassoapp.API.DrawingAPI;
import picasso.picassoapp.models.CallbackJson;
import picasso.picassoapp.models.Drawing;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity
{
    private DrawingView drawView;
    private ImageButton currPaint;
    private ImageButton saveButton;
    private ImageButton delButton;
    private TextView text;
    private String name;

    //TODO: FIND A WAY TO CLEAR THE DRAWING
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        //this instantiates our drawing view class on our actual gui view named drawing in this case
        drawView = (DrawingView)findViewById(R.id.drawing);
        delButton = (ImageButton)findViewById(R.id.erase_btn);
        saveButton = (ImageButton)findViewById(R.id.save_btn);

        text = (TextView) findViewById(R.id.RESPONSE);

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

        delButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                drawView.deleteEverything();
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                obtainName();
                String API = "https://infinite-brushlands-67485.herokuapp.com";
                //TODO: MAKE IT SO THAT THE USER CAN CHOOSE THE NAME, ALSO SO THAT YOU CAN CHANGE THE NAME IF IT FAILED ONCE
                name = "name2";
                Drawing retroSend = new Drawing();
                retroSend.copyDrawing(drawView.saved);
                retroSend.setName(name);
                RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(API).build();
                DrawingAPI api;
                api = restAdapter.create(DrawingAPI.class);
                api.postDrawing(retroSend, new Callback<CallbackJson>() {
                    @Override
                    public void success(CallbackJson json, Response response) {
                        text.setText(json.getStatus());
                        //it worked
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        //it failed
                        text.setText("it failed");

                    }
                });
            }
        });
    }


    public void obtainName()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Title");
        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                name = input.getText().toString();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
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
