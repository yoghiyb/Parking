package com.example.yoghi.e_parking;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {


    private DrawerLayout drawer;
    private FirebaseAuth firebaseAuth;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (user == null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new ProfileFragment()).commit();
                        break;
                    case R.id.nav_booking:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new BookingFragment()).commit();
                        break;
                    case R.id.nav_tiket:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new TiketFragment()).commit();
                        break;
                    case R.id.nav_history:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new HistoryFragment()).commit();
                        break;
                    case R.id.nav_about:
                        openDialog();
//                        Toast.makeText(MainActivity.this, "Create by : Yoghi Yuna Burnama", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.nav_logout:
                        firebaseAuth.signOut();
                        finish();
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        break;
                }
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new ProfileFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_profile);
        }
    }

    public void openDialog(){
        DialogTim dialogTim = new DialogTim();
        dialogTim.show(getSupportFragmentManager(),"Dialog kelompok");
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
