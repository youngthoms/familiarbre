package com.application.familiarbre.api;

import com.application.familiarbre.models.entites.FamilyMember;
import com.application.familiarbre.models.entites.Node;
import com.application.familiarbre.models.entites.Token;
import com.application.familiarbre.models.entites.User;
import com.application.familiarbre.models.services.FamilyMemberService;
import com.application.familiarbre.models.services.UserService;
import jakarta.xml.ws.http.HTTPException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


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
    public List<Node> getTree(@PathVariable String token, @PathVariable Long user_id_target) {
        User user = userService.loadUserByToken(token);
        FamilyMember familyMemberUserConnected = familyMemberService.getByUserId(user.getId());
        FamilyMember familyMemberUserTarget = familyMemberService.getByUserId(user_id_target);

        return familyMemberService.hasAccess(familyMemberUserConnected.getId(), familyMemberUserTarget.getId());
    }

    @GetMapping("/id/{id}")
    public FamilyMember byId(@PathVariable Long id) {
        return familyMemberService.getById(id);
    }
}
