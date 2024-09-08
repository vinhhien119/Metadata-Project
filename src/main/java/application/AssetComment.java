package application;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

/**
 * This class creates an Entity model of an Asset Comment for storage into the database with a
 * standard format. This is the minimal version of the information required by the database.
 *
 * @author Sarah Haines
 */
@Entity // This tells Hibernate to make a table out of this class
public class AssetComment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private Integer itemId;

  private String comment;

  private LocalDateTime timestamp;

  private boolean visibleComment;

  private String username;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getItemId() {
    return itemId;
  }

  public void setItemId(Integer itemId) {
    this.itemId = itemId;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }

  public boolean isVisibleComment() {
    return visibleComment;
  }

  public void setVisibleComment(boolean visibleComment) {
    this.visibleComment = visibleComment;
  }


}
