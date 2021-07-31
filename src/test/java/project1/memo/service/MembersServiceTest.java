package project1.memo.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project1.memo.domain.Members;
import project1.memo.repository.MembersRepository;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
public class MembersServiceTest {

    @Autowired
    EntityManager em;
    @Autowired
    MembersService membersService;
    @Autowired
    MembersRepository membersRepository;

    @Test
    public void 회원가입() {
        Members members = getMembers("joohyun", "1234");
        Members members1 = getMembers("joohyun", "1233");

        boolean join = membersService.join(members);
        boolean join1 = membersService.join(members1);

        Assertions.assertThat(join).isEqualTo(true);
        Assertions.assertThat(join1).isEqualTo(false);
    }


    @Test
    public void 삭제() {
        Members members = getMembers("joohyun", "1234");
        em.persist(members);
        boolean delete = membersService.delete("joohyun", "1234");
        Assertions.assertThat(delete).isEqualTo(true);
    }

    private Members getMembers(String name, String password) {
        Members members = Members.createMembers(name, password);
        return members;
    }
}