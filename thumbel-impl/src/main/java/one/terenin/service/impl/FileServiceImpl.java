package one.terenin.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import one.terenin.dto.file.FileRequest;
import one.terenin.entity.FileEntity;
import one.terenin.repository.FileRepository;
import one.terenin.service.FileService;
import one.terenin.service.impl.util.mapper.FileMapper;
import org.springframework.stereotype.Component;

import javax.servlet.http.Part;
import java.io.InputStream;
import java.nio.file.Files;

@Component
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileRepository repository;
    private final FileMapper mapper;

    @SneakyThrows
    @Override
    public void doUpload(FileRequest request, Part file) {
        FileEntity entity = repository.save(mapper.fromRequestToEntity.apply(request));
        byte[] data = null;
        InputStream inputStream = file.getInputStream();
        if (inputStream == null){
            throw new Exception("problem related with input stream");
        }
        data = inputStream.readAllBytes();
        entity.setData(data);
    }

    @Override
    public byte[] doDownload(String fileName) {
        FileEntity fileEntity = repository.findFileEntityByFileName(fileName);
        return fileEntity.getData();
    }
}
