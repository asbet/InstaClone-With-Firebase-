package com.betulas.instagramclonejava.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.betulas.instagramclonejava.databinding.RecyclerRowBinding;
import com.betulas.instagramclonejava.model.Post;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

//1. Post holder oluşturuyoruz
public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostHolder> {
//3. PostAdater geip implement etmemiz gerekir

    //4.Arraylisti istiyoruz
    private ArrayList<Post>postArrayList;

    //9.adım
    public PostAdapter(ArrayList<Post>postArrayList){
        this.postArrayList=postArrayList;
    }



    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //8.adım
        RecyclerRowBinding recyclerRowBinding=RecyclerRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new PostHolder(recyclerRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {
    //9.adım
        holder.recyclerRowBinding.recylerViewUserEmailText.setText(postArrayList.get(position).email);
        holder.recyclerRowBinding.recylerViewCommentText.setText(postArrayList.get(position).commnet);

        Picasso.get().load(postArrayList.get(position).downloadUrl).into(holder.recyclerRowBinding.recyclerViewImageView);

    }

    @Override
    public int getItemCount() {
        //5. sayısına olduğu kadar
        return postArrayList.size();
    }

    //2.Post holder alt enter yaparz
    class PostHolder extends RecyclerView.ViewHolder{
        //6binding kulladığımız için RecyclerRow binding oluştururmamız gerekiyor
        RecyclerRowBinding recyclerRowBinding;

        public PostHolder(RecyclerRowBinding recyclerRowBinding) {
            //7. atıyoruz
            super(recyclerRowBinding.getRoot());
            this.recyclerRowBinding=recyclerRowBinding;
        }
    }
}
