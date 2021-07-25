package project1.memo.controller.login;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class LoginForm {

    @NotEmpty(message = "로그인시 ID를 필수로 입력하셔야 됩니다.")
    private String login_Name;
    @NotEmpty(message = "로그인시 password를 필수로 입력하셔야 됩니다.")
    private String login_Password;
}
