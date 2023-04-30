package one.terenin.dto.communication.common;

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
public class MessageRequest {

    @NotBlank
    private String content;
    @NotBlank
    private UUID forumId;
    @NotBlank
    private String senderLogin;

}
