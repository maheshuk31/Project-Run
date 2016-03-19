package com.example.lee.projectrun.Actions;

import android.app.ListActivity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lee.projectrun.Controllers.FriendController;
import com.example.lee.projectrun.MainComponentsInformation.FriendNetworkInformation;
import com.example.lee.projectrun.MainComponentsInformation.UserNetworkInformation;
import com.example.lee.projectrun.MessageService.MessagingService;
import com.example.lee.projectrun.R;
import com.example.lee.projectrun.UserInformation;
import com.example.lee.projectrun.appInterfaces.IUserMessageNetworkManager;

/**
 * Created by urben on 3/5/16.
 */
public class ListFriend extends ListActivity
{
    private static final int ADD_NEW_FRIEND_ID = Menu.FIRST;
    private static final int EXIT_APP_ID = Menu.FIRST + 1;
    private IUserMessageNetworkManager imService = null;
    private FriendListAdapter friendAdapter;
    private UserInformation userInformation;
    private Button addFriend;

    public String ownusername = new String();

    private class FriendListAdapter extends BaseAdapter
    {
        class ViewHolder {
            TextView text;
            ImageView icon;
        }
        private LayoutInflater mInflater;
        private Bitmap mOnlineIcon;
        private Bitmap mOfflineIcon;

        private FriendNetworkInformation[] friends = null;


        public FriendListAdapter(Context context) {
            super();

            mInflater = LayoutInflater.from(context);

            mOnlineIcon = BitmapFactory.decodeResource(context.getResources(), R.mipmap.online);
            mOfflineIcon = BitmapFactory.decodeResource(context.getResources(), R.mipmap.offline);

        }

        public void setFriendList(FriendNetworkInformation[] friends)
        {
            this.friends = friends;
        }


        public int getCount() {

            return friends.length;
        }


        public FriendNetworkInformation getItem(int position) {

            return friends[position];
        }

