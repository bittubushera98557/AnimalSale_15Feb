package com.example.lenovo.emptypro.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.net.sip.SipSession;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import com.example.lenovo.emptypro.Adapters.ViewPageAdapter;
import com.example.lenovo.emptypro.Fragments.AddForSaleFragKotlin;
import com.example.lenovo.emptypro.Fragments.ChatFragKotlin;
import com.example.lenovo.emptypro.Fragments.HomeFragKotlin;
import com.example.lenovo.emptypro.Fragments.ProfileFragKotlin;
import com.example.lenovo.emptypro.Listeners.OnFragmentInteractionListener;
import com.example.lenovo.emptypro.R;
import com.example.lenovo.emptypro.Utilities.Utilities;
import com.example.lenovo.emptypro.Utils.CustomViewPager;
import com.example.lenovo.emptypro.Utils.SharedPrefUtil;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnFragmentInteractionListener, FragmentManager.OnBackStackChangedListener {
    public CustomViewPager viewPager;
    //ViewPagerAdapter adapter;
    public BottomNavigationView bottomNavigation;
    HomeFragKotlin homeFragment;
    ChatFragKotlin chatFragment;
    AddForSaleFragKotlin addForSaleFragment;
    ProfileFragKotlin profileFragment;
    ViewPageAdapter adapter;
    ActionBarDrawerToggle toggle;
    Utilities utilities = new Utilities();
    private TextView mTextMessage;
    View.OnClickListener navigationBackPressListener;
    BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener;
    DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference().child("/AllChat");
    ValueEventListener allUsersListener= null;
String  TAG="MainActivity ";
    String  loggedUserId = "";

int chatCount=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigationBackPressListener = new View.OnClickListener() {
            public void onClick(View v) {
                onBackPressed();
            }
        };
        mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.bottom_navigation_home: {
                        getSupportActionBar().setTitle("Home");
                        if (getSupportFragmentManager().getBackStackEntryCount() > 0)
                            getSupportFragmentManager().popBackStack( null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        viewPager.setCurrentItem(0, true);
                        /*    return  (mOnNavigationItemSelectedListener)   true;
                         */
                        return true;
                    }


                    case R.id.bottom_navigation_profile: {
                        getSupportActionBar().setTitle("Profile");
                        if (getSupportFragmentManager().getBackStackEntryCount() > 0)
                            getSupportFragmentManager().popBackStack( null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                        viewPager.setCurrentItem(1, true);
                        return true;
                    }

                    case R.id.bottom_navigation_chat: {
                        getSupportActionBar().setTitle("Chat");
                        if (getSupportFragmentManager().getBackStackEntryCount() > 0)
                            getSupportFragmentManager().popBackStack();
                        viewPager.setCurrentItem(2, true);
                        return true;
                    }

                    case R.id.bottom_navigation_sell: {
                        getSupportActionBar().setTitle("Add For Sale");
                        if (getSupportFragmentManager().getBackStackEntryCount() > 0)
                            getSupportFragmentManager().popBackStack();
                        viewPager.setCurrentItem(3, true);
                        return true;
                    }

                }
                return false;

            }


        };

        initComponent();
        mTextMessage = (TextView) findViewById(R.id.message);
        //    <com.google.android.material.bottomnavigation.BottomNavigationView
        bottomNavigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //   setBOttomBarData();

        try{
       //     getAllPendingChat();

        }
