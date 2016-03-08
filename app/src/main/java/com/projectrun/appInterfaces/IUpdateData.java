package com.projectrun.appInterfaces;

import com.projectrun.MainComponentsInformation.FriendNetworkInformation;
import com.projectrun.MainComponentsInformation.MessageInformation;

/**
 * Created by urben on 3/5/16.
 */
public interface IUpdateData {
    public void updateData(MessageInformation[] messages, FriendNetworkInformation[] friends, FriendNetworkInformation[] unApprovedFriends, String userKey);

}
