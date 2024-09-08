package application;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * This class creates an Entity model of a Asset for storage into the database with a standard
 * format. This is the minimal version of the information required by the database.
 *
 * @author Jay Bryant (https://spring.io/guides/gs/accessing-data-mysql/)
 * @author Sarah Haines
 */
@Entity // This tells Hibernate to make a table out of this class
public class Asset {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String type;

  private String title;

  private String link;

  private String author;

  private String customAttribute1;

  private String customAttribute2;

  private String customAttribute3;

  private String customAttribute4;

  private String association1;

  private String association2;

  private String association3;

  private String association4;

  private String associationRelation1;

  private String associationRelation2;

  private String associationRelation3;

  private String associationRelation4;


  public Asset() {}

  /**
   * Creates asset.
   * 
   * @param id id
   * @param type of asset
   * @param title of asset
   * @param link to asset
   * @param author of asset
   * @param customAttribute1 type attribute content
   * @param customAttribute2 type attribute content
   * @param customAttribute3 type attribute content
   * @param customAttribute4 type attribute content
   * @param association1 description of association
   * @param association2 description of association
   * @param association3 description of association
   * @param association4 description of association
   * @param associationRelation1 id of asset to associate 
   * @param associationRelation2 id of asset to associate 
   * @param associationRelation3 id of asset to associate 
   * @param associationRelation4 id of asset to associate 
   */
  public Asset(Integer id, String type, String title, String link, String author,
      String customAttribute1, String customAttribute2, String customAttribute3,
      String customAttribute4, String association1, String association2, String association3,
      String association4, String associationRelation1, String associationRelation2,
      String associationRelation3, String associationRelation4) {
    this.id = id;
    this.type = type;
    this.title = title;
    this.link = link;
    this.author = author;
    this.customAttribute1 = customAttribute1;
    this.customAttribute2 = customAttribute2;
    this.customAttribute3 = customAttribute3;
    this.customAttribute4 = customAttribute4;
    this.association1 = association1;
    this.association2 = association2;
    this.association3 = association3;
    this.association4 = association4;
    this.associationRelation1 = associationRelation1;
    this.associationRelation2 = associationRelation2;
    this.associationRelation3 = associationRelation3;
    this.associationRelation4 = associationRelation4;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
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

  public void setAssociation1(String association1) {
    this.association1 = association1;
  }

  public String getAssociation1() {
    return association1;
  }

  public void setAssociation2(String association2) {
    this.association2 = association2;
  }

  public String getAssociation2() {
    return association2;
  }

  public void setAssociation3(String association3) {
    this.association3 = association3;
  }

  public String getAssociation3() {
    return association3;
  }

  public void setAssociation4(String association4) {
    this.association4 = association4;
  }

  public String getAssociation4() {
    return association4;
  }

  public void setAssociationRelation1(String associationRelation1) {
    this.associationRelation1 = associationRelation1;
  }

  public String getAssociationRelation1() {
    return associationRelation1;
  }

  public void setAssociationRelation2(String associationRelation2) {
    this.associationRelation2 = associationRelation2;
  }

  public String getAssociationRelation2() {
    return associationRelation2;
  }

  public void setAssociationRelation3(String associationRelation3) {
    this.associationRelation3 = associationRelation3;
  }

  public String getAssociationRelation3() {
    return associationRelation3;
  }

  public void setAssociationRelation4(String associationRelation4) {
    this.associationRelation4 = associationRelation4;
  }

  public String getAssociationRelation4() {
    return associationRelation4;
  }

}
