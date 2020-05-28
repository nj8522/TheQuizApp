package com.Nash.thequizapp.MVVM;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Nash.thequizapp.Fragments.OnItemClicked;
import com.Nash.thequizapp.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

   //Data List
    private List<Modal> modalList;
    public void setModalList(List<Modal> modalList) {
        this.modalList = modalList;
    }

    //OnItemClicked
    private OnItemClicked onItemClicked;

    public RecyclerAdapter(OnItemClicked onItemClicked){
         this.onItemClicked = onItemClicked;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_card_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.title.setText(modalList.get(position).getName());
        holder.desc.setText(modalList.get(position).desc);

        //Img
        String home_Image = modalList.get(position).img;
        Glide.with(holder.itemView.getContext())
                .load(home_Image)
                .centerCrop()
                .placeholder(R.color.splash_color)
                .into(holder.img);


    }

    @Override
    public int getItemCount() {
        if(modalList == null){
            return 0;
        }else{
            return modalList.size();
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title;
        TextView desc;
        ImageView img;
        Button try_it;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.home_title);
            desc = itemView.findViewById(R.id.home_desc);
            img = itemView.findViewById(R.id.home_image);
            try_it = itemView.findViewById(R.id.home_btn);

            try_it.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
             onItemClicked.selectedItem(getAdapterPosition());
        }
    }
}
