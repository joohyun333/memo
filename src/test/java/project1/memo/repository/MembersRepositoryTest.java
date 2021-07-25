package project1.memo.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project1.memo.domain.Members;

import javax.persistence.EntityManager;
import java.util.List;

@SpringBootTest
@Transactional
public class MembersRepositoryTest {

    @Autowired EntityManager em;
    @Autowired MembersRepository membersRepository;

    @Test
    public void findOneTest(){
        Members members = getMembers();
        Members findMember = membersRepository.findMember(1L);
        Assertions.assertThat(findMember.getIdName()).isEqualTo(members.getIdName());
    }

    @Test
    public void findByNameTest(){
        Members members = getMembers();
        List<Members> byName = membersRepository.findByName(members.getIdName());
        Assertions.assertThat(byName.get(0)).isEqualTo(members);
    }

    private Members getMembers() {
        Members members = Members.createMembers("joohyun", "!234");
        membersRepository.save(members);
        return members;
    }
}