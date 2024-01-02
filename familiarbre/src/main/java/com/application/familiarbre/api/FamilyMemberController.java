package com.application.familiarbre.api;

import com.application.familiarbre.models.entites.FamilyMember;
import com.application.familiarbre.models.entites.Node;
import com.application.familiarbre.models.entites.Token;
import com.application.familiarbre.models.entites.User;
import com.application.familiarbre.models.services.FamilyMemberService;
import com.application.familiarbre.models.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/family-members")
@CrossOrigin(origins = "http://localhost:5173")
public class FamilyMemberController {
    @Autowired
    FamilyMemberService familyMemberService;
    @Autowired
    UserService userService;
    @GetMapping("/all")
    public List<FamilyMember> all(Model model) {
        List<FamilyMember> familyMembers = familyMemberService.getAll();
        model.addAttribute("familyMembers", familyMembers);

        return familyMembers;
    }

    @GetMapping("/tree/{token}/{user_id_target}")
    public List<Node> getTree(@PathVariable String token, @PathVariable Long user_id_target){
        System.out.println(token);
        User user = userService.loadUserByToken(token);
        Long user_id_connected = familyMemberService.getByUser(user).get().getId();
        return familyMemberService.hasAccess(user_id_connected,user_id_target);
    }

    @GetMapping("/id/{id}")
    public FamilyMember byId(@PathVariable Long id) {
        return familyMemberService.getById(id);
    }
}
