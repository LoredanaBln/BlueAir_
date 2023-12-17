package com.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import org.w3c.dom.Text;

import java.sql.Connection;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private TextView accName, accEmail;
    private DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);
        createDropMenu(savedInstanceState);
        accName.setText(Login.accountName);
        accEmail.setText(Login.email);

    }

    public void createDropMenu(Bundle savedInstanceState){
        Toolbar toolbar = findViewById(R.id.toolbar); //Ignore red line errors
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav,
                R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragment_main()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
        accName = headerView.findViewById(R.id.account_name);
        accEmail = headerView.findViewById(R.id.account_email);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.nav_home) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragment_main()).commit();
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else if(item.getItemId() == R.id.nav_logout){
            startActivity(new Intent(MainActivity.this, Login.class));
            finish();
        }
        else if(item.getItemId() == R.id.nav_cart){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragment_cart()).commit();
            drawerLayout.closeDrawer(GravityCompat.START);

        }
        else if(item.getItemId() == R.id.nav_tickets){
            //TODO ADD MENU FOR BOOKED TICKETS
        }
        else if(item.getItemId() == R.id.nav_about){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragment_aboutus()).commit();
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        return true;
    }
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
