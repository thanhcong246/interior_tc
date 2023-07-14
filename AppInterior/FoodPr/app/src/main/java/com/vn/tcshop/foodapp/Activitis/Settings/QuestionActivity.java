package com.vn.tcshop.foodapp.Activitis.Settings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.vn.tcshop.foodapp.R;

public class QuestionActivity extends AppCompatActivity {
    private View closeQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        closeQuestion = findViewById(R.id.closeQuestion);
        closeQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(QuestionActivity.this, SettingActivity.class));
                finish();
            }
        });
    }
}