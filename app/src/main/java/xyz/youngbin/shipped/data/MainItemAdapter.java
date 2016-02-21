package xyz.youngbin.shipped.data;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import xyz.youngbin.shipped.R;

/**
 * Created by youngbin on 16. 2. 21.
 */
public class MainItemAdapter extends BaseAdapter {

    Context mContext;
    String[] mTypeVal;
    String[] mType;
    String[] mName;
    String[] mNum;
    String[] mCarrier;
    String[] mStatus;
    Boolean[] mSelection;

    public MainItemAdapter(Context context, String[] TypeVal,String[] Type,
                           String[] Name, String[] Num, String[] Carrier,
                           String[] Status, Boolean[] Selection){

        mContext = context;
        mTypeVal = TypeVal;
        mType = Type;
        mName = Name;
        mNum = Num;
        mCarrier = Carrier;
        mStatus = Status;
        mSelection = Selection;
    }

    @Override
    public int getCount() {
        return mTypeVal.length;
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
        ImageView imgIcon;
        TextView txtType;
        TextView txtName;
        TextView txtCarrier;
        TextView txtStatus;
        View ItemBg;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater mLayoutInflater =
                (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null){
            convertView = mLayoutInflater.inflate(R.layout.item_main, null);
            holder = new ViewHolder();

            holder.imgIcon = (ImageView)convertView.findViewById(R.id.icon);
            holder.txtType = (TextView)convertView.findViewById(R.id.type);
            holder.txtName = (TextView)convertView.findViewById(R.id.name);
            holder.txtCarrier = (TextView)convertView.findViewById(R.id.carrier);
            holder.txtStatus = (TextView)convertView.findViewById(R.id.status);
            holder.ItemBg = convertView.findViewById(R.id.itembg);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        //Set Icon
        switch (mTypeVal[position]){
            case "mail":
                holder.imgIcon.setImageDrawable(
                        mContext.getResources().getDrawable(R.drawable.ic_type_mail));
                break;
            case "aircargo":
                holder.imgIcon.setImageDrawable(
                        mContext.getResources().getDrawable(R.drawable.ic_type_aircargo));
                break;
        }

        //Set Text
        holder.txtType.setText(mType[position]);
        holder.txtName.setText(mName[position]);
        holder.txtCarrier.setText(mCarrier[position]);
        holder.txtStatus.setText(mStatus[position]);

        //Set Selection Background Color
        if(mSelection[position]==null||!mSelection[position]){
            holder.ItemBg.setBackgroundColor(Color.WHITE);
        }else{
            holder.ItemBg.setBackgroundColor(Color.LTGRAY);
        }

        return convertView;
    }

    public void updateSelection(Boolean[] Selection){
        mSelection = Selection;
        notifyDataSetChanged();
    }
}
