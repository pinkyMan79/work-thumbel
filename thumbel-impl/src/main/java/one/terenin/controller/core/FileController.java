package one.terenin.controller.core;

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

    @io.swagger.annotations.ApiResponses({
            @ApiResponse(code = 200, message = "File updated", response = UUID.class),
            @ApiResponse(code = 400, message = "Error file-upd")})
    @Override
    public void upload(FileRequest request, Part part) {
        fileService.doUpload(request, part);
    }

    @Override
    public byte[] download(String fileName) {
        return fileService.doDownload(fileName);
    }
}
