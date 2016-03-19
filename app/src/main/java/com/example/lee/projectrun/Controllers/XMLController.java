package com.example.lee.projectrun.Controllers;

import android.util.Log;

import com.example.lee.projectrun.appInterfaces.IUpdateData;
import com.example.lee.projectrun.MainComponentsInformation.FriendNetworkInformation;
import com.example.lee.projectrun.MainComponentsInformation.MessageInformation;
import com.example.lee.projectrun.MainComponentsInformation.UserNetworkInformation;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.Vector;

/**
 * Created by urben on 3/5/16.
 */
public class XMLController extends DefaultHandler
{
    private String userKey = new String();
    private IUpdateData update_data;

    public XMLController(IUpdateData update_data) {
        super();
        this.update_data = update_data;
    }

    private Vector<FriendNetworkInformation> mFriends = new Vector<FriendNetworkInformation>();
    private Vector<FriendNetworkInformation> mOnlineFriends = new Vector<FriendNetworkInformation>();
    private Vector<FriendNetworkInformation> mUnapprovedFriends = new Vector<FriendNetworkInformation>();

    private Vector<MessageInformation> mUnreadMessages = new Vector<MessageInformation>();


    public void endDocument() throws SAXException
    {
        FriendNetworkInformation[] friends = new FriendNetworkInformation[mFriends.size() + mOnlineFriends.size()];
        MessageInformation[] messages = new MessageInformation[mUnreadMessages.size()];

        int onlineFriendCount = mOnlineFriends.size();
        for (int i = 0; i < onlineFriendCount; i++)
        {
            friends[i] = mOnlineFriends.get(i);
        }


        int offlineFriendCount = mFriends.size();
        for (int i = 0; i < offlineFriendCount; i++)
        {
            friends[i + onlineFriendCount] = mFriends.get(i);
        }

        int unApprovedFriendCount = mUnapprovedFriends.size();
        FriendNetworkInformation[] unApprovedFriends = new FriendNetworkInformation[unApprovedFriendCount];

        for (int i = 0; i < unApprovedFriends.length; i++) {
            unApprovedFriends[i] = mUnapprovedFriends.get(i);
        }

        int unreadMessagecount = mUnreadMessages.size();
        //Log.i("MessageLOG", "mUnreadMessages="+unreadMessagecount );
        for (int i = 0; i < unreadMessagecount; i++)
        {
            messages[i] = mUnreadMessages.get(i);
            Log.i("MessageLOG", "i=" + i);
        }

        this.update_data.updateData(messages, friends, unApprovedFriends, userKey);
        super.endDocument();
    }

    public void startElement(String uri, String localName, String name,
                             Attributes attributes) throws SAXException
    {
        if (localName == "friend")
        {
            FriendNetworkInformation friend = new FriendNetworkInformation();
            friend.userName = attributes.getValue(FriendNetworkInformation.USERNAME);
            String status = attributes.getValue(FriendNetworkInformation.STATUS);
            friend.ip = attributes.getValue(FriendNetworkInformation.IP);
            friend.port = attributes.getValue(FriendNetworkInformation.PORT);
            friend.userKey = attributes.getValue(FriendNetworkInformation.USER_KEY);
            //friend.expire = attributes.getValue("expire");

            if (status != null && status.equals("online"))
            {
                friend.status = UserNetworkInformation.ONLINE;
                mOnlineFriends.add(friend);
            }
            else if (status.equals("unApproved"))
            {
                friend.status = UserNetworkInformation.UNAPPROVED;
                mUnapprovedFriends.add(friend);
            }
            else
            {
                friend.status = UserNetworkInformation.OFFLINE;
                mFriends.add(friend);
            }
        }
        else if (localName == "user") {
            this.userKey = attributes.getValue(FriendNetworkInformation.USER_KEY);
        }
        else if (localName == "message") {
            MessageInformation message = new MessageInformation();
            message.userid = attributes.getValue(MessageInformation.USERID);
            message.sendt = attributes.getValue(MessageInformation.SENDT);
            message.messagetext = attributes.getValue(MessageInformation.MESSAGETEXT);
            Log.i("MessageLOG", message.userid + message.sendt + message.messagetext);
            mUnreadMessages.add(message);
        }
        super.startElement(uri, localName, name, attributes);
    }

    @Override
    public void startDocument() throws SAXException {
        this.mFriends.clear();
        this.mOnlineFriends.clear();
        this.mUnreadMessages.clear();
        super.startDocument();
    }


}

