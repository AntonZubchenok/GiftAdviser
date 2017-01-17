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

public class GiftCursorRecyclerViewAdapter
        extends CursorRecyclerViewAdapter<GiftCursorRecyclerViewAdapter.GiftViewHolder>
        implements View.OnClickListener {

    public static final String GIFT_EXTRA = "gift";
    private final LayoutInflater layoutInflater;
    private OnItemClickListener onItemClickListener;

    public GiftCursorRecyclerViewAdapter(Context context) {
        super();
        this.layoutInflater = LayoutInflater.from(context);
    }

    public void setOnItemClickListener(final OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    @Override
    public void onBindViewHolder(GiftViewHolder holder, Cursor cursor) {
        holder.bindData(cursor);
    }

    @Override
    public GiftViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = this.layoutInflater.inflate(R.layout.list_item, parent, false);
        view.setOnClickListener(this);
        return new GiftViewHolder(view);
    }

    @Override
    public void onClick(View view) {
        if (this.onItemClickListener != null)
        {
            //TODO сделать обработку нажатий

//            final RecyclerView recyclerView = (RecyclerView) view.getParent();
//            final int position = recyclerView.getChildLayoutPosition(view);
//            if (position != RecyclerView.NO_POSITION)
//            {
//                final Cursor cursor = this.getItem(position);
//                this.onItemClickListener.onItemClicked(cursor);
//            }

        }
    }

    public static class GiftViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_image)
        ImageView image;
        @BindView(R.id.tv_name)
        TextView name;


        public GiftViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(final Cursor cursor) {
            final String name = cursor.getString(cursor.getColumnIndex("name"));
            final int imageId = cursor.getInt(cursor.getColumnIndex("image"));
            int image = this.image.getContext().getResources().getIdentifier(String.valueOf(imageId),
                    "drawable", this.image.getContext().getPackageName());

            this.name.setText(name);
            this.image.setImageResource(image);
        }
    }

    public interface OnItemClickListener {
        void onItemClicked(Cursor cursor);
    }
}
