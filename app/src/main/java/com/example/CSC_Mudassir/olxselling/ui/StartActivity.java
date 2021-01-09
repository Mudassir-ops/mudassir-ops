package com.example.CSC_Mudassir.olxselling.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.CSC_Mudassir.R;
import com.example.CSC_Mudassir.freelancing.FreeLancingHomeActivity;
import com.example.CSC_Mudassir.noticeboard.NoticeBoardActivity;
import com.google.firebase.auth.FirebaseAuth;

public class StartActivity extends AppCompatActivity {
    Button sell_note_main, get_work_main, notice_board;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        sell_note_main = (Button) findViewById(R.id.sell_notes_main);
        get_work_main = (Button) findViewById(R.id.get_work_main);
        notice_board = (Button) findViewById(R.id.notice_board);
        firebaseAuth = FirebaseAuth.getInstance();
        ////This method will Start the Olx?Selling Module///////////////
        sell_note_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this, HomeActivity.class);

                startActivity(intent);
                //  startActivity(new Intent(Start.this,Home.class));
            }
        });


        ////////////This method will start the freelancing module//////////////
        get_work_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ///  startActivity(new Intent(MainActivity.this,Freelancing_Activity.class));
                if(firebaseAuth.getCurrentUser()!=null){
                    firebaseAuth.signOut();
                    startActivity(new Intent(StartActivity.this, FreeLancingHomeActivity.class));
                }else{
                    startActivity(new Intent(StartActivity.this, FreeLancingHomeActivity.class));
                }

            }
        });


        notice_board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(StartActivity.this, NoticeBoardActivity.class));

               /* if(firebaseAuth.getCurrentUser()!=null){

                }else{
                    startActivity(new Intent(StartActivity.this,AdminLoginActivity.class));
                }*/


            }
        });
    }


}