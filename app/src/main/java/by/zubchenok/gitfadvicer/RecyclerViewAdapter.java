package by.zubchenok.gitfadvicer;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import by.zubchenok.gitfadvicer.data.Gift;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.GiftViewHolder> {
    private Context context;
    private List<Gift> gifts;
    public static final String GIFT_EXTRA = "gift";

    public RecyclerViewAdapter(Context context, List<Gift> gifts) {
        this.gifts = gifts;
        this.context = context;
    }

    @Override
    public GiftViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (!gifts.isEmpty()) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_empty, parent, false);
        }
        GiftViewHolder viewHolder = new GiftViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(GiftViewHolder holder, int position) {
        final Context context = holder.image.getContext();
        int imageId = context.getResources().getIdentifier(gifts.get(position).getImageId(),
                "drawable", context.getPackageName());
        holder.name.setText(gifts.get(position).getName());
        holder.image.setImageResource(imageId);
        holder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(context, ItemActivity.class);
                intent.putExtra(GIFT_EXTRA, gifts.get(position));
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return gifts.size();
    }

    public static class GiftViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RecyclerView recyclerView;
        ItemClickListener clickListener;
        private TextView name;
        private ImageView image;

        public GiftViewHolder(View itemView) {
            super(itemView);

            recyclerView = (RecyclerView) itemView.findViewById(R.id.rv);
            name = (TextView) itemView.findViewById(R.id.tv_name);
            image = (ImageView) itemView.findViewById(R.id.iv_image);

            itemView.setTag(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onClick(v, getLayoutPosition());
        }

        public void setClickListener(ItemClickListener itemClickListener) {
            this.clickListener = itemClickListener;
        }
    }

    public interface ItemClickListener {
        void onClick(View v, int position);
    }
}
