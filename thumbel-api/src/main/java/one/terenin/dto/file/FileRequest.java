package one.terenin.dto.file;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileRequest {

    private String fileName;
    private String fileLocation;
    private byte data;
    private UUID maintainer;

}
