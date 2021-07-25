package project1.memo.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project1.memo.domain.Members;
import project1.memo.domain.Texts;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.fail;

@SpringBootTest
@Transactional
public class TextsRepositoryTest {

    @Autowired EntityManager em;
    @Autowired TextsRepository textsRepository;
    @Autowired MembersRepository membersRepository;

//    @Test
//    public void Texts_하나찾기(){
//        Texts texts1 = createTexts("Member1");
//    }

    @Test
    public void Texts_모두_찾기(){
        Texts texts1 = createTexts("Member1");
        List<Texts> all = textsRepository.findAll();
        Assertions.assertThat(all).isEqualTo(texts1.getMembers().getMemos());
    }

    @Test
    public void Texts_모두_찾기_애러1() throws Exception{
        Texts texts1 = createTexts("Member1");
        Texts texts2 = createTexts("Member2");
        List<Texts> all = textsRepository.findAll();
        ArrayList<Texts> objects = new ArrayList<>();
        objects.addAll(texts1.getMembers().getMemos());
        objects.addAll(texts2.getMembers().getMemos());
        Assertions.assertThat(all).isEqualTo(objects);
    }

    @Test
    public void Texts_이름으로_찾기(){
        Texts texts1 = createTexts("Member1");
        List<Texts> byName = textsRepository.findByName(texts1.getMembers());
        List<Texts> objects = new ArrayList<>();
        objects.addAll(texts1.getMembers().getMemos());
        Assertions.assertThat(byName).isEqualTo(objects);
    }

    private Texts createTexts(String member) {
        Members member1 = Members.createMembers(member, "1234");
        Texts texts1 = Texts.createTexts(member1, "제목1", "내용1");
        Texts texts2 = Texts.createTexts(member1, "제목2", "내용2");
        em.persist(member1);
        em.persist(texts1);
        em.persist(texts2);
        return texts1;
    }

}