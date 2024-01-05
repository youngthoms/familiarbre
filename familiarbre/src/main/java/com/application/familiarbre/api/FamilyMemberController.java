package com.application.familiarbre.api;

import com.application.familiarbre.models.entites.*;
import com.application.familiarbre.models.services.FamilyMemberService;
import com.application.familiarbre.models.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

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
    @PostMapping("/update/")
    public ResponseEntity<ApiResponse>update(@RequestBody UpdateRequest updateRequest){
        System.out.println("acceder api");
        String securitySocialNumber = updateRequest.getSocialSecurityNumber();
        FamilyMember member = familyMemberService.getBySocialSecurityNumber(securitySocialNumber);

        familyMemberService.update(member,updateRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.builder()
                        .response("Update successfully.")
                        .build());
    }
    @PostMapping("/spouse/add")
    public ResponseEntity<ApiResponse> addSpouse(@RequestBody AddSpouseRequest addSpouseRequest){
        System.out.println('e');
        FamilyMember member1 = familyMemberService.getById(addSpouseRequest.getMember1());
        FamilyMember member2 = familyMemberService.getById(addSpouseRequest.getMember2());

        if (member1 ==null || member2 == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.builder()
                            .response("Spouse not found")
                            .build());
        }
        else {
            try {
                familyMemberService.addSpouse(member1, member2);
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(ApiResponse.builder()
                                .response("Spouses successfully.")
                                .build());
            } catch (IllegalStateException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.builder()
                                .response(e.getMessage())
                                .build());
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ApiResponse.builder()
                                .response("An error occurred while adding the spouses.")
                                .build());
            }
        }
    }

    @PostMapping("/child/add")
    public ResponseEntity<ApiResponse> addChild(@RequestBody AddChildRequest addChildRequest) {
        FamilyMember parent = familyMemberService.getById(addChildRequest.getParentId());
        FamilyMember child = familyMemberService.getById(addChildRequest.getChildId());

        if (parent == null || child == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.builder()
                            .response("Parent or child not found.")
                            .build());
        }

        try {
            familyMemberService.addChild(parent, child);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.builder()
                            .response("Child added to parent successfully.")
                            .build());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.builder()
                            .response(e.getMessage())
                            .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.builder()
                            .response("An error occurred while adding the child.")
                            .build());
        }
    }

    @DeleteMapping("/child/remove/{childId}")
    public ResponseEntity<ApiResponse> removeChild(@PathVariable Long childId) {
        try {
            FamilyMember child = familyMemberService.getById(childId);
            if (child == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.builder().response("Child not found").build());
            }

            familyMemberService.removeChild(child);
            return ResponseEntity.ok(ApiResponse.builder().response("Child removed successfully").build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.builder().response("Error removing child: " + e.getMessage()).build());
        }
    }
}
