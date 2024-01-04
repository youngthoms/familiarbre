package com.application.familiarbre.api;

import com.application.familiarbre.models.entites.*;
import com.application.familiarbre.models.services.FamilyMemberService;
import com.application.familiarbre.models.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/privacy/{token}/{level}")
    public ResponseEntity<ApiResponse> setPrivacyLevel(@PathVariable String token, @PathVariable String level) {
        User user = userService.loadUserByToken(token);
        FamilyMember familyMember = familyMemberService.getByUserId(user.getId());
        Status privacyLevel = Status.fromString(level);
        familyMemberService.setStatus(familyMember, privacyLevel);

        return ResponseEntity.ok(ApiResponse.builder().response("OK").build());
    }

    @GetMapping("/add/child/{parent_id}/{child_id}")
    public ResponseEntity<ApiResponse> addNode(@PathVariable Long parent_id, @PathVariable Long child_id) {
        FamilyMember parent = familyMemberService.getById(parent_id);
        FamilyMember child = familyMemberService.getById(child_id);

        familyMemberService.addChild(parent, child);

        return ResponseEntity.ok(ApiResponse.builder().response("OK").build());
    }
}
