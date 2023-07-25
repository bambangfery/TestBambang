package com.dans.bambang;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.dans.bambang.databinding.ActivityMainBinding;
import com.dans.bambang.presenter.LoginPresenter;
import com.dans.bambang.utils.PrefManager;
import com.dans.bambang.view.LoginActivity;
import com.dans.bambang.view.account.AccountFragment;
import com.dans.bambang.view.adapter.ViewPagerAdapter;
import com.dans.bambang.view.home.HomeFragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;

import java.lang.reflect.Field;


public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    private GoogleSignInClient mGoogleSignInClient;
    private ActivityMainBinding binding;
    private PrefManager pref ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        initView();
    }

    private void initView(){
        pref = new PrefManager(this);
        BottomNavigationViewHelper.removeShiftMode(binding.navigation);
        binding.navigation.setItemIconTintList(null);
        binding.viewPager.setOffscreenPageLimit(2);
        binding.viewPager.setSwipeable(false);
        setupViewPager(binding.viewPager);
        binding.navigation.setOnNavigationItemSelectedListener(this);
//        binding.btnLogout.setOnClickListener(this);
    }

    private void setupViewPager(ViewPager viewPager)
    {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomeFragment(), "Home");
        adapter.addFragment(new AccountFragment(), "Account");
        viewPager.setAdapter(adapter);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_item1:
                binding.viewPager.setCurrentItem(0);
                item.setChecked(true);
                break;
            case R.id.action_item2:
                binding.viewPager.setCurrentItem(1);
                item.setChecked(true);
                break;
        }
        return false;
    }


    static class BottomNavigationViewHelper {
        @SuppressLint("RestrictedApi")
        static void removeShiftMode(BottomNavigationView view) {
            BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);

            try {
                Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
                shiftingMode.setAccessible(true);
                shiftingMode.setBoolean(menuView, false);
                shiftingMode.setAccessible(false);
                for (int i = 0; i < menuView.getChildCount(); i++) {
                    BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
//                    item.setShiftingMode(false);
                    item.setShifting(false);
                    // set once again checked value, so view will be updated
                    item.setChecked(item.getItemData().isChecked());
                }
            } catch (NoSuchFieldException e) {
                Log.e("ERROR NO SUCH FIELD", "Unable to get shift mode field");
            } catch (IllegalAccessException e) {
                Log.e("ERROR ILLEGAL ALG", "Unable to change value of shift mode");
            }
        }
    }


}