        public long getItemId(int position) {

            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            // A ViewHolder keeps references to children views to avoid unneccessary calls
            // to findViewById() on each row.
            ViewHolder holder;

            // When convertView is not null, we can reuse it directly, there is no need
            // to reinflate it. We only inflate a new View when the convertView supplied
            // by ListView is null.
            if (convertView == null)
            {
                convertView = mInflater.inflate(R.layout.friend_list_screen, null);

                // Creates a ViewHolder and store references to the two children views
                // we want to bind data to.
                holder = new ViewHolder();
                holder.text = (TextView) convertView.findViewById(R.id.text);
                holder.icon = (ImageView) convertView.findViewById(R.id.icon);

                convertView.setTag(holder);
            }
            else {
                // Get the ViewHolder back to get fast access to the TextView
                // and the ImageView.
                holder = (ViewHolder) convertView.getTag();
            }

            // Bind the data efficiently with the holder.
            holder.text.setText(friends[position].userName);
            holder.icon.setImageBitmap(friends[position].status == UserNetworkInformation.ONLINE ? mOnlineIcon : mOfflineIcon);

            return convertView;
        }

    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            Log.i("Broadcast receiver ", "received a message");
            Bundle extra = intent.getExtras();
            if (extra != null)
            {
                String action = intent.getAction();
                if (action.equals(MessagingService.FRIEND_LIST_UPDATED))
                {
                    // taking friend List from broadcast
                    //String rawFriendList = extra.getString(FriendInfo.FRIEND_LIST);
                    //FriendList.this.parseFriendInfo(rawFriendList);
                    ListFriend.this.updateData(FriendController.getFriendsInfo(),
                            FriendController.getUnapprovedFriendsInfo());

                }
            }
        }

    };
    public MessageReceiver messageReceiver = new MessageReceiver();

    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            imService = ((MessagingService.IMBinder)service).getService();

            FriendNetworkInformation[] friends = FriendController.getFriendsInfo(); //imService.getLastRawFriendList();
            if (friends != null) {
                ListFriend.this.updateData(friends, null); // parseFriendInfo(friendList);
            }

            setTitle(imService.getUsername() + "'s friend list");
            ownusername = imService.getUsername();
        }
        public void onServiceDisconnected(ComponentName className) {
            imService = null;
            Toast.makeText(ListFriend.this, R.string.local_service_stopped,
                    Toast.LENGTH_SHORT).show();
        }
    };



    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.list_friends);
        addFriend = (Button) findViewById(R.id.addfriend);
        Intent intent = getIntent();
        userInformation = (UserInformation)intent.getSerializableExtra("userinfo");


        friendAdapter = new FriendListAdapter(this);


        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddFriend.class);
                intent.putExtra("userinfo", userInformation);
                startActivity(intent);
            }
        });


    }
    public void updateData(FriendNetworkInformation[] friends, FriendNetworkInformation[] unApprovedFriends)
    {
        if (friends != null) {
            friendAdapter.setFriendList(friends);
            setListAdapter(friendAdapter);
        }

        //If there is a list of unapproved friends
        if (unApprovedFriends != null)
        {
            NotificationManager NM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            if (unApprovedFriends.length > 0)
            {
                String tmp = new String();
                for (int j = 0; j < unApprovedFriends.length; j++) {
                    tmp = tmp.concat(unApprovedFriends[j].userName).concat(",");
                }
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.notification)
                        .setContentTitle(getText(R.string.new_friend_request_exist))
                        .setPriority(NotificationCompat.PRIORITY_HIGH);
//                Notification notification = new Notification(R.drawable.notification,
//                        getText(R.string.new_friend_request_exist),
//                        System.currentTimeMillis());

                Intent i = new Intent(this, AwaitsAprovalForFriends.class);
                i.putExtra(FriendNetworkInformation.FRIEND_LIST, tmp);

                PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                        i, 0);

                mBuilder.setContentText("You have new friend request(s)");
//                notification.setLatestEventInfo(this, getText(R.string.new_friend_request_exist),
//                        "You have new friend request(s)",
//                        contentIntent);

                mBuilder.setContentIntent(contentIntent);


                NM.notify(R.string.new_friend_request_exist, mBuilder.build());
//                NM.notify(R.string.new_friend_request_exist, notification);
            }
            else
            {
                // if any request exists, then cancel it
                NM.cancel(R.string.new_friend_request_exist);
            }
        }

    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        super.onListItemClick(l, v, position, id);

        Intent i = new Intent(this, ChatMessage.class);
        FriendNetworkInformation friend = friendAdapter.getItem(position);
        i.putExtra(FriendNetworkInformation.USERNAME, friend.userName);
        i.putExtra(FriendNetworkInformation.PORT, friend.port);
        i.putExtra(FriendNetworkInformation.IP, friend.ip);
        startActivity(i);
    }




    @Override
    protected void onPause()
    {
        unregisterReceiver(messageReceiver);
        unbindService(mConnection);
        super.onPause();
    }

    @Override
    protected void onResume()
    {

        super.onResume();
        bindService(new Intent(ListFriend.this, MessagingService.class), mConnection , Context.BIND_AUTO_CREATE);

        IntentFilter i = new IntentFilter();
        //i.addAction(IMService.TAKE_MESSAGE);
        i.addAction(MessagingService.FRIEND_LIST_UPDATED);

        registerReceiver(messageReceiver, i);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean result = super.onCreateOptionsMenu(menu);

        menu.add(0, ADD_NEW_FRIEND_ID, 0, R.string.add_new_friend);

        menu.add(0, EXIT_APP_ID, 0, R.string.exit_application);

        return result;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item)
    {

        switch(item.getItemId())
        {
            case ADD_NEW_FRIEND_ID:
            {
                Intent i = new Intent(ListFriend.this, AddFriend.class);
                startActivity(i);
                return true;
            }
            case EXIT_APP_ID:
            {
                imService.exit();
                finish();
                return true;
            }
        }

        return super.onMenuItemSelected(featureId, item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);




    }
}

