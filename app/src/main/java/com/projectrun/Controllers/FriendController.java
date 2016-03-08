package com.projectrun.Controllers;

import com.projectrun.MainComponentsInformation.FriendNetworkInformation;

/**
 * Created by urben on 3/5/16.
 */
public class FriendController
{

    private static FriendNetworkInformation[] friendsInfo = null;
    private static FriendNetworkInformation[] unapprovedFriendsInfo = null;
    private static String activeFriend;

    public static void setFriendsInfo(FriendNetworkInformation[] friendInfo)
    {
        FriendController.friendsInfo = friendInfo;
    }



    public static FriendNetworkInformation checkFriend(String username, String userKey)
    {
        FriendNetworkInformation result = null;
        if (friendsInfo != null)
        {
            for (int i = 0; i < friendsInfo.length; i++)
            {
                if ( friendsInfo[i].userName.equals(username) &&
                        friendsInfo[i].userKey.equals(userKey)
                        )
                {
                    result = friendsInfo[i];
                    break;
                }
            }
        }
        return result;
    }

    public static void setActiveFriend(String friendName){
        activeFriend = friendName;
    }

    public static String getActiveFriend()
    {
        return activeFriend;
    }



    public static FriendNetworkInformation getFriendInfo(String username)
    {
        FriendNetworkInformation result = null;
        if (friendsInfo != null)
        {
            for (int i = 0; i < friendsInfo.length; i++)
            {
                if ( friendsInfo[i].userName.equals(username) )
                {
                    result = friendsInfo[i];
                    break;
                }
            }
        }
        return result;
    }



    public static void setUnapprovedFriendsInfo(FriendNetworkInformation[] unapprovedFriends) {
        unapprovedFriendsInfo = unapprovedFriends;
    }



    public static FriendNetworkInformation[] getFriendsInfo() {
        return friendsInfo;
    }



    public static FriendNetworkInformation[] getUnapprovedFriendsInfo() {
        return unapprovedFriendsInfo;
    }




}
