package com.freedom.playpuzzlequizgame;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.freedom.playpuzzlequizgame.helper.PrefManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private Dialog myDialog;
    private CardView cResults;
    private CardView cPlayQuiz;
    private CardView cMentalTest;
    private CardView cMoreTest;
    private PrefManager pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDialog = new Dialog(this);
        pref = new PrefManager(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        cPlayQuiz = findViewById(R.id.c_playquiz);
        cResults = findViewById(R.id.c_results);
        cMentalTest = findViewById(R.id.c_mentaltest);
        cMoreTest = findViewById(R.id.c_moretest);

        PrefManager pref = new PrefManager(this);
        ((TextView) findViewById(R.id.txt_credit)).setText(pref.getCredits());
        ((TextView) findViewById(R.id.txt_success)).setText(pref.getSuccess());
        ((TextView) findViewById(R.id.txt_failed)).setText(pref.getFailed());

        cResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,
                        ActivityResults.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });

        cPlayQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPopupQuizMenu(v);
            }
        });

        cMentalTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,
                        ActivityMoreTest.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });

        cMoreTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,
                        ActivityMoreTest.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });

        findViewById(R.id.toolbar_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPopup(cMoreTest);
            }
        });

        findViewById(R.id.toolbar_settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goSettings(v);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        updateUI(currentUser);

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");
        myRef.child(currentUser.getUid());
        myRef.setValue(PrefManager.PREF_DB_NAME, currentUser.getDisplayName());

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Toast.makeText(MainActivity.this, "\"Value is: \" + value", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(MainActivity.this, "\"Failed to read value", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void goProfile(View v) {
        Intent intent = new Intent(MainActivity.this,
                ProfileActivity.class);
        //intent.setFlags(Intent.F