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
public class LoginService {

    private final MembersRepository membersRepository;

    public boolean login(String idNamne, String password){
        List<Members> byName = membersRepository.findByName(idNamne);
        if (byName.size()==1 && byName.get(0).getPassword().equals(password)){
                return true;
        }
        return false;
    }
}
