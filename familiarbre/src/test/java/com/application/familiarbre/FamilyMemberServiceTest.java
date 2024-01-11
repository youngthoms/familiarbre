//package com.application.familiarbre;
//
//import static org.mockito.Mockito.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//import com.application.familiarbre.models.dao.FamilyMemberRepository;
//import com.application.familiarbre.models.entites.FamilyMember;
//import com.application.familiarbre.models.entites.Gender;
//import com.application.familiarbre.models.entites.Status;
//import com.application.familiarbre.models.services.FamilyMemberService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//
//
//@ExtendWith(MockitoExtension.class)
//public class FamilyMemberServiceTest {
//
//    @Mock
//    private FamilyMemberRepository familyMemberRepository;
//    @InjectMocks
//    private FamilyMemberService familyMemberService;
//    private FamilyMember mother, father, child;
//
//    @BeforeEach
//    void setup() {
//        mother = new FamilyMember();
//        mother.setId(1L);
//        mother.setGender(Gender.female);
//
//        father = new FamilyMember();
//        father.setId(2L);
//        father.setGender(Gender.male);
//
//        child = new FamilyMember();
//        child.setId(3L);
//    }
//    @Test
//    public void whenAddChildAndParentMale_thenSuccess() {
//        // Act
//        familyMemberService.addChild(father, child);
//
//        // Assert
//        assertEquals(child.getFid(), father);
//        assertNull(child.getMid());
//        verify(familyMemberRepository, times(1)).save(child);
//    }
//
//    @Test
//    public void whenAddChildAndParentFemale_thenSuccess() {
//        // Act
//        familyMemberService.addChild(mother, child);
//
//        // Assert
//        assertEquals(child.getMid(), mother);
//        assertNull(child.getFid());
//        verify(familyMemberRepository, times(1)).save(child);
//    }
//
//    @Test
//    public void whenAddChildWithNullParent_thenThrowIllegalArgumentException() {
//        assertThrows(IllegalArgumentException.class, () -> {
//            familyMemberService.addChild(null, child);
//        });
//    }
//
//    @Test
//    public void whenAddChildWithParentHavingNullGender_thenThrowIllegalStateException() {
//        FamilyMember parentWithNullGender = new FamilyMember();
//        parentWithNullGender.setId(4L);
//        parentWithNullGender.setGender(null);
//
//        assertThrows(IllegalStateException.class, () -> {
//            familyMemberService.addChild(parentWithNullGender, child);
//        });
//    }
//
//    @Test
//    public void whenAddChildToChildWithExistingMother_thenThrowIllegalStateException() {
//        child.setMid(new FamilyMember());
//
//        assertThrows(IllegalStateException.class, () -> {
//            familyMemberService.addChild(mother, child);
//        });
//    }
//
//    @Test
//    public void whenAddChildToChildWithExistingFather_thenThrowIllegalStateException() {
//        child.setFid(new FamilyMember());
//
//        assertThrows(IllegalStateException.class, () -> {
//            familyMemberService.addChild(father, child);
//        });
//    }
//}
