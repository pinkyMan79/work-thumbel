package one.terenin.dto.special;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ForumUserInfo {

    private String forumTitle;
    private String senderLogin;
    private String count;

}
