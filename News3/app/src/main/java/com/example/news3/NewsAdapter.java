package com.example.news3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

/** 这里是RecyclerVieww适配器的标准写法，也即必须重写以下三个方法
 *  这里我们自定义一个适配器继承自RecyclerView.Adapter, 并且我们需要将泛型指定为NewsAdapter.ViewHolder
 *  其中ViewHolder是NewsAdapter里面的一个内部类
 */
public class NewsAdapter extends BaseAdapter {

    private List<News> newsList;

    @Override
    public int getCount() {
       return newsList.size();
    }

    @Override
    public Object getItem(int position) {
        return newsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder=null;
        if(convertView==null)
        {
            viewHolder=new ViewHolder();
            convertView=View.inflate(parent.getContext(),R.layout.news_item,null);
            viewHolder.newsImage = convertView.findViewById(R.id.news_image);
            viewHolder.newsIntroduction = convertView.findViewById(R.id.news_introduction);
            viewHolder.description=convertView.findViewById(R.id.description);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder=(ViewHolder)convertView.getTag();
        }
//        viewHolder.newsImage.setImageResource(newsList.get(position).getImageId());
        Glide.with(parent.getContext()).load(newsList.get(position).getPicUrl()).into(viewHolder.newsImage);
        viewHolder.newsIntroduction.setText(newsList.get(position).getTitle());
        String str=newsList.get(position).getDescription()+"  "+newsList.get(position).getTime();
        viewHolder.description.setText(str);
        return convertView;
    }

    static class  ViewHolder  {
        ImageView newsImage;
        TextView newsIntroduction;
        TextView description;
    }

    // 构造函数，用于传入数据源
    public NewsAdapter(List<News> newsList) {
        this.newsList = newsList;
    }

}

