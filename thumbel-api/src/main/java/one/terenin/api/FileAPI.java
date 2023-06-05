package one.terenin.api;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import one.terenin.dto.file.FileRequest;
import one.terenin.dto.file.FileResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Part;
import java.util.UUID;

@RequestMapping("/file")
@CrossOrigin(origins = "*", maxAge = 3600)
public interface FileAPI {


    @ApiOperation(value = "Выгрузка файла", nickname = "upload-file")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Файл выгружен", response = Void.class),
            @ApiResponse(code = 400, message = "Проблемы во время выгрузки")})
    @PostMapping("/upload")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    void upload(@RequestBody FileRequest request, Part part);

    @ApiOperation(value = "Скачивание файла", nickname = "download-file")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Файл сохранён", response = FileResponse.class),
            @ApiResponse(code = 400, message = "Файл не сохранён")})
    @GetMapping("/download/{filename}")
    @ResponseBody
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    byte[] download(@PathVariable("filename") String fileName);

}
