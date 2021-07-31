package project1.memo.controller.members;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MemberDeleteForm {
    private String idName;
    private String password;
}
