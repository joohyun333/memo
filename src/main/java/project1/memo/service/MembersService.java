package project1.memo.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project1.memo.domain.Members;
import project1.memo.repository.MembersRepository;

import java.util.List;

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

    @Transactional
    public boolean validateMembersAndModifyPassword(String name, String password, String newPassword) {
        List<Members> byName = membersRepository.findByName(name);
        if (!byName.isEmpty() && byName.get(0).getPassword().equals(password)){
            byName.get(0).updatePassword(newPassword);
            return true;
        }
        else {
            return false;
        }
    }

    public boolean newPasswordsCoincidence(String newPassword, String newPassword2){
        return newPassword.equals(newPassword2);
    }
}
