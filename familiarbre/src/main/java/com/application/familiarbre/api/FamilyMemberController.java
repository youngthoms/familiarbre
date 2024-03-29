package com.application.familiarbre.api;

import com.application.familiarbre.models.entites.*;
import com.application.familiarbre.models.helper.FamilyMemberHelper;
import com.application.familiarbre.models.helper.FamilyTreeHelper;
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
    public ResponseEntity<AllFamilyMembersResponse> allFamilyMembers() {
        List<FamilyMember> familyMembers = familyMemberService.getAllPublic();
        List<PublicFamilyMember> publicFamilyMembers = FamilyMemberHelper.familyMembersToPublicFamilyMembers(familyMembers);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        AllFamilyMembersResponse
                                .builder()
                                .familyMembers(publicFamilyMembers)
                                .build()
                );
    }

    @GetMapping("/tree/{user_id_target}")
    public List<Node> getTree(@RequestParam(required = false) String token, @PathVariable Long user_id_target) {
        FamilyMember familyMemberUserTarget = familyMemberService.getByUserId(user_id_target);

        if (token == null) {
            return familyMemberService.hasAccess(null, familyMemberUserTarget.getId());
        }

        User user = userService.loadUserByToken(token);
        FamilyMember familyMemberUserConnected = familyMemberService.getByUserId(user.getId());

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
    public ResponseEntity<ApiResponse> update(@RequestBody UpdateRequest updateRequest) {

        FamilyMember member = familyMemberService.getById(updateRequest.getId());

        familyMemberService.update(member, updateRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.builder()
                        .response("Update successfully.")
                        .build());
    }

    @PostMapping("/spouse/add")
    public ResponseEntity<ApiResponse> addSpouse(@RequestBody AddSpouseRequest addSpouseRequest) {
        FamilyMember member1 = familyMemberService.getById(addSpouseRequest.getMember1());
        FamilyMember member2 = familyMemberService.getById(addSpouseRequest.getMember2());

        if (member1 == null || member2 == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.builder()
                            .response("Spouse not found")
                            .build());
        } else {
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
        FamilyMember child = null;

        if (addChildRequest.getChildId() != null) {
            child = familyMemberService.getById(addChildRequest.getChildId());
        }
        FamilyMember dad = null;

        if (addChildRequest.getDadId() != null) {
            dad = familyMemberService.getById(addChildRequest.getDadId());

        }
        FamilyMember mom = null;

        if (addChildRequest.getMomId() != null) {
            mom = familyMemberService.getById(addChildRequest.getMomId());
        }

        if (child == null) {
            child = new FamilyMember();
        }
        if (addChildRequest.getGender() == Gender.male) {
            child.setGender(Gender.male);
        } else {
            child.setGender(Gender.female);
        }
        if (mom == null) {
            mom = new FamilyMember();
            mom.setGender(Gender.female);
            familyMemberService.save(mom);
        }
        child.setMid(mom);
        if (dad == null) {
            dad = new FamilyMember();
            dad.setGender(Gender.male);
            familyMemberService.save(dad);
        }
        child.setFid(dad);
        if (!mom.getPids().isEmpty()) {
            List temp = mom.getPids();
            temp.remove(mom.getPids().size() - 1);
            mom.setPids(temp);
        }
        if (!dad.getPids().isEmpty()) {
            List temp = dad.getPids();
            dad.getPids().remove(dad.getPids().size() - 1);
            dad.setPids(temp);
        }
        if (mom != null && dad != null) {
            mom.addPid(dad);
            dad.addPid(mom);
        }

        familyMemberService.save(child);


        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.builder()
                        .response("Child added to parent successfully.")
                        .build());
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

    @GetMapping("/new")
    public ResponseEntity<CreateFamilyMemberResponse> createFamilyMember() {
        FamilyMember fm = familyMemberService.create();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CreateFamilyMemberResponse
                        .builder()
                        .id(fm.getId())
                        .build()
                );
    }

    @GetMapping("/privacy/{userId}")
    public ResponseEntity<GetPrivacyValueResponse> getPrivacyValue(@PathVariable Long userId) {
        FamilyMember familyMember = familyMemberService.getByUserId(userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(GetPrivacyValueResponse
                        .builder()
                        .status(familyMember.getStatus().name().toLowerCase())
                        .build()
                );
    }
}
