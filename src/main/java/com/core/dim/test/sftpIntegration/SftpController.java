package com.core.dim.test.sftpIntegration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

@RestController
@RequestMapping("/sftp")
public class SftpController {

    private SftpService sftpService;

    @Autowired
    public void setSftpService(SftpService sftpService) {
        this.sftpService = sftpService;
    }

    @GetMapping("/download")
    public ResponseEntity<String> downloadBatch(){
        sftpService.downloadRequest();
        return new ResponseEntity<>("Done", HttpStatus.OK);
    }
}
