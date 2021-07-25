package project1.memo.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project1.memo.domain.Members;
import project1.memo.domain.Texts;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
public class TextsServiceTest {

    @Autowired EntityManager em;
    @Autowired TextsService textsService;

    @Test
    public void 게시글삭제(){
        Members member1 = Members.createMembers("member1", "!234");
        Texts texts = Texts.createTexts(member1, "제목1", "내용1");
        em.persist(member1);
        em.persist(texts);
        boolean b = textsService.deleteTexts(texts.getId(), member1.getIdName());
        Assertions.assertThat(b).isEqualTo(true);
    }
    @Test
    public void null일_경우_게시글삭제(){
        Members member1 = Members.createMembers("member1", "!234");
        Texts texts = Texts.createTexts(member1, "제목1", "내용1");
        em.persist(member1);
        em.persist(texts);
        boolean b = textsService.deleteTexts(texts.getId(), "");
        Assertions.assertThat(b).isEqualTo(false);
    }
}