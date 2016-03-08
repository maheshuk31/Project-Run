package com.projectrun.Controllers;

import com.projectrun.MainComponentsInformation.MessageInformation;

/**
 * Created by urben on 3/5/16.
 */
public class MessageController
{

    private static MessageInformation[] messagesInfo = null;

    public static void setMessagesInfo(MessageInformation[] messageInfo)
    {
        MessageController.messagesInfo = messageInfo;
    }



    public static MessageInformation checkMessage(String username)
    {
        MessageInformation result = null;
        if (messagesInfo != null)
        {
            for (int i = 0; i < messagesInfo.length;)
            {

                result = messagesInfo[i];
                break;

            }
        }
        return result;
    }





    public static MessageInformation getMessageInfo(String username)
    {
        MessageInformation result = null;
        if (messagesInfo != null)
        {
            for (int i = 0; i < messagesInfo.length;)
            {
                result = messagesInfo[i];
                break;

            }
        }
        return result;
    }






    public static MessageInformation[] getMessagesInfo() {
        return messagesInfo;
    }







}
