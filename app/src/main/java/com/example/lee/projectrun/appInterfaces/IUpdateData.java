package com.example.lee.projectrun.appInterfaces;

import com.example.lee.projectrun.MainComponentsInformation.FriendNetworkInformation;
import com.example.lee.projectrun.MainComponentsInformation.MessageInformation;

/**
 * Created by urben on 3/5/16.
 */
public interface IUpdateData {
    public void updateData(MessageInformation[] messages, FriendNetworkInformation[] friends, FriendNetworkInformation[] unApprovedFriends, String userKey);

}
