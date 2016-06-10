package picasso.picassoapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.os.Build.VERSION;

//app imports
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import picasso.picassoapp.API.DrawingAPI;
import picasso.picassoapp.models.CallbackJson;
import picasso.picassoapp.models.Drawing;
import picasso.picassoapp.models.SimpleCallback;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity
{
    private DrawingView drawView;
    private ImageButton currPaint;
    //private ImageButton saveButton;
    //private ImageButton delButton;
    private TextView text;
    private String name = "new_drawing";
    private boolean pressedYes = false;
    private List<Drawing> callback;
    private boolean selectedPostDrawing = false;
    private String nameToDraw;

    //TODO: FIND A WAY TO CLEAR THE DRAWING
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar bla = getSupportActionBar();
        bla.show();

        //this instantiates our drawing view class on our actual gui view named drawing in this case
        drawView = (DrawingView)findViewById(R.id.drawing);
        //delButton = (ImageButton)findViewById(R.id.erase_btn);
        //saveButton = (ImageButton)findViewById(R.id.save_btn);

        //text = (TextView) findViewById(R.id.RESPONSE);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menus, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.erase_btn:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                name = "new_drawing";
                drawView.deleteEverything();
                return true;
            case R.id.draw_btn:
                //
                //
                if(selectedPostDrawing)
                {
                    retroCall(nameToDraw);
                    selectedPostDrawing = false;
                }
                return true;
            case R.id.new_btn:
                //
                //
                Log.d("MIO","what the actual fuck");
                obtainName();
                return true;
            case R.id.save_btn:
                if(pressedYes)
                {
                    String API = "https://infinite-brushlands-67485.herokuapp.com";
                    Drawing retroSend = new Drawing();
                    retroSend.copyDrawing(drawView.saved);
                    retroSend.setName(name);
                    RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(API).build();
                    DrawingAPI api;
                    api = restAdapter.create(DrawingAPI.class);
                    api.postDrawing(retroSend, new Callback<CallbackJson>() {
                        @Override
                        public void success(CallbackJson callbackJson, Response response) {
                            Toast.makeText(MainActivity.this, "Successfully sent",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            //it failed
                        }
                    });
                    pressedYes = false;
                }
                return true;

            case R.id.show_list:
                // this make a get from retrofit and show a list etc. blablabl
                String url = "https://infinite-brushlands-67485.herokuapp.com";
                RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(url).build();
                DrawingAPI api;
                api = restAdapter.create(DrawingAPI.class);
                api.getDrawingList(new Callback<List<Drawing>>() {
                    @Override
                    public void success(List<Drawing> drawings, Response response) {
                        callback = drawings;
                        View menuItemView = findViewById(R.id.my_toolbar);
                        String words;
                        PopupMenu popup = new PopupMenu(MainActivity.this,menuItemView, Gravity.END);
                        //Inflating the Popup using xml file
                        //popup.getMenuInflater().inflate(R.menu.pop_menu, popup.getMenu());
                        //registering popup with OnMenuItemClickListener
                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){
                            public boolean onMenuItemClick(MenuItem item){
                                Toast.makeText(MainActivity.this, "you clicked:" + (String) item.getTitle(),Toast.LENGTH_SHORT).show();
                                nameToDraw = (String) item.getTitle();
                                selectedPostDrawing = true;
                                //sendStored = true;
                                //sendName = (String) item.getTitle();
                                return true;
                            }
                        });
                        for(int i = 0; i < callback.size(); i++)
                        {
                            words = callback.get(i).getName();
                            popup.getMenu().add(1,R.id.one,(i + 1),words);
                        }
                        popup.show(); //showing popup menu
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    public void obtainName()
    {
        boolean answer = false;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Title");
        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT); //| InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);
        //builder.show();
        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                pressedYes = true;
                name = input.getText().toString();
                //hola
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                name = "new_drawing";
                dialog.cancel();
            }
        });
        builder.show();
        //return answer;
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

    public void retroCall(String name)
    {
        String url = "https://infinite-brushlands-67485.herokuapp.com";
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(url).build();
        DrawingAPI api;
        api = restAdapter.create(DrawingAPI.class);
        api.drawStored(new Drawing(name), new Callback<SimpleCallback>() {
            @Override
            public void success(SimpleCallback callback, Response response) {
                Toast.makeText(MainActivity.this, callback.getStatus() ,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(MainActivity.this, "fuck", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
