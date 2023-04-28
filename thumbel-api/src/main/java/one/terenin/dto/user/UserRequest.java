package one.terenin.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {

    private String login;
    private String password;
    private String repeatPassword;
    private String email;
    private byte photo;
    private String biography;

}
