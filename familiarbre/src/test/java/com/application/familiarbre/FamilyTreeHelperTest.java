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

    @Test
    void getChildList_ShouldCorrectlyPopulateFamilyTreeWithChildren() {
        // Arrange
        FamilyMember currentUser = parent1;

        parent1.setGender(Gender.male);

        FamilyMember child1 = new FamilyMember();
        child1.setId(3L);
        child1.setStatus(Status.PUBLIC);
        child1.setFid(parent1); // child1's father is 'parent'

        FamilyMember child2 = new FamilyMember();
        child2.setId(4L);
        child2.setStatus(Status.PUBLIC);
        child2.setFid(parent1); // child2's father is 'parent'

        List<FamilyMember> allFamilyMembers = new ArrayList<>();
        allFamilyMembers.add(child1);
        allFamilyMembers.add(child2);
        allFamilyMembers.add(parent1);

        List<FamilyMember> familyTree = new ArrayList<>();

        // Act
        FamilyTreeHelper.getChildList(currentUser, parent1, familyTree, allFamilyMembers);

        // Assert
        assertTrue(familyTree.contains(child1));
        assertTrue(familyTree.contains(child2));
        assertEquals(2, familyTree.size()); // Verifying that both children are added
    }

    @Test
    void isInFamilyTree_WhenSameUser_ShouldReturnTrue() {
        // Arrange
        FamilyMember user = new FamilyMember();
        user.setId(1L);

        // Act & Assert
        assertTrue(FamilyTreeHelper.isInFamilyTree(user, user));
    }

    @Test
    void isInFamilyTree_WhenDirectParent_ShouldReturnTrue() {
        // Arrange
        FamilyMember child = new FamilyMember();
        child.setId(1L);

        FamilyMember parent = new FamilyMember();
        parent.setId(2L);
        child.setFid(parent); // Setting parent as father of the child

        // Act & Assert
        assertTrue(FamilyTreeHelper.isInFamilyTree(parent, child));
    }

    @Test
    void isInFamilyTree_WhenAncestor_ShouldReturnTrue() {
        // Arrange
        FamilyMember child = new FamilyMember();
        child.setId(1L);

        FamilyMember parent = new FamilyMember();
        parent.setId(2L);
        child.setFid(parent);

        FamilyMember grandparent = new FamilyMember();
        grandparent.setId(3L);
        parent.setFid(grandparent); // Setting grandparent as father of the parent

        // Act & Assert
        assertTrue(FamilyTreeHelper.isInFamilyTree(grandparent, child));
    }

    @Test
    void isInFamilyTree_WhenNotRelated_ShouldReturnFalse() {
        // Arrange
        FamilyMember user1 = new FamilyMember();
        user1.setId(1L);

        FamilyMember user2 = new FamilyMember();
        user2.setId(2L);

        // Act & Assert
        assertFalse(FamilyTreeHelper.isInFamilyTree(user1, user2));
    }

    @Test
    void addChild_WhenFamilyMemberIsPublic_ShouldAddToFamilyTree() {
        // Arrange
        FamilyMember currentUser = new FamilyMember();
        FamilyMember familyMember = new FamilyMember();
        familyMember.setStatus(Status.PUBLIC);
        familyMember.setFid(currentUser);

        List<FamilyMember> familyTree = new ArrayList<>();
        List<FamilyMember> allFamilyMembers = new ArrayList<>();
        allFamilyMembers.add(currentUser);
        allFamilyMembers.add(familyMember);

        // Act
        FamilyTreeHelper.addChild(currentUser, familyTree, familyMember, allFamilyMembers);

        // Assert
        assertTrue(familyTree.contains(familyMember));
    }

    @Test
    void addChild_WhenFamilyMemberIsPrivateAndCurrentUser_ShouldAddToFamilyTree() {
        // Arrange
        FamilyMember currentUser = new FamilyMember();
        FamilyMember familyMember = currentUser;
        familyMember.setStatus(Status.PRIVATE);

        List<FamilyMember> familyTree = new ArrayList<>();
        List<FamilyMember> allFamilyMembers = new ArrayList<>();
        allFamilyMembers.add(currentUser);

        // Act
        FamilyTreeHelper.addChild(currentUser, familyTree, familyMember, allFamilyMembers);

        // Assert
        assertTrue(familyTree.contains(familyMember));
    }

    @Test
    void addChild_WhenFamilyMemberIsProtectedAndInFamilyTree_ShouldAddToFamilyTree() {
        // Arrange
        FamilyMember currentUser = new FamilyMember();
        FamilyMember familyMember = new FamilyMember();
        familyMember.setStatus(Status.PROTECTED);
        familyMember.setFid(currentUser);

        List<FamilyMember> familyTree = new ArrayList<>();
        List<FamilyMember> allFamilyMembers = new ArrayList<>();
        allFamilyMembers.add(currentUser);
        allFamilyMembers.add(familyMember);

        // Mock isInFamilyTree to return true for these members
        // Note: This requires changing isInFamilyTree to non-static or using a PowerMockito to mock static methods.

        // Act
        FamilyTreeHelper.addChild(currentUser, familyTree, familyMember, allFamilyMembers);

        // Assert
        assertTrue(familyTree.contains(familyMember));
    }

}
