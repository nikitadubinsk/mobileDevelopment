package com.dubinsky.room;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private Context context;
    List<User> list;
    private View selected;

    UserAdapter(Context context, List<User> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        User user = list.get(position);
        holder.textID.setText(String.valueOf(user.id));
        holder.textName.setText(user.name);
        holder.textEmail.setText(user.email);
        holder.line.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (selected != null){
                    selected.setBackgroundColor(0);
                }
                v.setBackgroundColor(context.getColor(R.color.colorAccent));
                selected = v;
                User user = list.get(position);
                ((MainActivity) context).position = position;
                ((MainActivity) context).editName.setText(user.name);
                ((MainActivity) context).editEmail.setText(user.email);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView textID, textName, textEmail, textTelephone;
        LinearLayout line;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            textID = itemView.findViewById(R.id.textID);
            textName = itemView.findViewById(R.id.textName);
            textEmail = itemView.findViewById(R.id.textEmail);
            textTelephone = itemView.findViewById(R.id.textTelephone);
            line = itemView.findViewById(R.id.line);
        }
    }
}
