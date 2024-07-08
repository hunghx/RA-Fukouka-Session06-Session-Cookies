package ra.accademy.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ra.accademy.dto.request.FormAddUser;
import ra.accademy.service.IUserService;

@Controller
@RequestMapping("/admin/users")
public class UserController {
    @Autowired
    private IUserService userService;
    @GetMapping("/list")
    public String list(Model model) {
        // làm sao để truyền dữ liệu về view
        model.addAttribute("users",userService.findAll());
        return "admin/user";
    }

    //ddieeufu huong tới trang add
    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("userForm", new FormAddUser());
        return "admin/user-add";
    }
    // xuwr li them moi
    @PostMapping("/add")
    public String doAdd(@ModelAttribute("userForm") FormAddUser request){
        // gui sang service để xử
        userService.create(request);
        // điều hướng về trang userlist
        return "redirect:/admin/users/list";
    }
}
