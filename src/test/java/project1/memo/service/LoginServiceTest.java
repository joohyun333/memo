package project1.memo.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.transaction.annotation.Transactional;
import project1.memo.domain.Members;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
public class LoginServiceTest {

    @Autowired LoginService loginService;
    @Autowired EntityManager em;

    @Test
    public void 로그인_회원정보_일치(){
        Members mem1 = getMembers("joohyun", "1234");
        boolean login_true = loginService.login(mem1.getIdName(), mem1.getPassword());
        boolean login_false = loginService.login(mem1.getIdName(), "1233");
        Assertions.assertThat(login_true).isEqualTo(true);
        Assertions.assertThat(login_false).isEqualTo(false);
    }
    private Members getMembers(String name, String password) {
        Members members = Members.createMembers(name,password);
        em.persist(members);
        return members;
    }
}