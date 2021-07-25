package project1.memo.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project1.memo.domain.Members;
import project1.memo.repository.MembersRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MembersService {

    private final MembersRepository membersRepository;

    @Transactional
    public boolean join(Members members) {
        if (validateMembers(members.getIdName())) {
            membersRepository.save(members);
            return true;
        }
        return false;
    }

    public boolean validateMembers(String name) {
        if (membersRepository.findByName(name).isEmpty())
            return true;
        else {
            return false;
        }
    }

    public Members findOne(Long id) {
        return membersRepository.findMember(id);
    }
}
