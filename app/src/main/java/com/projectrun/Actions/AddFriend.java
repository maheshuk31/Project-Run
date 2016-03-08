package com.projectrun.Actions;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.projectrun.R;
import com.projectrun.appInterfaces.IUserMessageNetworkManager;
import com.projectrun.MessageService.MessagingService;

/**
 * Created by urben on 3/5/16.
 */
public class AddFriend extends Activity implements View.OnClickListener {

    private static Button mAddFriendButton;
    private static Button mCancelButton;
    private static EditText mFriendUserNameText;

    private static IUserMessageNetworkManager mImService;

    private static final int TYPE_FRIEND_USERNAME = 0;
    private static final String LOG_TAG = "AddFriend";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_friend);
        setTitle(getString(R.string.add_new_friend));

        mAddFriendButton = (Button)findViewById(R.id.addFriend);
        mCancelButton = (Button)findViewById(R.id.cancel);
        mFriendUserNameText = (EditText)findViewById(R.id.newFriendUsername);

        //If someone clicks on the button
        if (mAddFriendButton != null) {
            mAddFriendButton.setOnClickListener(this);
        } else {
            Log.e(LOG_TAG, "onCreate: mAddFriendButton is null");
            throw new NullPointerException("onCreate: mAddFriendButton is null");
        }

        if (mCancelButton != null) {
            mCancelButton.setOnClickListener(this);
        } else {
            Log.e(LOG_TAG, "onCreate: mCancelButton is null");
            throw new NullPointerException("onCreate: mCancelButton is null");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = new Intent(this, MessagingService.class);
        if (mConnection != null) {
            bindService(intent, mConnection , Context.BIND_AUTO_CREATE);
        } else {
            Log.e(LOG_TAG, "onResume: mConnection is null");
        }

    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mConnection != null) {
            unbindService(mConnection);
        } else {
            Log.e(LOG_TAG, "onResume: mConnection is null");
        }
    }

    @Override
    public void onClick(View view) {
        if (view == mCancelButton) {
            finish();
        } else if (view == mAddFriendButton) {
            //This method is defined at the bottom
            addNewFriend();
        } else {
            Log.e(LOG_TAG, "onClick: view clicked is unknown");
        }
    }

    private final ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            mImService = ((MessagingService.IMBinder)service).getService();
        }

        public void onServiceDisconnected(ComponentName className) {
            if (mImService != null) {
                mImService = null;
            }

            Toast.makeText(AddFriend.this, R.string.local_service_stopped, Toast.LENGTH_SHORT).show();
        }
    };

    //  Remove deprecated method
    protected Dialog onCreateDialog(int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddFriend.this);
        if (id == TYPE_FRIEND_USERNAME) {
            builder.setTitle(R.string.add_new_friend)
                    .setMessage(R.string.type_friend_username)
                    .setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            // TODO
                        }
                    });
        }

        return builder.create();
    }

    private void addNewFriend() {
        if (mFriendUserNameText.length() > 0) {
            //  A thread is really needed ?
            Thread thread = new Thread() {
                @Override
                public void run() {
                    //  Please check if the request is successful and raise a error message if needed.
                    mImService.addNewFriendRequest(mFriendUserNameText.getText().toString());
                }
            };
            thread.start();

            //  Show the toast only if the sent of the request is successful
            Toast.makeText(AddFriend.this, R.string.request_sent, Toast.LENGTH_SHORT).show();

            finish();
        } else {
            Log.e(LOG_TAG, "addNewFriend: username length (" + mFriendUserNameText.length() + ") is < 0");
            Toast.makeText(AddFriend.this, R.string.type_friend_username, Toast.LENGTH_LONG).show();
        }
    }
}