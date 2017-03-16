package com.example.vb.firebaseblogapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private RecyclerView blog_list;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        blog_list= (RecyclerView) findViewById(R.id.rcView);
        blog_list.setHasFixedSize(true);
        blog_list.setLayoutManager(new LinearLayoutManager(this));
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Blog");

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Blog,BlogViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Blog, BlogViewHolder>
                (
                        Blog.class,
                        R.layout.blog_row,
                        BlogViewHolder.class,
                        mDatabase

                ) {
            @Override
            protected void populateViewHolder(BlogViewHolder viewHolder, Blog model, int position) {

                viewHolder.setTitle(model.getTitle());
                viewHolder.setDesc(model.getDesc());
                viewHolder.setImage(model.getImage(),getApplicationContext());
            }
        };

        blog_list.setAdapter(firebaseRecyclerAdapter);

    }

    public static class BlogViewHolder extends RecyclerView.ViewHolder{
        View mview;

        public BlogViewHolder(View itemView) {
            super(itemView);
            mview=itemView;
        }

        public void setTitle(String title){
            TextView post_title = (TextView) mview.findViewById(R.id.post_title);
            post_title.setText(title);

        }
        public void setDesc(String desc){
            TextView post_desc = (TextView) mview.findViewById(R.id.post_desc);
            post_desc.setText(desc);

        }
        public  void  setImage(String image, Context ctx){
            ImageView post_image = (ImageView) mview.findViewById(R.id.post_image);
            Picasso.with(ctx).load(image).into(post_image);
        }

    }


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
