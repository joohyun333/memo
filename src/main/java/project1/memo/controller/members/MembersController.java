package project1.memo.controller.members;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import project1.memo.domain.Members;
import project1.memo.service.MembersService;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class MembersController {

    private final MembersService membersService;

    @GetMapping("members/join")
    public String joinForm(Model model){
        model.addAttribute("memberJoinForm", new MemberJoinForm());
        return "members/joinForm";
    }

    @PostMapping("members/join")
    public String join(@Valid MemberJoinForm memberJoinForm, BindingResult result, Model model){
        if (result.hasErrors()){
            return "members/joinForm";
        }
        boolean join_bool = membersService.join(Members.createMembers(memberJoinForm.getName(), memberJoinForm.getPassword()));
        if (!join_bool){
            model.addAttribute("already", "해당 ID는 이미 존재합니다.");
            return "members/joinForm";
        }
        return "redirect:/";
    }
}
