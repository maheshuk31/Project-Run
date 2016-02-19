package com.example.lee.projectrun;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;





/**
 * Created by urben on 2/15/16.
 */
public class ChatAdapter extends ArrayAdapter<DataSource> {
    private List<DataSource> chatList = new ArrayList<DataSource> ();
    private TextView chatTxt;
    Context ctxt;
    public ChatAdapter(Context context, int resource) {
        super(context, resource);
        ctxt = context;
    }

    @Override
    public void add(DataSource object) {
        chatList.add(object);
        super.add(object);
    }

    @Override
    public int getCount() {
        return chatList.size();
    }

    @Override
    public DataSource getItem(int position) {
        return chatList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       if (convertView == null){
           LayoutInflater inflator = (LayoutInflater) ctxt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
           convertView = inflator.inflate(R.layout.individual_message_layout, parent, false);
       }

        chatTxt = (TextView) convertView.findViewById(R.id.individualMessage);

        String Message;
        boolean POSITION;

        DataSource source = getItem(position);
        Message = source.text;
        POSITION = source.position;
        chatTxt.setText(Message);
        chatTxt.setBackgroundResource(POSITION ? R.drawable.inmessage : R.drawable.outmessage);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        if(!POSITION)
        {
            params.gravity = Gravity.RIGHT;
        }
        else
        {
            params.gravity = Gravity.LEFT;
        }
        chatTxt.setLayoutParams(params);

        return convertView;
    }
}
