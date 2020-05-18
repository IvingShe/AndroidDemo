package cn.iving.demo.adapter;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cn.iving.demo.androiddemoapp.R;

/**
 * author：Iving
 * date：2020/5/16 10:32
 * description：
 *    测试页面的
 */
public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {
   private static  String  TAG =RecycleAdapter.class.getSimpleName();

    private Context mCotext;


    public List<DataBean> getDateBean() {
        return mDateBean;
    }

    public void setDateBean(List<DataBean> mDateBean) {
        this.mDateBean = mDateBean;
    }

    private List<DataBean> mDateBean;

    public RecycleAdapter(Context mCotext) {
        this.mCotext = mCotext;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View  view =LayoutInflater.from(mCotext).inflate(R.layout.layout_recycleview_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       holder.mBtn.setText(mDateBean.get(position).text);
       holder.mBtn.setTag(mDateBean.get(position).type);
        holder.mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processClickListener((int)v.getTag());
            }
        });
    }



    @Override
    public int getItemCount() {
        return mDateBean==null? 0:mDateBean.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
       Button mBtn;

       public ViewHolder(@NonNull View itemView) {
           super(itemView);
           mBtn  = itemView.findViewById(R.id.btn);
       }
   }

   public static class DataBean{
       public static final int TYPE_TEST=0x00;
       public static final int TYPE_PERMISSIONS=0x01;
       public int type;
       public String text;
   }


    private void processClickListener(int type){
        Log.d(TAG,"#processClickListener#type="+type);
        switch (type){
            case  DataBean.TYPE_TEST:

                break;
            case  DataBean.TYPE_PERMISSIONS:
                testPermissions();
                break;
        }
    }

    private void testPermissions(){
        ComponentName componentName = new ComponentName("com.example.ipcaidlserver","com.example.ipcaidlserver.MainActivity");

        Intent intent = new Intent();
        intent.setComponent(componentName);
        mCotext.startActivity(intent);
    }


}
