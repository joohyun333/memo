package project1.memo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project1.memo.controller.texts.TextsForm;
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
    public void saveTexts(Members member, String title, String describe) {
        Texts text = Texts.createTexts(member, title, describe);
        textsRepository.save(text);
    }

    @Transactional
    public boolean deleteTexts(Long textsId, String name) {
        boolean delete_valid = valid(textsId, name);
        if (delete_valid) {
            textsRepository.delete(textsRepository.findOne(textsId));
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    public void update(Long textsId, TextsForm dto) {
        Texts text = textsRepository.findOne(textsId);
        text.updateInfo(dto);
        textsRepository.save(text);
    }

    /**
     * 글쓴이랑 접속한 놈이랑 일치하는지 검사하는 method
     * true = 일치 false = 불일치
     */
    public boolean valid(Long textId, String name) {
        if (name.equals("")) {
            return false;
        }
        Long write_member = textsRepository.findOne(textId).getMembers().getId(); //글을 쓴 놈 id
        Long login_member = membersRepository.findByName(name).get(0).getId(); // 현재 접속한 놈 id
        if (write_member.equals(login_member)) {
            return true;
        } else {
            return false;
        }
    }
}
