package project1.memo.controller.login;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import project1.memo.domain.Members;
import project1.memo.domain.Texts;
import project1.memo.repository.MembersRepository;
import project1.memo.repository.TextsRepository;
import project1.memo.service.LoginService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final MembersRepository membersRepository;
    private final TextsRepository textsRepository;

    @GetMapping("/login")
    public String loginMember(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "login/loginForm";
    }

    @PostMapping("/login")
    public String login(@Valid LoginForm loginForm, BindingResult result, Model model, HttpServletResponse httpServletResponse) {
        if (result.hasErrors()) {
            return "login/loginForm";
        }
        boolean login = loginService.login(loginForm.getLogin_Name(), loginForm.getLogin_Password());
        if (login) {
            Cookie idCookie = new Cookie("memberId", loginForm.getLogin_Name());
            httpServletResponse.addCookie(idCookie);
            return "redirect:/";
        } else {
            model.addAttribute("disagreement", "해당 아이디와 비밀번호가 일치하지 않습니다.");
            return "login/loginForm";
        }
    }

    @GetMapping("/")
    public String cookieLogin(@CookieValue(name = "memberId", required = false) String memberid, Model model){
        List<Texts> all_text = textsRepository.findAll();
        model.addAttribute("texts", all_text);
        if (memberid == null){
            return "home/main";
        }
        List<Members> byName = membersRepository.findByName(memberid);
        if (byName.isEmpty()){
            return "home/main";
        }
        model.addAttribute("memberInfo",byName.get(0));
        return "home/cookieMain";
    }
    @PostMapping("/logout")
    public String logout(HttpServletResponse response){
        Cookie cookie = new Cookie("memberId", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }

}
