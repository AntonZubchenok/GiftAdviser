package by.zubchenok.gitfadvicer.data;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import by.zubchenok.gitfadvicer.R;

import static by.zubchenok.gitfadvicer.data.GiftContract.GiftEntry.COLUMN_IMAGE;
import static by.zubchenok.gitfadvicer.data.GiftContract.GiftEntry.COLUMN_NAME;

public class GiftCursorRecyclerViewAdapter
        extends CursorRecyclerViewAdapter<GiftCursorRecyclerViewAdapter.GiftViewHolder> {

    private final LayoutInflater layoutInflater;
    private final OnItemClickListener onItemClickListener;

    public GiftCursorRecyclerViewAdapter(Context context, OnItemClickListener listener) {
        super();
        this.layoutInflater = LayoutInflater.from(context);
        this.onItemClickListener = listener;
    }

    @Override
    public void onBindViewHolder(GiftViewHolder holder, Cursor cursor) {
        holder.bindData(cursor, onItemClickListener);
    }

    @Override
    public GiftViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = this.layoutInflater.inflate(R.layout.list_item, parent, false);
        return new GiftViewHolder(view);
    }

    public static class GiftViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_gift)
        ImageView image;
        @BindView(R.id.text_gift_name)
        TextView name;

        public GiftViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(final Cursor cursor, final OnItemClickListener listener) {
            final String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
            final String image = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE));
            int imageId = this.image.getContext().getResources().getIdentifier(image, "drawable",
                    this.image.getContext().getPackageName());

            this.name.setText(name);
            this.image.setImageResource(imageId);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClicked(cursor);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClicked(Cursor cursor);
    }
}
