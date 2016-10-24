package rnb.myemotionforme.Events;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import rnb.myemotionforme.R;

/**
 * Created by kimminyoung on 2016-05-05.
 */
public class MyMusic_ListVIewAdapter extends BaseAdapter {
    private Context mContext = null;
    public ArrayList<ListData> mListData = new ArrayList<ListData>();
    ViewHolder holder;

    public MyMusic_ListVIewAdapter(Context mContext) {
        super();
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
            return mListData.size();
    }

    @Override
    public Object getItem(int position) {
        return mListData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void addItem(Drawable icon, String mTitle, String mDate){
        ListData addInfo = null;
        addInfo = new ListData();
        addInfo.mIcon = icon;
        addInfo.mTitle = mTitle;
        addInfo.mDate = mDate;

        mListData.add(addInfo);
    }

    public void remove(int position){
        mListData.remove(position);
        dataChange();
    }

    public void sort(){
        Collections.sort(mListData, ListData.ALPHA_COMPARATOR);
        dataChange();
    }

    public void dataChange(){
        this.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_mymusic, null);
            holder.mIcon = (ImageView) convertView.findViewById(R.id.Mymusic_mImage);
            holder.mText = (TextView) convertView.findViewById(R.id.Mymusic_mText);
            holder.mDate = (TextView) convertView.findViewById(R.id.Mymusic_mDate);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        ListData mData = mListData.get(position);

        if (mData.mIcon != null) {
            holder.mIcon.setVisibility(View.VISIBLE);
            holder.mIcon.setImageDrawable(mData.mIcon);
        }else{
            holder.mIcon.setVisibility(View.GONE);
        }

        holder.mText.setText(mData.mTitle);
        holder.mDate.setText(mData.mDate);

        return convertView;
    }
}

