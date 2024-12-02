package com.lgcns.hrm.cv.email;

public interface MailReceiveTemplate {

   void getAllMails(String folderFetch);

    void receiveUnreadMails(String folderFetch, String flagFolder);

}
