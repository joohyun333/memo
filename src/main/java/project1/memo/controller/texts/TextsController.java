package project1.memo.controller.texts;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import project1.memo.domain.Members;
import project1.memo.domain.Texts;
import project1.memo.repository.MembersRepository;
import project1.memo.repository.TextsRepository;
import project1.memo.service.TextsService;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class TextsController {

    private final TextsService textsService;
    private final TextsRepository textsRepository;
    private final MembersRepository membersRepository;

    @GetMapping("board/write")
    public String write(Model model) {
        model.addAttribute("textsForm", new TextsForm());
        return "texts/writeForm";
    }

    @PostMapping("board/write")
    public String writePost(@CookieValue(name = "memberId", required = false) String memberid, TextsForm textsForm) {
        Members members = membersRepository.findByName(memberid).get(0);
        textsService.saveTexts(members, textsForm.getTitle(), textsForm.getContent());
        return "redirect:/";
    }

    @GetMapping("board/view/{textId}")
    public String view(Model model, @PathVariable(name = "textId") Long id) {
        Texts one = textsRepository.findOne(id);
        model.addAttribute("one_text", one);
        return "texts/viewForm";
    }

    @PostMapping("board/delete/{textId}")
    public String delete(@PathVariable(name = "textId") Long id,
                         @CookieValue(name = "memberId", required = false, defaultValue = "") String name,
                         Model model) {
        boolean delete_valid = textsService.deleteTexts(id, name);
        if (delete_valid) {
            model.addAttribute("msg", "DELETE");//삭제한다는 알림날리기
            return "texts/alertsRedirect";
        } else {
            model.addAttribute("msg", "Error");//삭제 못한다는 알림날리기
            return "texts/alertsRedirect";
        }
    }

    @GetMapping("board/edit/{textsId}")
    public String update(@PathVariable(name = "textsId") Long id,
                         @CookieValue(name = "memberId", required = false, defaultValue = "") String name
            , Model model) {
        boolean valid = textsService.valid(id, name);
        if (valid) {
            Texts text = textsRepository.findOne(id);
            TextsForm textsForm = TextsForm.builder().title(text.getTitle()).content(text.getDescribe()).build();
            model.addAttribute("textId", id);
            model.addAttribute("textForm", textsForm);
            return "texts/modifyForm";
        } else {
            model.addAttribute("msg", "Error");
            return "texts/alertsRedirect";
        }
    }

    @PostMapping("board/edit/{textsId}")
    public String update(@PathVariable(name = "textsId") Long id,
                         Model model,
                         TextsForm textForm) {
        textsService.update(id, textForm);
        model.addAttribute("msg", "MODIFY");
        return "texts/alertsRedirect";
    }

    @GetMapping("board/my-memos")
    public String personalTexts(@CookieValue(name = "memberId", required = false, defaultValue = "") String name, Model model) {
        if (name.isEmpty()){
            model.addAttribute("msg","Error");
            return "texts/alertsRedirect";
        }
        else {
            List<Texts> memos = membersRepository.findByName(name).get(0).getMemos();
            model.addAttribute("memos", memos);
            return "texts/personalForm";
        }
    }
}