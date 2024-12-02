//package com.lgcns.hrm.cv.email;
//
//import com.lgcns.hrm.cv.common.constants.ErrorCodes;
//import com.lgcns.hrm.cv.common.utils.CollectionUtil;
//import com.lgcns.hrm.cv.common.utils.StringUtil;
//import com.lgcns.hrm.cv.email.extract.ExtractExecutor;
//import com.lgcns.hrm.cv.email.extract.ExtractFactory;
//import com.lgcns.hrm.cv.email.extract.ExtractType;
//import com.lgcns.hrm.cv.exception.EmailException;
//import com.lgcns.hrm.cv.service.CandidateCvService;
//import com.lgcns.hrm.cv.service.CandidatesService;
//import com.lgcns.hrm.cv.service.SysParamService;
//import jakarta.mail.*;
//import jakarta.mail.search.AndTerm;
//import jakarta.mail.search.FlagTerm;
//import jakarta.mail.search.FromStringTerm;
//import jakarta.mail.search.OrTerm;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.concurrent.Executor;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class JavaMailReceiverTemplate implements MailReceiveTemplate {
//    @Value("${spring.mail-imap.box}")
//    private String box;
//    private final Store store;
//    private final CandidatesService candidatesService;
//    private final CandidateCvService candidateCvService;
//    private final SysParamService sysParamService;
//    private final Executor threadPoolTaskExecutor;
//    private static final String DOWNLOADED_MAIL_FOLDER = "DOWNLOADED";
//
//    @Override
//    public void getAllMails(String folderFetch) {
//        log.info("Todo get all mail in folder {}", folderFetch);
//    }
//
//    @Override
//    public void receiveUnreadMails(String folderFetch, String folderAdd) {
//        try {
//            var folders = StringUtil.isBlank(folderFetch) ? Arrays.asList(ExtractType.values()) : Collections.singletonList(ExtractType.getExtractType(folderFetch));
//            var flagFolder = StringUtil.isBlank(folderFetch) ? DOWNLOADED_MAIL_FOLDER : folderAdd;
//            for (var folderName : folders) {
//                threadPoolTaskExecutor.execute(() -> processingInFolder(folderName, flagFolder));
//            }
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//            throw new EmailException(ErrorCodes.SERVER_ERROR);
//        }
//    }
//
//    private void processingInFolder(ExtractType folderName, String flagFolder) {
//        try {
//            var folder = store.getFolder(folderName.getLabel());
//            var extractContent = ExtractFactory.getExtractContent(folderName);
//            folder.open(Folder.READ_WRITE);
//            var messages = fetchMessagesInFolder(folder);
//            Arrays.sort(messages, (m1, m2) -> {
//                try {
//                    return m2.getSentDate().compareTo(m1.getSentDate());
//                } catch (MessagingException e) {
//                    throw new EmailException(ErrorCodes.IO_EXCEPTION);
//                }
//
//            });
//            Arrays.stream(messages).forEach(x -> threadPoolTaskExecutor.execute(new ExtractExecutor(candidateCvService, candidatesService, sysParamService, extractContent, x)));
//            copyMailToDownloadedFolder(store, messages, flagFolder);
//            folder.close(true);
//        } catch (Exception ex) {
//            throw new EmailException(ErrorCodes.SERVER_ERROR);
//        }
//    }
//
//    private void copyMailToDownloadedFolder(Store store, Message[] messages, String folderAdd) throws MessagingException {
//        var downloadedMailFolder = store.getFolder(folderAdd);
//        if (downloadedMailFolder.exists()) {
//            downloadedMailFolder.open(Folder.READ_WRITE);
//            downloadedMailFolder.appendMessages(messages);
//            downloadedMailFolder.close(true);
//        }
//    }
//
//    private Message[] searchMessageInFolder(Folder folder) throws MessagingException {
//        var orTerm = new OrTerm(new FromStringTerm[]{new FromStringTerm(ExtractType.ITVIEC.getLabel()),
//                new FromStringTerm(ExtractType.TOPCV.getLabel()),
//                new FromStringTerm(ExtractType.TOPDEV.getLabel())});
//        var seenTerm = new FlagTerm(new Flags(Flags.Flag.SEEN), false);
//        var andTerm = new AndTerm(orTerm, seenTerm);
//        return folder.search(andTerm);
//    }
//
//    private Message[] fetchMessagesInFolder(Folder folder) throws MessagingException {
//        var contentsProfile = new FetchProfile();
//        var seenTerm = new FlagTerm(new Flags(Flags.Flag.SEEN), false);
//        contentsProfile.add(FetchProfile.Item.ENVELOPE);
//        contentsProfile.add(FetchProfile.Item.CONTENT_INFO);
//        contentsProfile.add(FetchProfile.Item.FLAGS);
//        contentsProfile.add(FetchProfile.Item.SIZE);
//        var messages = folder.search(seenTerm);
//        folder.fetch(messages, contentsProfile);
//        return messages;
//    }
//}
