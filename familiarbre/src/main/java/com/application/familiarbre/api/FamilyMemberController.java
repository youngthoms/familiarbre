package com.application.familiarbre.api;

import com.application.familiarbre.models.entites.FamilyMember;
import com.application.familiarbre.models.services.FamilyMemberService;
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

    @GetMapping("/all")
    public List<FamilyMember> all(Model model) {
        List<FamilyMember> familyMembers = familyMemberService.getAll();
        model.addAttribute("familyMembers", familyMembers);

        return familyMembers;
    }

    @GetMapping("/tree/{user_id_connected}/{user_id_target}")
    public List<FamilyMember> getTree(@PathVariable Long user_id_connected,@PathVariable Long user_id_target){
        return familyMemberService.getFamilyTree(user_id_connected,user_id_target);
    }

    @GetMapping("/id/{id}")
    public FamilyMember byId(@PathVariable Long id) {
        return familyMemberService.getById(id);
    }
}
