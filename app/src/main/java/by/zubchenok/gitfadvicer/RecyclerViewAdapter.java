package by.zubchenok.gitfadvicer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.GiftViewHolder> {

    public static class GiftViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;
        TextView name;
        ImageView image;

        public GiftViewHolder(View itemView) {
            super(itemView);

            recyclerView = (RecyclerView) itemView.findViewById(R.id.rv);
            name = (TextView) itemView.findViewById(R.id.tv_name);
            image = (ImageView) itemView.findViewById(R.id.iv_image);
        }
    }

    List<Gift> gifts;

    public RecyclerViewAdapter(List<Gift> gifts) {
        this.gifts = gifts;
    }

    @Override
    public GiftViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        GiftViewHolder viewHolder = new GiftViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(GiftViewHolder holder, int position) {
        Context context = holder.image.getContext();
        int imageId = context.getResources().getIdentifier(gifts.get(position).getImageId(),
                "drawable",context.getPackageName());
        holder.name.setText(gifts.get(position).getName());
        holder.image.setImageResource(imageId);
    }

    @Override
    public int getItemCount() {
        return gifts.size();
    }
}
