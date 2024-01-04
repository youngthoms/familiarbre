package com.application.familiarbre;

import com.application.familiarbre.models.entites.FamilyMember;
import com.application.familiarbre.models.entites.Gender;
import com.application.familiarbre.models.entites.Node;
import com.application.familiarbre.models.entites.Status;
import com.application.familiarbre.models.helper.FamilyTreeHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.ArrayList;

class FamilyTreeHelperTest {
    private FamilyMember child, parent1, parent2, currentUser, familyMember;
    @BeforeEach
    void setup() {
        child = new FamilyMember();
        child.setId(3L);

        currentUser = new FamilyMember();
        currentUser.setId(10L);
        currentUser.setStatus(Status.PUBLIC);

        parent1 = new FamilyMember();
        parent1.setId(20L);
        parent1.setStatus(Status.PUBLIC);

        parent2 = new FamilyMember();
        parent2.setId(30L);
        parent2.setStatus(Status.PUBLIC);

        familyMember = new FamilyMember();
        familyMember.setId(2L);
    }
    @Test
    void whenGetParentsList_withPublicParents_thenShouldIncludeParents() {
        FamilyMember child = new FamilyMember();
        child.setMid(parent1);
        child.setFid(parent2);

        List<FamilyMember> familyTree = new ArrayList<>();

        FamilyTreeHelper.getParentsList(currentUser, child, familyTree);

        assertTrue(familyTree.contains(parent1));
        assertTrue(familyTree.contains(parent2));
    }

    @Test
    void whenGetParentsList_withPrivateParentBeingCurrentUser_thenShouldIncludeParent() {
        parent1.setStatus(Status.PRIVATE);
        FamilyMember child = new FamilyMember();
        child.setMid(parent1);
        child.setFid(parent2);

        List<FamilyMember> familyTree = new ArrayList<>();

        FamilyTreeHelper.getParentsList(currentUser, child, familyTree);

        assertFalse(familyTree.contains(parent1));
        assertTrue(familyTree.contains(parent2));
    }

    @Test
    void whenGetParentsList_withNullParent_thenShouldNotIncludeParent() {
        FamilyMember child = new FamilyMember();
        child.setMid(null);
        child.setFid(parent2);

        List<FamilyMember> familyTree = new ArrayList<>();

        FamilyTreeHelper.getParentsList(currentUser, child, familyTree);

        assertFalse(familyTree.contains(null));
        assertTrue(familyTree.contains(parent2));
    }
    @Test
    void whenGetFamilyTree_thenReturnCorrectNodeList() {
        // Arrange
        FamilyMember currentUser = new FamilyMember();
        currentUser.setId(1L); // Assuming currentUser is needed

        FamilyMember parent = new FamilyMember();
        parent.setStatus(Status.PUBLIC);
        parent.setId(3L);

        FamilyMember child = new FamilyMember();
        child.setStatus(Status.PUBLIC);
        child.setId(4L);
        child.setMid(parent); // Setting parent as mother of the child

        List<FamilyMember> allFamilyMembers = new ArrayList<>();
        allFamilyMembers.add(parent);
        allFamilyMembers.add(child);

        // Act
        List<Node> nodes = FamilyTreeHelper.getFamilyTree(currentUser, child, allFamilyMembers);

        // Assert
        assertEquals(2, nodes.size()); // Expecting two nodes: one parent and one child
        // Additional assertions to verify the correctness of Node properties
        // For example, checking the parent-child relationship in the nodes
    }
}
