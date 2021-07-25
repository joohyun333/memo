package project1.memo.controller.members;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class MemberJoinForm {

    @NotEmpty(message = "회원 id는 필수로 입력해야 합니다.")
    private String name;
    @NotEmpty(message = "회원 password는 필수로 입력해야 합니다.")
    private String password;
}
