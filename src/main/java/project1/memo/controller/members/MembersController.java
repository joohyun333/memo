package project1.memo.controller.members;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import project1.memo.domain.Members;
import project1.memo.repository.MembersRepository;
import project1.memo.service.MembersService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class MembersController {

    private final MembersService membersService;
    private final MembersRepository membersRepository;

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

    //회원 관리
    @GetMapping("members/memberInfo")
    public String memberInfo(){
        return "members/memberInfo";
    }

    //비번 수정
    @GetMapping("members/modifyInfo")
    public String modifyInfoForm(@CookieValue(name = "memberId", required = false) String memberId, Model model){
        if(memberId == null){
            return "redirect:/";
        }
        Members member = membersRepository.findByName(memberId).get(0);
        MemberModifyForm build = MemberModifyForm.builder().name(member.getIdName()).build();
        model.addAttribute("Info", build);
        return "members/modifyForm";
    }

    @PostMapping("members/modifyInfo")
    public String modifyInfo(@CookieValue(name = "memberId", required = false) String memberId,
                             MemberModifyForm memberModifyForm, Model model){
        //새비밀번호와 새비밀번호가 일치하지 않을 때
        if(!membersService.newPasswordsCoincidence(memberModifyForm.getNew_password(),
                memberModifyForm.getNew_password_valid())){
            model.addAttribute("msg", "notNewPassCoincidence");
            return "members/alertsRedirect";
        }
        else {
            //기존의 비번이랑 아이디가 일치하는지 여부(true 일치, false 불일치)
            boolean updateInfo = membersService.validateMembersAndModifyPassword(memberId,
                    memberModifyForm.getPassword(),
                    memberModifyForm.getNew_password());
            if (updateInfo) {
                model.addAttribute("msg", "MODIFY");
                return "members/alertsRedirect";
            }
            else {
                model.addAttribute("msg", "accNotCoincidence");
                return "members/alertsRedirect";
            }
        }
    }

    //회원탈퇴
    @GetMapping("members/withdraw")
    public String memberWithdrawForm(){
        return "members/withdrawForm";
    }

    @PostMapping("members/withdraw")
    public String memberWithdraw(@CookieValue(name = "memberId", required = false, defaultValue = "") String idName,
                                 Model model, HttpServletResponse response,
                                 String password){
        boolean delete = membersService.delete(idName, password);
        if(delete){
            model.addAttribute("msg","DELETE");
            Cookie cookie = new Cookie("memberId", null);
            cookie.setMaxAge(0);
            response.addCookie(cookie);
            return "members/alertsRedirect";
        }
        else {
            model.addAttribute("msg", "accNotCoincidenceInDelete");
            return "members/alertsRedirect";
        }
    }
}