catch (Exception exp)
{
    Log.e(TAG,"call getAllPendingChat excep="+exp.toString());
}
        setViewPageData();
        viewPager.setPagingEnabled(false);

        getSupportActionBar().setTitle("Home");
        loggedUserId= SharedPrefUtil.getUserId(MainActivity.this);

    }

    private void getAllPendingChat() {
        allUsersListener=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                chatCount=0;
                for (DataSnapshot data : dataSnapshot.getChildren()){
 String keyVal=data.getKey();
                    String strDB_OwnerId = data.child("ownerId").getValue().toString();
                    String strDB_intersterId= data.child("intersterId").getValue().toString();

                   /* for (DataSnapshot chatData : data.child("Chat").getChildren()){
                        String chatKeyVal= chatData.getKey();
                        //Log.e(TAG+"",chatKeyVal);
                    }*/
                    if (strDB_OwnerId == loggedUserId  ||  strDB_intersterId == loggedUserId)
                    {
                                         chatCount++;

                    }


                }


                 Log.e(TAG+"", "chatCount ="+chatCount);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MainActivity.this,"database error",
                        Toast.LENGTH_SHORT).show();
            }
        };


        dbReference.addValueEventListener(allUsersListener);
    }

    private void setViewPageData() {
        viewPager = (CustomViewPager) findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(4);

        adapter = new ViewPageAdapter(getSupportFragmentManager());
        homeFragment = new HomeFragKotlin();
        profileFragment = new ProfileFragKotlin();
        chatFragment = new ChatFragKotlin();
        addForSaleFragment = new AddForSaleFragKotlin();
        adapter.addFragment(homeFragment);
        adapter.addFragment(profileFragment);
        adapter.addFragment(chatFragment);
        adapter.addFragment(addForSaleFragment);
        viewPager.setAdapter(adapter);

/*         bottomNavigation.creab("", 4);*/
/*
        BottomNavigationMenuView bottomNavigationMenuView =
                (BottomNavigationMenuView) bottomNavigation.getChildAt(0);
        View v = bottomNavigation.getChildAt(2);
        BottomNavigationItemView itemView = (BottomNavigationItemView) v;

        View badge = LayoutInflater.from(this)
                .inflate(R.layout.homescreen_count, bottomNavigationMenuView, false);
        TextView tv = badge.findViewById(R.id.notification_badge);
        tv.setText("22+");
        itemView.addView(badge);*/

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent intent = new Intent(getApplication(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        } else if (id == R.id.nav_favorite) {
            Intent intent = new Intent(getApplication(), FavouritePets.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            //   finish();
        } else if (id == R.id.nav_sold) {
            Intent intent = new Intent(getApplication(), SoldPets.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            // finish();
        } else if (id == R.id.nav_feedback) {
            Intent intent = new Intent(getApplication(), FeedbackScreen.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            // finish();
        } else if (id == R.id.nav_termCondition) {
            Intent intent = new Intent(getApplication(), WebUrlActivity.class);
            intent.putExtra("from", "terms");
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        } else if (id == R.id.nav_privacy) {
            Intent intent = new Intent(getApplication(), WebUrlActivity.class);
            intent.putExtra("from", "privacy");
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        } else if (id == R.id.nav_aboutUs) {
            Intent intent = new Intent(getApplication(), WebUrlActivity.class);
            intent.putExtra("from", "about");
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        } else if (id == R.id.nav_uploaded_petes) {
            Intent intent = new Intent(getApplication(), UploadedPets.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            // finish();
        } else if (id == R.id.nav_share) {
            shareTextUrl();
        } else if (id == R.id.nav_logout) {
            utilities.alertLogoutDialog(MainActivity.this, "Want to Logout ?");

          /*  SharedPrefUtil.setUserId(MainActivity.this,"");
            Intent intent = new Intent(getApplication(), LoginSignUp.class);
            //       intent.putExtra("id", "" + id);
            //       intent.putExtra("firstName", "" + first_name);
            //      intent.putExtra("lastName", "" + last_name);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
           // finish();*/
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void shareTextUrl() {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        // Add data to the intent, the receiving app will decide
        // what to do with it.
        share.putExtra(Intent.EXTRA_SUBJECT, "VLovePets");
        share.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id="+getPackageName());

        startActivity(Intent.createChooser(share, "Share To Friends !"));
    }


    private void initComponent() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
     //   getSupportActionBar().setHomeButtonEnabled(true);
      //  getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        getSupportFragmentManager().addOnBackStackChangedListener(this);

        if (SharedPrefUtil.getUserId(this) != "") {
            TextView tv_loggedEmail = navigationView.getHeaderView(0).findViewById(R.id.textView);
            tv_loggedEmail.setText("Logged Mobile :- " + SharedPrefUtil.getUserMobile(this));
        }

    }

  /*  @Override
    public void currentItem(int pageNum) {
        viewPager.setCurrentItem(pageNum);

    }*/

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onBackStackChanged() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            toggle.setDrawerIndicatorEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toggle.setToolbarNavigationClickListener(navigationBackPressListener);
        } else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
            toggle.setDrawerIndicatorEnabled(true);

            toggle.setToolbarNavigationClickListener(toggle.getToolbarNavigationClickListener());
        }
    }
}