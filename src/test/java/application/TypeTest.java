package application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 * Test suite for Type Class to verify its basic functionality such as getters and setters
 *
 * @author Sarah Haines
 */


class TypeTest {

  /**
   * Test to verify the function of the Type get/set ID
   */
  @Test
  public void typeGetSetID() {
    Type t = new Type();
    t.setId(2);
    assertEquals(t.getId(), 2, "Test that the id can be set and retrieved");
  }

  /**
   * Test to verify the function of the Type get/set Type Name
   */
  @Test
  public void typeGetSetTypeName() {
    Type t = new Type();
    t.setTypeName("Document");
    assertEquals(t.getTypeName(), "Document", "Test that the type name can be set and retrieved");
  }
  
  /**
   * Test to verify the function of the Type get/set Custom Attribute 1
   */
  @Test
  public void typeGetSetCustomAttribute1() {
    Type t = new Type();
    t.setCustomAttribute1("Version");
    assertEquals(t.getCustomAttribute1(), "Version", "Test that the CustomAttribute1 can be set and retrieved");
  }
  
  /**
   * Test to verify the function of the Type get/set Custom Attribute 2
   */
  @Test
  public void typeGetSetCustomAttribute2() {
    Type t = new Type();
    t.setCustomAttribute2("Author");
    assertEquals(t.getCustomAttribute2(), "Author", "Test that the CustomAttribute2 can be set and retrieved");
  }
  
  /**
   * Test to verify the function of the Type get/set Custom Attribute 3
   */
  @Test
  public void typeGetSetCustomAttribute3() {
    Type t = new Type();
    t.setCustomAttribute3("Security Rating");
    assertEquals(t.getCustomAttribute3(), "Security Rating", "Test that the CustomAttribute3 can be set and retrieved");
  }
  
  /**
   * Test to verify the function of the Type get/set Custom Attribute 4
   */
  @Test
  public void typeGetSetCustomAttribute4() {
    Type t = new Type();
    t.setCustomAttribute4("Format");
    assertEquals(t.getCustomAttribute4(), "Format", "Test that the CustomAttribute4 can be set and retrieved");
  }
  
}
