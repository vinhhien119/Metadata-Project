package application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

/**
 * Test suite for Asset Comment Class to verify its basic functionality such as getters and setters
 *
 * @author Sarah Haines
 */


class AssetCommentTest {

  /**
   * Test to verify the function of the asset comments get/set comment ID
   */
  @Test
  public void assetCommentGetSetCommentID() {
    AssetComment ac = new AssetComment();
    ac.setId(6);
    assertEquals(ac.getId(), 6, "Test that the comment id can be set and retrieved");
  }

  /**
   * Test to verify the function of the asset comments get/set asset ID
   */
  @Test
  public void assetCommentGetSetItemID() {
    AssetComment ac = new AssetComment();
    ac.setItemId(2);
    assertEquals(ac.getItemId(), 2, "Test that the comment item ids can be set and retrieved");
  }

  /**
   * Test to verify the function of the asset comments get/set type ID
   */
  @Test
  public void assetCommentGetSetComment() {
    AssetComment ac = new AssetComment();
    ac.setComment("I'm a comment");
    assertEquals(ac.getComment(), "I'm a comment",
        "Test that the comments can be set and retrieved");
  }

  /**
   * Test to verify the function of the asset comments get/set timestamp
   */
  @Test
  public void assetCommentGetSetTimestamp() {
    AssetComment ac = new AssetComment();
    LocalDateTime time = LocalDateTime.now();
    ac.setTimestamp(time);
    assertEquals(ac.getTimestamp(), time, "Test that the timestamp can be set and retrieved");
  }


  /**
   * Test to verify the function of the asset comments get/set username
   */
  @Test
  public void assetCommentGetSetUsername() {
    AssetComment ac = new AssetComment();
    ac.setUsername("username");
    assertEquals(ac.getUsername(), "username", "Test that the usernames can be set and retrieved");
  }

}
