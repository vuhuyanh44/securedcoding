package com.lgcns.hrm.cv.controller;

import com.lgcns.hrm.cv.common.domain.Result;
import com.lgcns.hrm.cv.email.MailReceiveTemplate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "Email", description = "Sync E-Mail Management API")
@RestController
@RequestMapping("/sync")
@RequiredArgsConstructor
@PreAuthorize("hasRole('SUPER_ADMIN')")
public class SyncMailController {
//    private final MailReceiveTemplate mailReceiveTemplate;
    @Operation(summary = "Sync unread email then extract content")
    @PostMapping("/unread-email")
    @PostAuthorize("hasAuthority('SYNC_EMAIL:ALL')")
    public Result<String> syncUnreadEmail(@RequestParam(value = "folder", defaultValue = "INBOX") String folder,
                                  @RequestParam(value = "flagFolder", defaultValue = "DOWNLOADED") String flagFolder) {
//        mailReceiveTemplate.receiveUnreadMails(folder, flagFolder);
        return Result.success();
    }
}
