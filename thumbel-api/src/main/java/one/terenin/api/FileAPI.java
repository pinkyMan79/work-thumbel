package one.terenin.api;

import one.terenin.dto.file.FileRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Part;

@RequestMapping("/file")
@CrossOrigin(origins = "*", maxAge = 3600)
public interface FileAPI {

    @PostMapping("/upload")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    void upload(@RequestBody FileRequest request, Part part);

    @GetMapping("/download/{filename}")
    @ResponseBody
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    byte[] download(@PathVariable("filename") String fileName);

}
