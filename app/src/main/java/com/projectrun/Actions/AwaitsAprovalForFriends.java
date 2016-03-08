package com.projectrun.Actions;

import android.app.ListActivity;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.projectrun.R;
import com.projectrun.appInterfaces.IUserMessageNetworkManager;
import com.projectrun.MessageService.MessagingService;
import com.projectrun.MainComponentsInformation.FriendNetworkInformation;

/**
 * Created by urben on 3/5/16.
 */
public class AwaitsAprovalForFriends extends ListActivity {

    private static final int APPROVE_SELECTED_FRIENDS_ID = 0;
    //	private static final int DISCARD_ID = 1;
    private String[] friendUsernames;
    private IUserMessageNetworkManager imService;
    String approvedFriendNames = new String(); // comma separated
    String discardedFriendNames = new String(); // comma separated

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();

        String names = extras.getString(FriendNetworkInformation.FRIEND_LIST);

        friendUsernames = names.split(",");

        setListAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_multiple_choice, friendUsernames));

        getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        // canceling friend request notification
        NotificationManager NM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NM.cancel(R.string.new_friend_request_exist);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean result = super.onCreateOptionsMenu(menu);

        menu.add(0, APPROVE_SELECTED_FRIENDS_ID, 0, R.string.approve_selected_friends);

        return result;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item)
    {

        switch(item.getItemId())
        {
            case APPROVE_SELECTED_FRIENDS_ID:
            {
                int reqlength = getListAdapter().getCount();

                for (int i = 0; i < reqlength ; i++)
                {
                    if (getListView().isItemChecked(i)) {
                        approvedFriendNames = approvedFriendNames.concat(friendUsernames[i]).concat(",");
                    }
                    else {
                        discardedFriendNames = discardedFriendNames.concat(friendUsernames[i]).concat(",");
                    }
                }
                Thread thread = new Thread(){
                    @Override
                    public void run() {
                        if ( approvedFriendNames.length() > 0 ||
                                discardedFriendNames.length() > 0
                                )
                        {
                            imService.sendFriendsReqsResponse(approvedFriendNames, discardedFriendNames);

                        }
                    }
                };
                thread.start();

                Toast.makeText(AwaitsAprovalForFriends.this, R.string.request_sent, Toast.LENGTH_SHORT).show();

                finish();
                return true;
            }

        }

        return super.onMenuItemSelected(featureId, item);
    }

    @Override
    protected void onPause()
    {
        unbindService(mConnection);
        super.onPause();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        bindService(new Intent(AwaitsAprovalForFriends.this, MessagingService.class), mConnection , Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection mConnection = new ServiceConnection() {

        public void onServiceConnected(ComponentName className, IBinder service) {
            imService = ((MessagingService.IMBinder)service).getService();


        }
        public void onServiceDisconnected(ComponentName className) {
            imService = null;
            Toast.makeText(AwaitsAprovalForFriends.this, R.string.local_service_stopped,
                    Toast.LENGTH_SHORT).show();
        }
    };
}
