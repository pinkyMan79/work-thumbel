package one.terenin.service;


import one.terenin.dto.file.FileRequest;

import javax.servlet.http.Part;


public interface FileService {

    void doUpload(FileRequest request, Part file);

    byte[] doDownload(String fileName);

}
