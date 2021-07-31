package project1.memo.controller.members;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class MemberModifyForm {
    private String name;
    private String password;
    private String new_password;
    private String new_password_valid;
}
