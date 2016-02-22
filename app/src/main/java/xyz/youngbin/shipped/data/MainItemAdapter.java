package xyz.youngbin.shipped.data;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.annotation.BoolRes;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;
import xyz.youngbin.shipped.R;

/**
 * Created by youngbin on 16. 2. 21.
 */
public class MainItemAdapter extends RealmBaseAdapter<DataModel> {

    String TAG = this.getClass().getSimpleName();
    ArrayList<String> mSelection;

    public MainItemAdapter(Context context, RealmResults<DataModel> realmResults, boolean automaticUpdate, ArrayList<String> Selection) {
        super(context, realmResults, automaticUpdate);
        this.mSelection = Selection;
    }

    private static class ViewHolder {
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
                (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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
        if(realmResults.get(position).getTypeVal()!=null) {
            switch (realmResults.get(position).getTypeVal()) {
                default:
                    break;
                case "mail":
                    holder.imgIcon.setImageDrawable(
                            context.getResources().getDrawable(R.drawable.ic_type_mail));
                    break;
                case "aircargo":
                    holder.imgIcon.setImageDrawable(
                            context.getResources().getDrawable(R.drawable.ic_type_aircargo));
                    break;
            }
        }else {
            Log.d(TAG, "mTypeVal for pos "+position+" is null");
        }

        //Set Text
        holder.txtType.setText(Util.typeValToTypeString(context, realmResults.get(position).getTypeVal()));
        holder.txtName.setText(realmResults.get(position).getName());
        holder.txtCarrier.setText(Util.carrierValtoCarrierString(context,
                realmResults.get(position).getTypeVal(), realmResults.get(position).getCarrierVal()));

        String statusSummary = Util.getFirstItemOfArrayString(realmResults.get(position).getStatusArray());
        if(statusSummary==null||statusSummary.equals("")){
            holder.txtStatus.setText(context.getResources().getString(R.string.no_status));
        }else {
            holder.txtStatus.setText(statusSummary);
        }

        //Set Selection Background Color
        if(mSelection.contains(String.valueOf(position))){
            holder.ItemBg.setBackgroundColor(Color.LTGRAY);
        }else {
            holder.ItemBg.setBackgroundColor(Color.WHITE);
        }

        return convertView;
    }

    public void updateSelection(ArrayList<String> Selection){
        mSelection = Selection;
        notifyDataSetChanged();
    }
}
