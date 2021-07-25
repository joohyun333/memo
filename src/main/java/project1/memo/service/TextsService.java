package project1.memo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project1.memo.domain.Members;
import project1.memo.domain.Texts;
import project1.memo.repository.MembersRepository;
import project1.memo.repository.TextsRepository;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TextsService {

    private final TextsRepository textsRepository;
    private final MembersRepository membersRepository;


    @Transactional
    public void saveTexts(Members member, String title, String describe){
        Texts text = Texts.createTexts(member, title, describe);
        textsRepository.save(text);
    }
    @Transactional
    public boolean deleteTexts(Long textsId, String name){
        if (name.equals("")){
            return false;
        }
        Long write_member = textsRepository.findOne(textsId).getMembers().getId(); //글을 쓴 놈 id
        Long login_member = membersRepository.findByName(name).get(0).getId(); // 현재 접속한 놈 id
        if (write_member.equals(login_member)){
            textsRepository.delete(textsRepository.findOne(textsId));
            return true;
        }
        else {
            return false;
        }
    }
    @Transactional
    public void updateTexts(Long id, String content){
        Texts text = textsRepository.findOne(id);
        text.setDescribe(content);
    }
}
