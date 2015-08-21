package blogs.dinesh.com.everything;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.din.utils.Blog;
import com.din.utils.DatabaseHandler;
import com.din.utils.ImageHelper;
import com.melnykov.fab.FloatingActionButton;

import java.util.List;


public class MainActivity extends AppCompatActivity
        implements NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private Toolbar mToolbar;
    DatabaseHandler db;
    Toast toast;
    Context context;
    //    DatabaseHandler db;
    List<Blog> blogs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);

        setSupportActionBar(mToolbar);

        context = this;
        db = new DatabaseHandler(this);
        RecyclerView rv = (RecyclerView)findViewById(R.id.recycler_view);
          /* Fab related codes  */
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.attachToRecyclerView(rv);
        rv.setHasFixedSize(false);
        fab.setType(FloatingActionButton.TYPE_NORMAL);
        fab.setColorNormal(getResources().getColor(R.color.primary));
        fab.setColorPressed(getResources().getColor(R.color.accent));
        fab.setShadow(true);
        fab.setColorRipple(getResources().getColor(R.color.ripple));
        /* Fab related codes  */
        final Blog temp = new Blog();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new MaterialDialog.Builder(MainActivity.this)
                        .title("Add Blog")
                        .inputMaxLengthRes(20, android.R.color.holo_red_dark)
                        .input(null, null, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                // Do something
                                temp.setTitle((String) input.toString());
                            }
                        }).input(null, null, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        try {
                            temp.setDesc((String) input.toString());
                            Blog blot = new Blog(temp.getTitle(), temp.getDesc());
                            db.addBlog(blot);
                        }catch(Exception e){
                            Toast.makeText(MainActivity.this, "DB Error",Toast.LENGTH_LONG).show();
                        }
                    }
                }).show();


               /* new MaterialDialog.Builder(MainActivity.this)
                        .title("Insert Data")
                        .content("content")
                        .inputType(InputType.TYPE_CLASS_TEXT)
                        .input("Enter title", null, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
//                                tit = input.toString();
                                temp.setTitle(input.toString());
                            }
                        })
                        .inputType(InputType.TYPE_CLASS_TEXT)
                        .input("Enter Description", null, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                temp.setDesc(input.toString());
                            }
                        })
                       *//* .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Blog blot = new Blog(temp.getTitle(),temp.getDesc());
                        db.addBlog(blot);
                    }
                })*//*.show();
*/

            }
        });
        db.truncTable();
        //DATA PART
        byte[] img = getBytes();
        db.addBlog(new Blog(img,"Dinesh","Rocks in Mars","Mars in the Solar system. What is the big fuss about mars? Its yet another big stone floating around the space "));
        db.addBlog(new Blog(img, "Simon", "Water in Mars", "There was a huge volume of Water visible in Mars."));
        db.addBlog(new Blog(img, "Beagle", "New intergalactic system", "Mars in the Solar system. What is the big fuss about mars? Its yet another big stone floating around the space "));
        db.addBlog(new Blog(img, "Antony", "Sand storm", "There was a huge volume of Water visible in Mars."));
        db.addBlog(new Blog(img, "Michael", "Milky way", "Mars in the Solar system. What is the big fuss about mars? Its yet another big stone floating around the space . There was a huge volume of Water visible in Mars."));
        blogs = (List<Blog>)db.getAllBlogs();
        if(!blogs.isEmpty()) {
            try {
                initializeAdapter(rv, blogs);
                rv.addOnItemTouchListener(

                        new RecyclerItemClickListener(context, new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                                Blog currData = blogs.get(position);
                                dialog(currData);
//                            toast.makeText(MainActivity.this, position, Toast.LENGTH_LONG).show();
                            }
                        })
                );
            }catch (Exception e)
            {
                new MaterialDialog.Builder(this)
                        .title("Oops!!")
                        .content("Fuzzy DB Logic, ArrayIndex out of bound exception")
                        .positiveText("OK")
                        .show();
            }

        }else{
            new MaterialDialog.Builder(this)
                    .title("Oops!!")
                    .content("No Post Available")
                    .positiveText("OK")
                    .show();
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        rv.setLayoutManager(linearLayoutManager);
        registerForContextMenu(rv);

//        db.addBlog(new Blog(img,"Dinesh","Rocks in Mars","There was a huge roccks visible in Mars."));
//        db.addBlog(new Blog(img, "Dinesh", "Water in Mars", "There was a huge volume of Water visible in Mars."));


        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.fragment_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setup(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer), mToolbar);
//        // populate the navigation drawer
        mNavigationDrawerFragment.setUserData("Dinesh", "dineshr93@gmail.com", BitmapFactory.decodeResource(getResources(), R.drawable.avatar));



    }

    public byte[] getBytes() {
        Bitmap icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.avatar);
        ImageHelper imageHelper = new ImageHelper();
        return imageHelper.bitmapToByte(icon);
    }

    public void dialog(final Blog currData){
        String[] options = {"Delete","Edit"};
        new MaterialDialog.Builder(this)
                .title("Options")
                .items(options)
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        Toast.makeText(MainActivity.this,text,Toast.LENGTH_LONG).show();
                        if(text == "Edit"){

                        }else{
//                            DatabaseHandler handle = new DatabaseHandler(context);
                            blogs.remove(currData);
                            db.deleteBlog(currData);


                        }

                        return true;
                    }
                })
               /* .positiveText("Choose")*/
                .show();
    }
    /*@Override
    public boolean onContextItemSelected(MenuItem item) {
        int position = -1;
        try {
            position =
        } catch (Exception e) {
          //  Log.d(TAG, e.getLocalizedMessage(), e);
            return super.onContextItemSelected(item);
        }
        switch (item.getItemId()) {
            case R.id.title:
                // do your stuff
                break;
            case R.id.:
                // do your stuff
                break;
        }
        return super.onContextItemSelected(item);

    }*/


/* @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }*/


    private void initializeAdapter(RecyclerView rv,List<Blog> blogs){
        RVAdapter adapter = new RVAdapter(blogs);
        rv.setAdapter(adapter);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        Toast.makeText(this, "Menu item selected -> " + position, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen())
            mNavigationDrawerFragment.closeDrawer();
        else
            super.onBackPressed();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
