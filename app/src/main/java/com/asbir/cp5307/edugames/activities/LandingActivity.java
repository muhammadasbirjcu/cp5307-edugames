package com.asbir.cp5307.edugames.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.asbir.cp5307.edugames.R;
import com.google.android.material.textfield.TextInputEditText;

public class LandingActivity extends AppCompatActivity {
    private TextInputEditText nameInput;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        saveButton = findViewById(R.id.playButton);
        nameInput = findViewById(R.id.name_input);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitName();
            }
        });

        nameInput.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    submitName();
                    return true;
                }
                return false;
            }
        });
    }

    private boolean submitName(){
        String name = nameInput.getText().toString().trim();
        if(name.length() <= 1){
            Toast.makeText(this, getResources().getString(R.string.name_too_short), Toast.LENGTH_SHORT);
            return false;
        }else{
            this.startGameActivity(name);
            return true;
        }
    }

    private void startGameActivity(String name){
        Intent gameIntent = new Intent(this, GameActivity.class);
        gameIntent.putExtra("name", name);
        startActivity(gameIntent);
        finish();
    }
}