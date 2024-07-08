package ra.accademy.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class User {
    private Integer id; // lớp bao bọc  : wrapper class / thay vì sử dụng nguyên thủy
    private String username, password, fullName, email, phone, address;
    private Boolean active, role;
}
