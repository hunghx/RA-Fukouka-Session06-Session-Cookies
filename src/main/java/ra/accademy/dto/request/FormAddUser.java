package ra.accademy.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormAddUser {
    private String username;
    private String password;
    private String fullName;
    private String email;
    private String phone;
    private String address;
}
