package com.example.locarv2.Adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.locarv2.R;

public class ListMobilAdaptor extends RecyclerView.Adapter<ListMobilAdaptor.MyViewHolder> {

    String data1[], data2[];
    int imageData[];
    Context context;


    public ListMobilAdaptor(Context ct, String cars[], String prices[], int images[]){
        data1 = cars;
        data2 = prices;
        context = ct;
        imageData = images;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cari_mobil_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.carsText.setText(data1[position]);
        holder.priceText.setText(data2[position]);
        holder.carsImg.setImageResource(imageData[position]);
    }

    @Override
    public int getItemCount() {
        return data1.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView carsText, priceText;
        ImageView carsImg;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            carsText = itemView.findViewById(R.id.carsName);
            priceText = itemView.findViewById(R.id.carsPrice);
            carsImg = itemView.findViewById(R.id.carsImage);
        }
    }
}
