package one.terenin.service.impl;

import lombok.RequiredArgsConstructor;
import one.terenin.dto.file.FileRequest;
import one.terenin.repository.FileRepository;
import one.terenin.service.FileService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileRepository repository;

    @Override
    public void doUpload(FileRequest request) {

    }

    @Override
    public byte[] doDownload(String fileName) {
        return new byte[0];
    }
}
