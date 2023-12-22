package com.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

public class Activity_Admin extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private TextView accName, accEmail, test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_admin);
        createDropMenu(savedInstanceState);
        accName.setText(Activity_login.accountName);
        accEmail.setText(Activity_login.email);
    }

    private void createDropMenu(Bundle savedInstanceState){
        Toolbar toolbar = findViewById(R.id.toolbar_admin); //Ignore red line errors
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout_admin);
        NavigationView navigationView = findViewById(R.id.nav_view_admin);
        View headerView = navigationView.getHeaderView(0);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav,
                R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_admin, new Fragment_admin_home()).commit();
            navigationView.setCheckedItem(R.id.nav_home_admin);
        }
        accName = headerView.findViewById(R.id.account_name);
        accEmail = headerView.findViewById(R.id.account_email);
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.nav_home_admin) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_admin, new Fragment_admin_home()).commit();
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        /*else if(item.getItemId() == R.id.nav_logout_admin){
            startActivity(new Intent(Activity_Admin.this, Activity_login.class));
            finish();
        }*/

        else if(item.getItemId() == R.id.nav_database){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_admin, new Fragment_Modify_database()).commit();
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