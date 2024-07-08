package ra.accademy.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class FormLogin {
    @NotBlank(message = "khong dc de trong")
    private String username;
    @NotBlank(message = "khong dc de trong")
    private String password;
}
