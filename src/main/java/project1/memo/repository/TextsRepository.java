package project1.memo.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project1.memo.controller.texts.TextsForm;
import project1.memo.domain.Members;
import project1.memo.domain.Texts;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TextsRepository {

    private final EntityManager em;

    public void save(Texts texts) {
        em.persist(texts);
    }

    public Texts findOne(Long id) {
        return em.find(Texts.class, id);
    }

    public List<Texts> findAll() {
        return em.createQuery("select t from Texts t", Texts.class).getResultList();
    }

    public List<Texts> findByName(Members members) {
        return em.createQuery("select t from Texts t where t.members = :members", Texts.class)
                .setParameter("members", members)
                .getResultList();
    }

    public void delete(Texts texts) {
        em.remove(texts);
    }
}
