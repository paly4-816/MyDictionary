package com.example.mydictionary;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton addWordButton;
    private BottomNavigationView navigationView;
    private NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
    private void init(){

        navController = Navigation.findNavController(this,R.id.nav_host_fragment);
        navigationView = findViewById(R.id.bottom_navigation_menu);
        NavigationUI.setupWithNavController(navigationView,navController);
        addWordButton = findViewById(R.id.add_word_button);
        addWordButton.setOnClickListener(e->{
            navController.navigate(R.id.add_word_item);
        });
        navController.addOnDestinationChangedListener((navController, navDestination, bundle) -> {

            viewAnimation(addWordButton, navDestination.getId() == R.id.list_item);

            viewAnimation(navigationView, navDestination.getId() != R.id.add_word_item);
        });

    }

    private void viewAnimation(View view,boolean flag) {
        Animation animation = null;
        if (flag && view.getVisibility() == View.INVISIBLE) {
            animation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.show_nav);
            view.setVisibility(View.VISIBLE);

        } else if (!flag && view.getVisibility() == View.VISIBLE) {
            animation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.hide_nav);
            view.setVisibility(View.INVISIBLE);
        }

        if (animation != null) {
            view.startAnimation(animation);
        }
    }

}