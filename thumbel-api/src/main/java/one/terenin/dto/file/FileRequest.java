package one.terenin.dto.file;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileRequest {

    @NotBlank
    private String fileName;
    @NotBlank
    private String fileLocation;
    private byte[] data;
    @NotBlank
    private UUID maintainer;

}
