package com.example.vb.firebaseblogapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //kendi yazdığımız menuyu projeye dahil ediyoruz
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //eğer menudeki item'e tıklanınca çalışan metodumuz
        if (item.getItemId()==R.id.action_Add){

            //post activity çağırıyoruz
            startActivity(new Intent(MainActivity.this,PostActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
}
