package one.terenin.service;


import one.terenin.dto.file.FileRequest;

public interface FileService {

    void doUpload(FileRequest request);

    byte[] doDownload(String fileName);

}
