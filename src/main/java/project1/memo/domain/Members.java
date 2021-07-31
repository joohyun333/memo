package project1.memo.domain;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project1.memo.controller.members.MemberModifyForm;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Members {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(name = "member_name", nullable = false)
    private String idName;

    @Column(name = "member_password", nullable = false)
    private String password;

    @OneToMany(mappedBy = "members")
    private List<Texts> memos = new ArrayList<>();

    public static Members createMembers(String name, String password) {
        Members members = new Members();
        members.idName = name;
        members.password = password;
        return members;
    }

    public void updatePassword(String password) {
        this.password = password;
    }
}
