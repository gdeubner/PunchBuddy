package com.mean.punchbuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RoutineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine);
        setUpSpeedSpinner();
        fillSpinner(R.id.per_set,10);
        fillSpinner(R.id.number_of_sets, 10);
        fillSpinner(R.id.rest_time, 15);
    }

    private void setUpSpeedSpinner(){

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.speeds, R.layout.spinner_layout);

        adapter.setDropDownViewResource(R.layout.spinner_layout);
        Spinner sItems = (Spinner) findViewById(R.id.speed);
        sItems.setAdapter(adapter);
    }

    private void fillSpinner(int spinner, int num){
        List<String> spinnerArray =  new ArrayList<String>();
        for(int i = 1; i <= num; i++){
            spinnerArray.add(String.valueOf(i));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, R.layout.spinner_layout, spinnerArray);

        adapter.setDropDownViewResource(R.layout.spinner_layout);
        Spinner sItems = (Spinner) findViewById(spinner);
        sItems.setAdapter(adapter);
    }

    public void gotToWorkout(View view){
        Intent intent = new Intent(this, WorkoutActivity.class);
        startActivity(intent);
    }
}