package com.apps.gerdjes.myfirstdatabase;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.List;

import static android.R.attr.name;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener  {

    private MyDBAdapter mDbAdapter;
    private ListView mListView;
    private Spinner mFaculties;
    private EditText mStudentName;
    private String[] mAllFaculties = {"Engineering", "Business", "Arts"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeViews();
        initializeDatabase();
        loadList();
    }
    private void initializeViews(){

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mFaculties = (Spinner)findViewById(R.id.faculties_spinner);
        mFaculties.setAdapter(new ArrayAdapter<>(MainActivity.this,android.R.layout.simple_list_item_1,mAllFaculties));

        mStudentName = (EditText) findViewById(R.id.student_name);
        mListView = (ListView) findViewById(R.id.student_list);

        Button mAddStudent = (Button)findViewById(R.id.add_student);
        mAddStudent.setOnClickListener(this);

        Button mDeleteEngeneers = (Button)findViewById(R.id.delete_engineers);
        mDeleteEngeneers.setOnClickListener(this);
    }

    private void initializeDatabase()
    {
        mDbAdapter = new MyDBAdapter (MainActivity.this);
        mDbAdapter.open();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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
    private void loadList(){
        List<String>allStudents;
        allStudents = mDbAdapter.selectAllStudents();
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_list_item_1,allStudents);
        mListView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.add_student:
                mDbAdapter.insertStudent (mStudentName.getText().toString(), mFaculties.getSelectedItemPosition()+1);
                loadList();
                break;
            case  R.id.delete_engineers:
                mDbAdapter.deleteAllEngineers();
                loadList();
                break;
        }
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
