package ra.accademy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ra.accademy.dto.request.FormLogin;
import ra.accademy.model.User;
import ra.accademy.service.IUserService;
import ra.accademy.service.ProductService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class HomeController {
    @Autowired
    private IUserService userService;
    @Autowired
    private ProductService productService ; // ko cần khởi tạo đối tượng nữa
    @Autowired
    private HttpSession httpSession;

    // sử dụng DI để sử dụng bean ta vì trí này
    @RequestMapping
    public String home(HttpSession session) {
        // lưu session : HttpSession -  thuộc HttpServletRequest
//        HttpSession session = request.getSession();
        // thêm 1 thuộc tính vào phiên :  ví dụ lưu thông tin người dung đang đăng nhap , giỏ hàng
        session.setAttribute("arrayInt",new int[]{1,2,3,4,5,6});
        return "admin/user";
    }


    @RequestMapping("/session")
    public String session(HttpSession session)
    {
        // lấy ra mảng
        int[] array = (int[]) session.getAttribute("arrayInt");
        return "admin/user";
    }

    @RequestMapping("/cookies")
    public String cookies(HttpServletResponse response){
        // cookies lưu ở Response
        Cookie cookie1 = new Cookie("username","hunghx");
        cookie1.setMaxAge(24*60*60); // 1 ngày
        Cookie cookie2 = new Cookie("pass","123456");
        response.addCookie(cookie1);
        response.addCookie(cookie2);
        return "admin/user";
    }

    @RequestMapping("/getCookies")
    public String getCookies(@CookieValue("username") String username ){
        // tự động thêm vào request khi gửi lên server
        System.out.println(username);
        return "admin/user";
    }

    @RequestMapping("/login")
    public String login(Model model){
        model.addAttribute("formLogin",new FormLogin());
        return "auth/login";
    }
    @PostMapping("/login")
    public String doLogin(@Valid @ModelAttribute("formLogin") FormLogin formLogin, BindingResult result,Model model, HttpSession session){
        if(result.hasErrors()){
            model.addAttribute("formLogin",formLogin);
            return "auth/login";
        }
        User user = userService.login(formLogin);
        // lưu hông tin vào session
        if (user != null) {
            session.setAttribute("userLogin",user);
            return "home";
        }else {
            // thông tin ko chính xác
            // tài khoản bị khóa
            // kiểm tra quyên xem user hay admin
        }
        return null;
    }
}
