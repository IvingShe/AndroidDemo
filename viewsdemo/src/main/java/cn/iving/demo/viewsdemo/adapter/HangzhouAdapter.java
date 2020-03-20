package cn.iving.demo.viewsdemo.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.iving.demo.viewsdemo.R;

/**
 * data: 2019,5,8
 */
public class HangzhouAdapter extends RecyclerView.Adapter {


    private final List<String> mData;

    public HangzhouAdapter(List<String> data) {
        mData=data;
    }

    //创建View缓存；
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_hangzhou,null);
        return new HangZhouViewHolder(view);
    }

    //绑定数据；
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        HangZhouViewHolder hangZhouViewHolder = (HangZhouViewHolder)viewHolder;
        hangZhouViewHolder.mTvText.setText(mData.get(position));

    }

    @Override
    public int getItemCount() {
        return mData==null?0:mData.size();
    }
    //View item;
    public class HangZhouViewHolder extends RecyclerView.ViewHolder{

        public TextView mTvText;

        public HangZhouViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvText= itemView.findViewById(R.id.tvItem);
        }
    }

}
