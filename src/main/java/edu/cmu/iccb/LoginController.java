package edu.cmu.iccb;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class LoginController {
	
    @RequestMapping(method = RequestMethod.GET, value = "/")
    public String provideUploadInfo(Model model, RedirectAttributes redirectAttributes) {     
        return "login";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public String handleLogin(RedirectAttributes redirectAttributes) {
        return "redirect:/images";
    }

}
