package one.terenin.controller;

import lombok.RequiredArgsConstructor;
import one.terenin.api.FileAPI;
import one.terenin.dto.file.FileRequest;
import one.terenin.service.FileService;
import org.springframework.stereotype.Service;

import javax.servlet.http.Part;

@Service
@RequiredArgsConstructor
public class FileController implements FileAPI {

    private final FileService fileService;

    @Override
    public void upload(FileRequest request, Part part) {
        fileService.doUpload(request, part);
    }

    @Override
    public byte[] download(String fileName) {
        return fileService.doDownload(fileName);
    }
}
