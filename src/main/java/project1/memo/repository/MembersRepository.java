package project1.memo.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project1.memo.domain.Members;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MembersRepository {

    private final EntityManager em;

    public void save(Members members){
        em.persist(members);
    }

    public Members findMember(Long id){
        Members members = em.find(Members.class, id);
        return members;
    }

    public List<Members> findByName(String name){
        List<Members> member = em.createQuery(
                "select m from Members m where m.idName = :memberName", Members.class)
                .setParameter("memberName", name)
                .getResultList();
        return member;
    }



}
