package br.com.fggs1.gs1project.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.fggs1.gs1project.R;
import br.com.fggs1.gs1project.helper.GS1Util;
import br.com.fggs1.gs1project.model.Product;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int HEADER = 0;
    private final int CHILD = 1;

    private Context context;
    private List<Product> products;

    public ProductAdapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
    }

    @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case HEADER:
                return new HeaderViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.product_header_item_list, parent, false));
            case CHILD:
                return new ChildViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.product_child_item_list, parent, false));
            default:
                return null;
        }
    }

    @Override public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        Product product = products.get(position);

        switch (getItemViewType(position)) {

            case HEADER:
                ((HeaderViewHolder) holder).lblName.setText(product.getName());
                ((HeaderViewHolder) holder).lblDescription.setText(product.getDescription());
                ((HeaderViewHolder) holder).lblValue.setText(
                    GS1Util.getFormattedValue(product.getValue()));
                Glide.with(context)
                    .load(product.getUrl())
                    .into(((HeaderViewHolder) holder).imgProduct);
                break;
            case CHILD:
                if (position == 1) {
                    ((ChildViewHolder) holder).separator.setVisibility(View.VISIBLE);
                } else {
                    ((ChildViewHolder) holder).separator.setVisibility(View.GONE);
                }
                ((ChildViewHolder) holder).lblName.setText(product.getName());
                ((ChildViewHolder) holder).lblValue.setText(
                    GS1Util.getFormattedValue(product.getValue()));
                Glide.with(context)
                    .load(product.getUrl())
                    .into(((ChildViewHolder) holder).imgProduct);
                break;
        }
    }

    @Override public int getItemCount() {
        return products != null ? products.size() : 0;
    }

    @Override public int getItemViewType(int position) {
        return position == 0 ? HEADER : CHILD;
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.img_product) public ImageView imgProduct;
        @Bind(R.id.txt_product_value) public TextView lblValue;
        @Bind(R.id.txt_product_name) public TextView lblName;
        @Bind(R.id.txt_description) public TextView lblDescription;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class ChildViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.separator) public View separator;
        @Bind(R.id.img_product) public ImageView imgProduct;
        @Bind(R.id.lbl_product_value) public TextView lblValue;
        @Bind(R.id.lbl_product_name) public TextView lblName;

        public ChildViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
