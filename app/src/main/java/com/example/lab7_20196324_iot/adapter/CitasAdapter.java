package com.example.lab7_20196324_iot.adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab7_20196324_iot.R;
import com.example.lab7_20196324_iot.VerCita;
import com.example.lab7_20196324_iot.entity.CitasDto;

import java.util.List;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;


public class CitasAdapter extends RecyclerView.Adapter<CitasAdapter.ViewHolder>{

    private List<CitasDto> citasList;
    private Context context;


    public CitasAdapter(List<CitasDto> citasList) {
        this.citasList = citasList;

        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cliente, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CitasAdapter.ViewHolder holder, int position) {


        CitasDto cita = citasList.get(position);
        holder.tvcliente.setText(cita.getCorreoCliente());
        holder.tvhora.setText(cita.getHora());
        holder.tvservicio.setText(cita.getServicio());

        holder.recCard.setOnClickListener(view -> {
            Intent intent = new Intent(context, VerCita.class);
            intent.putExtra("cliente", citasList.get(holder.getAdapterPosition()).getCorreoCliente());
            intent.putExtra("hora", citasList.get(holder.getAdapterPosition()).getHora());
            intent.putExtra("servicio", citasList.get(holder.getAdapterPosition()).getServicio());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        if (citasList != null) {
            return citasList.size();
        } else {
            return 0; // Manejo del caso en el que actividadList es nula
        }
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvhora, tvservicio,tvcliente;
        //public TextView tvnumeventos;

        public ImageView imagenactividad;
        CardView recCard;

        public ViewHolder(View itemView) {
            super(itemView);
            //imagenactividad = itemView.findViewById(R.id.imagenactividad);
            tvhora = itemView.findViewById(R.id.tvhora);
            tvservicio = itemView.findViewById(R.id.tvservicio);
            //tvnumeventos = itemView.findViewById(R.id.tvnumeventos);
            tvcliente = itemView.findViewById(R.id.tvcliente);
            recCard = itemView.findViewById(R.id.recCard);
        }
    }

}
