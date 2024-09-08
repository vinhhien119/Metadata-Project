package application;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * This class creates an Entity model of a Type for storage into the database with a standard
 * format. This is the minimal version of the information required by the database.
 *
 * @author Jay Bryant (https://spring.io/guides/gs/accessing-data-mysql/)
 * @author Sarah Haines
 */
@Entity // This tells Hibernate to make a table out of this class
public class Type {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String typeName;
  
  private String customAttribute1;
  
  private String customAttribute2;
  
  private String customAttribute3;
  
  private String customAttribute4;


  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getTypeName() {
    return typeName;
  }

  public void setTypeName(String typeName) {
    this.typeName = typeName;
  }

  public String getCustomAttribute1() {
    return customAttribute1;
  }

  public void setCustomAttribute1(String customAttribute1) {
    this.customAttribute1 = customAttribute1;
  }
  
  public String getCustomAttribute2() {
    return customAttribute2;
  }

  public void setCustomAttribute2(String customAttribute2) {
    this.customAttribute2 = customAttribute2;
  }
  
  public String getCustomAttribute3() {
    return customAttribute3;
  }

  public void setCustomAttribute3(String customAttribute3) {
    this.customAttribute3 = customAttribute3;
  }
  
  public String getCustomAttribute4() {
    return customAttribute4;
  }

  public void setCustomAttribute4(String customAttribute4) {
    this.customAttribute4 = customAttribute4;
  }
}
