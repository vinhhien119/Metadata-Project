package application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 * Test suite for Asset Class to verify its basic functionality such as getters and setters
 *
 * @author Sarah Haines
 */


class AssetTest {

  /**
   * Test to verify the function of the Asset get/set ID
   */
  @Test
  public void assetGetSetID() {
    Asset a = new Asset();
    a.setId(2);
    assertEquals(a.getId(), 2, "Test that the id can be set and retrieved");
  }

  /**
   * Test to verify the function of the Asset get/set Type
   */
  @Test
  public void assetGetSetType() {
    Asset a = new Asset();
    a.setType("Document");
    assertEquals(a.getType(), "Document", "Test that the type can be set and retrieved");
  }
  
  /**
   * Test to verify the function of the Asset get/set Title
   */
  @Test
  public void assetGetSetTitle() {
    Asset a = new Asset();
    a.setTitle("I am a document");
    assertEquals(a.getTitle(), "I am a document", "Test that the title can be set and retrieved");
  }
  
  /**
   * Test to verify the function of the Asset get/set Link
   */
  @Test
  public void assetGetSetLink() {
    Asset a = new Asset();
    a.setLink("www.google.com");
    assertEquals(a.getLink(), "www.google.com", "Test that the link can be set and retrieved");
  }
  
  /**
   * Test to verify the function of the Asset get/set ProgLang
   */
  @Test
  public void assetGetSetAuthor() {
    Asset a = new Asset();
    a.setAuthor("Ellie H");
    assertEquals(a.getAuthor(), "Ellie H", "Test that the author can be set and retrieved");
  }
  
  /**
   * Test to verify the function of the Asset get/set CustomAttribute1
   */
  @Test
  public void assetGetSetCustomAttribute1() {
    Asset a = new Asset();
    a.setCustomAttribute1("one");
    assertEquals(a.getCustomAttribute1(), "one", "Test that the CustomAttribute1 can be set and retrieved");
  }

  /**
   * Test to verify the function of the Asset get/set CustomAttribute2
   */
  @Test
  public void assetGetSetCustomAttribute2() {
    Asset a = new Asset();
    a.setCustomAttribute2("two");
    assertEquals(a.getCustomAttribute2(), "two", "Test that the CustomAttribute2 can be set and retrieved");
  }
  
  /**
   * Test to verify the function of the Asset get/set CustomAttribute3
   */
  @Test
  public void assetGetSetCustomAttribute3() {
    Asset a = new Asset();
    a.setCustomAttribute3("three");
    assertEquals(a.getCustomAttribute3(), "three", "Test that the CustomAttribute3 can be set and retrieved");
  }
  
  /**
   * Test to verify the function of the Asset get/set CustomAttribute4
   */
  @Test
  public void assetGetSetCustomAttribute4() {
    Asset a = new Asset();
    a.setCustomAttribute4("four");
    assertEquals(a.getCustomAttribute4(), "four", "Test that the CustomAttribute4 can be set and retrieved");
  }
  
  /**
   * Test to verify the function of the Asset get/set Association1
   */
  @Test
  public void assetGetSetAssociation1() {
    Asset a = new Asset();
    a.setAssociation1("one");
    assertEquals(a.getAssociation1(), "one", "Test that the Association1 can be set and retrieved");
  }

  /**
   * Test to verify the function of the Asset get/set Association2
   */
  @Test
  public void assetGetSetAssociation2() {
    Asset a = new Asset();
    a.setAssociation2("two");
    assertEquals(a.getAssociation2(), "two", "Test that the Association2 can be set and retrieved");
  }
  
  /**
   * Test to verify the function of the Asset get/set Association3
   */
  @Test
  public void assetGetSetAssociation3() {
    Asset a = new Asset();
    a.setAssociation3("three");
    assertEquals(a.getAssociation3(), "three", "Test that the Association3 can be set and retrieved");
  }
  
  /**
   * Test to verify the function of the Asset get/set Association4
   */
  @Test
  public void assetGetSetAssociation4() {
    Asset a = new Asset();
    a.setAssociation4("four");
    assertEquals(a.getAssociation4(), "four", "Test that the Association4 can be set and retrieved");
  }
  /**
   * Test to verify the function of the Asset get/set AssociationRelation1
   */
  @Test
  public void assetGetSetAssociationRelation1() {
    Asset a = new Asset();
    a.setAssociationRelation1("one");
    assertEquals(a.getAssociationRelation1(), "one", "Test that the AssociationRelation1 can be set and retrieved");
  }

  /**
   * Test to verify the function of the Asset get/set AssociationRelation2
   */
  @Test
  public void assetGetSetAssociationRelation2() {
    Asset a = new Asset();
    a.setAssociationRelation2("two");
    assertEquals(a.getAssociationRelation2(), "two", "Test that the AssociationRelation2 can be set and retrieved");
  }
  
  /**
   * Test to verify the function of the Asset get/set AssociationRelation3
   */
  @Test
  public void assetGetSetAssociationRelation3() {
    Asset a = new Asset();
    a.setAssociationRelation3("three");
    assertEquals(a.getAssociationRelation3(), "three", "Test that the AssociationRelation3 can be set and retrieved");
  }
  
  /**
   * Test to verify the function of the Asset get/set AssociationRelation4
   */
  @Test
  public void assetGetSetAssociationRelation4() {
    Asset a = new Asset();
    a.setAssociationRelation4("four");
    assertEquals(a.getAssociationRelation4(), "four", "Test that the AssociationRelation4 can be set and retrieved");
  }
  

}
