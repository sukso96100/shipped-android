package xyz.youngbin.shipped.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import xyz.youngbin.shipped.R;

/**
 * Created by youngbin on 16. 2. 19.
 */
public class DetailsAdapter extends BaseAdapter {

    Context mContext;
    String[] mStatus;
    String[] mTime;

    public DetailsAdapter(String[] Status, String[] Time, Context context){
        mStatus = Status;
        mTime = Time;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mTime.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder {
        TextView txtStatus;
        TextView txtTime;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater mLayoutInflater =
                (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null){
            convertView = mLayoutInflater.inflate(R.layout.item_details, null);
            holder = new ViewHolder();
            holder.txtStatus = (TextView) convertView.findViewById(R.id.status);
            holder.txtTime = (TextView) convertView.findViewById(R.id.time);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtStatus.setText(mStatus[position]);
        holder.txtTime.setText(mTime[position]);

        return convertView;
    }
}
