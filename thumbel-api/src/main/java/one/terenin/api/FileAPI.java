package one.terenin.api;

import one.terenin.dto.file.FileRequest;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/file")
public interface FileAPI {

    @PostMapping("/upload")
    void upload(@RequestBody FileRequest request);

    @GetMapping("/download/{filename}")
    @ResponseBody
    byte[] download(@PathVariable("filename") String fileName);

}
