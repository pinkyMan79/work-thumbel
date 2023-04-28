package one.terenin.dto.communication.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageResponse {

    private UUID id;
    private Instant date;
    private String content;
    private UUID forumId;
    private String senderLogin;

}
