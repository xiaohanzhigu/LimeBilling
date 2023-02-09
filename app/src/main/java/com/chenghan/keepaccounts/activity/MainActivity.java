package com.chenghan.keepaccounts.activity;

import android.os.Bundle;

import com.chenghan.keepaccounts.R;
import com.chenghan.keepaccounts.base.BaseActivity;
import com.chenghan.keepaccounts.bean.DisburseIncome;
import com.chenghan.keepaccounts.databinding.ActivityMainBinding;
import com.chenghan.keepaccounts.presenter.MainPresenter;
import com.chenghan.keepaccounts.view.IMainView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.List;


public class MainActivity extends BaseActivity<MainPresenter, IMainView> implements IMainView{

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main2);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this);
    }

    @Override
    public void showMessage(String msg) {

    }

}