package edu.cmu.iccb;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.cmu.iccb.services.ImageService;

@Controller
public class HomeController {

	private ImageService imageService;

	@Autowired
	public void setImageService(ImageService imageService) {
		this.imageService = imageService;
	}

	public ImageService getImageService() {
		return imageService;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/images")
	public String provideUploadInfo(Model model, RedirectAttributes redirectAttributes,
			@CookieValue(value = "oauth", defaultValue = "invalid") String oauthCookie) {
		if (oauthCookie.equals("invalid"))
			return "redirect:/";

		List<String> imageIds = imageService.getUploadedImages();
		model.addAttribute("files", imageIds);
		return "uploadForm";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/images")
	public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes,
			@CookieValue(value = "oauth", defaultValue = "invalid") String oauthCookie) {
		if (oauthCookie.equals("invalid"))
			return "redirect:/";

		String name = file.getOriginalFilename();

		try {
			imageService.saveImage(name, file.getInputStream());
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", name + " failed to upload");
		}

		return "redirect:/images";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/")
	public String loginForm(Model model, RedirectAttributes redirectAttributes) {
		return "login";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/oauth")
	public String postOauth(RedirectAttributes redirectAttributes, HttpServletResponse response) {
		response.addCookie(new Cookie("oauth", "valid"));
		return "redirect:/images";
	}
}
