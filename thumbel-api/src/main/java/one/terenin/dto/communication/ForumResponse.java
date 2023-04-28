package one.terenin.dto.communication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Set<UUID> messages;

}
