package one.terenin.dto.communication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import one.terenin.dto.communication.common.MessageResponse;

import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ForumResponse {

    private UUID id;
    private String title;
    private String description;
    private Set<MessageResponse> messages;

}
