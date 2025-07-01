package com.core.dim.test.sftpIntegration;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Vector;

@Service
public class SftpService {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(SftpService.class);



    private void downloadAllFiles(String remoteDir, String localDownloadPath, ChannelSftp sftpChannel) {
        try {
            sftpChannel.cd(remoteDir);
            Vector<ChannelSftp.LsEntry> files = sftpChannel.ls("*.txt");

            for (ChannelSftp.LsEntry entry : files) {
                String fileName = entry.getFilename();
                downloadFile(remoteDir, fileName, localDownloadPath, sftpChannel);
            }

        } catch (Exception e) {
            log.error("Error listing/downloading files from SFTP", e);
        }
    }


    private void downloadFile(String remoteDir, String remoteFileName, String localDownloadPath, ChannelSftp sftpChannel) {
        try (FileOutputStream fos = new FileOutputStream(localDownloadPath + File.separator + remoteFileName)) {
            log.info("Downloading file: {}", remoteFileName);
            sftpChannel.cd(remoteDir);
            sftpChannel.get(remoteFileName, fos);
            log.info("Downloaded file: {} to {}", remoteFileName, localDownloadPath);
        } catch (Exception e) {
            log.error("Error downloading file: {}", remoteFileName, e);
        }
    }


    public void downloadRequest() {
        ChannelSftp sftpChannel = null;
        Session session = null;
        try {
            String username = "";
            String password = "";
            String host = "";
            int port = 22;
            String remoteDir = "/download";
            String localDownloadPath = "C:\\Users\\harshp\\Desktop\\Project-Dim";

            JSch.setLogger(new JSchDebugLogger());
            JSch jsch = new JSch();
            session = jsch.getSession(username, host, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setConfig("PreferredAuthentications", "keyboard-interactive");
            session.setConfig("server_host_key", "ssh-rsa");
            session.setConfig("kex", "diffie-hellman-group14-sha1,diffie-hellman-group-exchange-sha256");
            session.setConfig("cipher.s2c", "aes128-ctr");
            session.setConfig("cipher.c2s", "aes128-ctr");
            session.setTimeout(15000);
            session.connect(15000);

            sftpChannel = (ChannelSftp) session.openChannel("sftp");
            sftpChannel.connect(15000);

            downloadAllFiles(remoteDir, localDownloadPath, sftpChannel);

        } catch (Exception e) {
            log.error("Error initializing SFTP connection", e);
        } finally {
            if (sftpChannel != null && sftpChannel.isConnected()) {
                sftpChannel.disconnect();
                log.info("Disconnected SFTP Channel");
            }
            if (session != null && session.isConnected()) {
                session.disconnect();
                log.info("Disconnected SFTP Session");
            }
        }
    }
}
