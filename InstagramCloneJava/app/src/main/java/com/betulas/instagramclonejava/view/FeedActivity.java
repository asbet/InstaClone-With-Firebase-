package com.betulas.instagramclonejava.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.betulas.instagramclonejava.R;
import com.betulas.instagramclonejava.adapter.PostAdapter;
import com.betulas.instagramclonejava.databinding.ActivityFeedBinding;
import com.betulas.instagramclonejava.model.Post;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class FeedActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    FirebaseFirestore firebaseFirestore;
    ArrayList<Post> postArrayList;
    private ActivityFeedBinding bindig;
    PostAdapter postAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindig=ActivityFeedBinding.inflate(getLayoutInflater());
        View view=bindig.getRoot();
        setContentView(view);
        auth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        //Arraylisti initshize ettik
        postArrayList=new ArrayList<>();
        getData();
//10. Adım Hangi linear layoutu kullancaksak onu yazıcaz
        //Alt alta göstereceğimizi söylüyoruz
        bindig.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //11.adım
        postAdapter=new PostAdapter(postArrayList);
        bindig.recyclerView.setAdapter(postAdapter);

    }

    private void getData(){
        firebaseFirestore.collection("Posts").orderBy("date", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error !=null){
                    Toast.makeText(FeedActivity.this,error.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }
                if(value!=null){
                    //value.getDocuments() bize liste olarak dönüyor(getDocuments()) bu deperleri alamamız için for loop a almamız gerekiiyor
                    for(DocumentSnapshot snapshot: value.getDocuments()){
                        Map<String,Object> data=snapshot.getData();

                        //data.get yaptığında bize object olara döndürüyor ama biz Stringe çevirelim ki kullanıcıya gösterelim
                        // (String) ifadesini kullanıyoruz ve diyoruz ki bak kardeşim bu gerçekten string buna "CASTİNG " denir
                        String userEmail=(String) data.get("useremail");
                        String comment=(String) data.get("comment");
                        String downloadUrl=(String) data.get("downloadurl");
                        Post post=new Post(userEmail,comment,downloadUrl);
                        //Şimdi recycler view de göstermek için arraylist oluşturuyoruz
                        postArrayList.add(post);
                    }
                    //12.adım
                    postAdapter.notifyDataSetChanged();
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.option_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.add_post){
            Intent intent=new Intent(FeedActivity.this, UploadActivity.class);
            startActivity(intent);

        }else if(item.getItemId()==R.id.signout){
            //Sign out işlemi yapılıcak
            auth.signOut();

            Intent intentToMain=new Intent(FeedActivity.this, MainActivity.class);
            startActivity(intentToMain);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}