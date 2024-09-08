package application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import application.config.SecurityConfig;
import application.model.Role;
import application.model.User;
import application.service.AuthenticationService;
import application.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import application.repository.TokenRepository;
import application.repository.UserRepository;



/**
 * Test suite to validate the functions held within the Main Controller Class.
 *
 * @author Yusur Taha
 * @author Sarah Haines
 * @author Romina Petrozzi
 */


@ExtendWith(MockitoExtension.class)
@WebMvcTest(MainController.class)
@ImportAutoConfiguration(exclude = SecurityConfig.class)
class MainControllerTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private AssetRepository assetRepository;
  @MockBean
  private TypeRepository typeRepository;
  @MockBean
  private ActionLogRepository actionLogRepository;
  @MockBean
  private UserRepository userRepository;
  @MockBean
  private TokenRepository tokenRepository;
  @MockBean
  private AssetCommentRepository assetCommentRepository;

  @Autowired
  private JwtService jwtService;

  private String jwtToken;
  @Autowired
  private MainController mc;


  @BeforeEach
  void beforeEach() {
    User user = new User();
    user.setUsername("wkis088");
    user.setPassword("123");
    user.setRole(Role.ADMIN);
    user.setFirstName("Hien");
    user.setLastName("Phan");
    userRepository.save(user);

    jwtService = new JwtService(tokenRepository);
    jwtToken = jwtService.generateToken(user);
    System.out.println(jwtToken);
  }
  /**
   * Test successful string response of method which runs the map for the post request of create new
   * asset.
   *
   * @throws Exception , could be any checked exception.
   */
  @Test
  @WithMockUser(username = "testUser", authorities = {"ADMIN"})
  void testAddNewAsset() throws Exception {
    String requestBody =
        "{\"type\":\"document\", \"title\":\"This is a test document\", \"link\":\"file:///Users/yusur/Downloads/wk1a-combined.pdf\", \"lineNum\":120, \"progLang\":\"Java\", \"isDocumentedIn\":1, \"dependsOn\":2, \"succeededBy\":4, \"customAttribute1\":\"Project1\", \"customAttribute2\":\"Author\", \"customAttribute3\":\"Language\", \"customAttribute4\":\"Subject\"}";

    MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/asset/add")
        .contentType(MediaType.APPLICATION_JSON).content(requestBody)).andReturn();

    assertEquals("Asset saved successfully", result.getResponse().getContentAsString());
  }

  /**
   * Test error string response of method which runs the map for the post request of create new
   * asset.
   *
   * @throws Exception , could be any checked exception.
   */
  @Test
  void testAddNewAsset_Exception() throws Exception {

    // asset to be added
    Asset asset1 = new Asset();
    asset1.setId(12345);
    asset1.setLink("www.youtube.com");
    asset1.setAuthor("Ellie H");
    asset1.setTitle("This is a title");
    asset1.setType("Video");

    // Mock the behaviour of assetRepository.save() to throw an exception
    when(assetRepository.save(asset1))
        .thenThrow(new RuntimeException("This is a made up exception"));

    // Call the addNewAsset method
    ResponseEntity<String> response = mc.addNewAsset(asset1);

    // Verify that the response is as expected
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertEquals("Error: This is a made up exception", response.getBody());
  }

  /**
   * Test string response of the method which runs the map for the post request of create new type.
   *
   * @throws Exception , could be any checked exception.
   */
  @Test
  @WithMockUser(username = "testUser", authorities = {"ADMIN"})
  void testAddNewType() throws Exception {
    String requestBody =
        "{\"typeName\":\"document\", \"customAttribute1\":\"Size\", \"customAttribute2\":\"Security Level\", \"customAttribute3\":\"Revision\", \"customAttribute4\":\"Author\"}";

    MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/type/add")
        .contentType(MediaType.APPLICATION_JSON).content(requestBody)).andReturn();

    assertEquals("Type saved successfully", result.getResponse().getContentAsString());
  }

  /**
   * Test exception string response of the method which runs the map for the post request of create
   * new type.
   *
   * @throws Exception , could be any checked exception.
   */
  @Test
  void testAddNewType_Exception() throws Exception {

    Type type = new Type();

    // Mock the behaviour of assetRepository.save() to throw an exception
    when(typeRepository.save(type))
        .thenThrow(new RuntimeException("This is a made up exception 2"));

    // Call the addNewAsset method
    ResponseEntity<String> response = mc.addNewType(type);

    // Verify that the response is as expected
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertEquals("Error: This is a made up exception 2", response.getBody());
  }


  /**
   * Test to validate the string response of the method which allows for population of the attribute
   * data for a specific asset.
   *
   * @throws Exception , could be any checked exception.
   */
  @Test
  @WithMockUser(username = "testUser", authorities = {"ADMIN"})
  // test 2
  void testAssetForm() throws Exception {
    mvc.perform(MockMvcRequestBuilders.get("/asset/createAsset"))
        .andExpect(MockMvcResultMatchers.status().isOk()) // expects that the request was successful
        .andExpect(MockMvcResultMatchers.view().name("createAsset"))
        .andExpect(MockMvcResultMatchers.model().attributeExists("createAsset"));
  }

  /**
   * // * Test to validate the string response of method which populates database with asset
   * attributes // * data. // * // * @throws Exception , could be any checked exception. //
   */


  /**
   * Test to validate that upon searching for a specific title, the asset with the same title is
   * retrieved.
   *
   * @throws Exception , could be any checked exception.
   */
  @Test
  void testGetAssetByTitle() throws Exception {

    // asset to be added
    Asset asset1 = new Asset();
    asset1.setId(12345);
    asset1.setLink("www.youtube.com");
    asset1.setAuthor("Ellie H");
    asset1.setTitle("Beans");
    asset1.setType("Video");

    mc.addNewAsset(asset1);
    String titleToFind = "Beans";
    Asset expectedAsset = new Asset();
    expectedAsset.setTitle(titleToFind);

    when(assetRepository.findAll()).thenReturn(List.of(expectedAsset));

    List<Asset> actualAssets = mc.getAssetByTitle(titleToFind);

    assertEquals(expectedAsset.getTitle(), actualAssets.get(0).getTitle(),
        "Should return an asset with the specified title");

  }

  /**
   * Test to validate that upon searching for a specific title, all assets with the same title are
   * retrieved as a list.
   *
   * @throws Exception , could be any unchecked exception.
   */
  @Test
  void testGetAssetListByTitle() throws Exception {

    // asset to be added
    Asset asset1 = new Asset();
    asset1.setId(12345);
    asset1.setLink("www.youtube.com");
    asset1.setAuthor("Ellie H");
    asset1.setTitle("Beans");
    asset1.setType("Video");

    mc.addNewAsset(asset1);

    // asset to be added
    Asset asset2 = new Asset();
    asset2.setId(12332);
    asset2.setLink("randomLink");
    asset2.setAuthor("Layla M");
    asset2.setTitle("Beans");
    asset2.setType("document");

    mc.addNewAsset(asset2);

    // asset to be added
    Asset asset3 = new Asset();
    asset3.setId(1254);
    asset3.setLink("randomLink2");
    asset3.setAuthor("Ava S");
    asset3.setTitle("Beans");
    asset3.setType("mp3");

    mc.addNewAsset(asset3);

    // asset to be added
    Asset asset4 = new Asset();
    asset4.setId(1344);
    asset4.setLink("www.youtube.com");
    asset4.setAuthor("Sofia F");
    asset4.setTitle("notBeans");
    asset4.setType("Video");

    mc.addNewAsset(asset4);

    List<Asset> expectedAssets = new ArrayList<>();
    when(assetRepository.findAll()).thenReturn(expectedAssets);

    String titleToFind = "Beans";

    // make mock assets and add to list containing expected result
    Asset expectedAsset1 = new Asset();
    expectedAsset1.setTitle(titleToFind);
    expectedAsset1.setType("document");
    expectedAssets.add(expectedAsset1);

    Asset expectedAsset2 = new Asset();
    expectedAsset2.setTitle(titleToFind);
    expectedAsset2.setType("video");
    expectedAssets.add(expectedAsset2);

    Asset expectedAsset3 = new Asset();
    expectedAsset3.setTitle(titleToFind);
    expectedAsset3.setType("mp3");
    expectedAssets.add(expectedAsset3);

    // get assets with title "Beans" and add to list
    List<Asset> actualAssets = mc.getAssetByTitle(titleToFind);

    for (int i = 0; i < expectedAssets.size(); i++) {
      System.out.println("Expected asset title: " + expectedAssets.get(i).getTitle() + "\n"
          + " Type: " + expectedAssets.get(i).getType());
      System.out.println("Result asset title: " + actualAssets.get(i).getTitle() + "\n" + " Type: "
          + actualAssets.get(i).getType() + "\n");

    }


    // check that all assets in database with title "Beans" are returned
    for (int i = 0; i < expectedAssets.size(); i++) {
      assertEquals(expectedAssets.get(i).getTitle(), actualAssets.get(i).getTitle(),
          "Should return a list of assets with the searched title Beans.");
    }

  }

  /**
   * Test to validate that upon searching for a specific type, an asset with the same type is
   * retrieved.
   *
   * @throws Exception , could be any unchecked exception.
   */
  @Test
  void testGetAssetByType() throws Exception {

    // asset to be added
    Asset asset1 = new Asset();
    asset1.setId(12345);
    asset1.setLink("www.youtube.com");
    asset1.setAuthor("Ellie H");
    asset1.setTitle("Beans");
    asset1.setType("video");

    mc.addNewAsset(asset1);
    String typeToFind = "video";
    Asset expectedAsset = new Asset();
    expectedAsset.setType(typeToFind);

    when(assetRepository.findAll()).thenReturn(List.of(expectedAsset));

    List<Asset> actualAssets = mc.getAssetByType(typeToFind);

    assertEquals(expectedAsset.getType(), actualAssets.get(0).getType(),
        "Should return an asset with the specified type");

  }

  /**
   * Test to validate that upon searching for a specific type, all assets with the same type are
   * retrieved as a list.
   *
   * @throws Exception , could be any unchecked exception.
   */
  @Test
  void testGetAssetListByType() throws Exception {

    // asset to be added
    Asset asset1 = new Asset();
    asset1.setId(12345);
    asset1.setLink("www.youtube.com");
    asset1.setAuthor("Ellie H");
    asset1.setTitle("Beans");
    asset1.setType("Video");

    mc.addNewAsset(asset1);

    // asset to be added
    Asset asset2 = new Asset();
    asset2.setId(12332);
    asset2.setLink("randomLink");
    asset2.setAuthor("Ava S");
    asset2.setTitle("Beans");
    asset2.setType("document");

    mc.addNewAsset(asset2);

    // asset to be added
    Asset asset3 = new Asset();
    asset3.setId(1254);
    asset3.setLink("randomLink2");
    asset3.setAuthor("Layla M");
    asset3.setTitle("Beans");
    asset3.setType("mp3");

    mc.addNewAsset(asset3);

    // asset to be added
    Asset asset4 = new Asset();
    asset4.setId(1344);
    asset4.setLink("www.youtube.com");
    asset4.setAuthor("Ben F");
    asset4.setTitle("notBeans");
    asset4.setType("Video");

    mc.addNewAsset(asset4);

    List<Asset> expectedAssets = new ArrayList<>();
    when(assetRepository.findAll()).thenReturn(expectedAssets);

    String typeToFind = "Video";

    // make mock assets and add to list containing expected result
    Asset expectedAsset1 = new Asset();
    expectedAsset1.setTitle("fish");
    expectedAsset1.setType(typeToFind);
    expectedAssets.add(expectedAsset1);

    Asset expectedAsset2 = new Asset();
    expectedAsset2.setTitle("cat");
    expectedAsset2.setType(typeToFind);
    expectedAssets.add(expectedAsset2);

    Asset expectedAsset3 = new Asset();
    expectedAsset3.setTitle("sheep");
    expectedAsset3.setType(typeToFind);
    expectedAssets.add(expectedAsset3);

    // get assets with type "video" and add to list
    List<Asset> actualAssets = mc.getAssetByType(typeToFind);

    for (int i = 0; i < expectedAssets.size(); i++) {
      System.out.println("Asset title: " + expectedAssets.get(i).getTitle() + "\n"
          + " Expected result type: " + expectedAssets.get(i).getType());
      System.out.println("Asset title: " + actualAssets.get(i).getTitle() + "\n"
          + " Expected result type: " + actualAssets.get(i).getType() + "\n");

    }


    // check that all assets in database with type "video" are returned
    for (int i = 0; i < expectedAssets.size(); i++) {
      assertEquals(expectedAssets.get(i).getType(), actualAssets.get(i).getType(),
          "Should return a list of assets with the searched type video.");
    }

  }

  /**
   * Test to validate that upon searching for a specific link, an asset with the same link is
   * retrieved.
   *
   * @throws Exception , could be any unchecked exception.
   */
  @Test
  void testGetAssetByLink() throws Exception {

    // asset to be added
    Asset asset1 = new Asset();
    asset1.setId(12345);
    asset1.setLink("www.youtube.com");
    asset1.setAuthor("Sarah H");
    asset1.setTitle("Beans");
    asset1.setType("video");

    mc.addNewAsset(asset1);
    String linkToFind = "www.youtube.com";
    Asset expectedAsset = new Asset();
    expectedAsset.setLink(linkToFind);

    when(assetRepository.findAll()).thenReturn(List.of(expectedAsset));

    List<Asset> actualAssets = mc.getAssetByLink(linkToFind);

    assertEquals(expectedAsset.getLink(), actualAssets.get(0).getLink(),
        "Should return an asset with the specified link");

  }

  /**
   * Test to validate that upon searching for a specific link, all assets with the same link are
   * retrieved as a list.
   *
   * @throws Exception , could be any unchecked exception.
   */
  @Test
  void testGetAssetListByLink() throws Exception {

    // asset to be added
    Asset asset1 = new Asset();
    asset1.setId(12345);
    asset1.setLink("www.youtube.com");
    asset1.setAuthor("Ava S");
    asset1.setTitle("Beans");
    asset1.setType("Video");

    mc.addNewAsset(asset1);

    // asset to be added
    Asset asset2 = new Asset();
    asset2.setId(12332);
    asset2.setLink("randomLink");
    asset2.setAuthor("Ellie H");
    asset2.setTitle("Beans");
    asset2.setType("document");

    mc.addNewAsset(asset2);

    // asset to be added
    Asset asset3 = new Asset();
    asset3.setId(1254);
    asset3.setLink("randomLink2");
    asset3.setAuthor("Ava S");
    asset3.setTitle("Beans");
    asset3.setType("mp3");

    mc.addNewAsset(asset3);

    // asset to be added
    Asset asset4 = new Asset();
    asset4.setId(1344);
    asset4.setLink("www.youtube.com");
    asset4.setAuthor("Finn S");
    asset4.setTitle("notBeans");
    asset4.setType("Video");

    mc.addNewAsset(asset4);

    List<Asset> expectedAssets = new ArrayList<>();
    when(assetRepository.findAll()).thenReturn(expectedAssets);

    String linkToFind = "www.youtube.com";

    // make mock assets and add to list containing expected result
    Asset expectedAsset1 = new Asset();
    expectedAsset1.setTitle("fish");
    expectedAsset1.setLink(linkToFind);
    expectedAssets.add(expectedAsset1);

    Asset expectedAsset2 = new Asset();
    expectedAsset2.setTitle("cat");
    expectedAsset2.setLink(linkToFind);
    expectedAssets.add(expectedAsset2);

    Asset expectedAsset3 = new Asset();
    expectedAsset3.setTitle("sheep");
    expectedAsset3.setLink(linkToFind);
    expectedAssets.add(expectedAsset3);

    // get assets with link "www.youtube.com" and add to list
    List<Asset> actualAssets = mc.getAssetByLink(linkToFind);

    for (int i = 0; i < expectedAssets.size(); i++) {
      System.out.println("Asset title: " + expectedAssets.get(i).getTitle() + "\n"
          + " Expected result link: " + expectedAssets.get(i).getLink());
      System.out.println("Asset title: " + actualAssets.get(i).getTitle() + "\n"
          + " Expected result link: " + actualAssets.get(i).getLink() + "\n");

    }


    // check that all assets in database with link "www.youtube.com" are returned
    for (int i = 0; i < expectedAssets.size(); i++) {
      assertEquals(expectedAssets.get(i).getType(), actualAssets.get(i).getType(),
          "Should return a list of assets with the searched link 'www.youtube.com'.");
    }

  }

  /**
   * Test to validate that upon searching for a specific programming language, an asset with the
   * same programming language is retrieved.
   *
   * @throws Exception , could be any unchecked exception.
   */
  @Test
  void testGetAssetByAuthor() throws Exception {

    // asset to be added
    Asset asset1 = new Asset();
    asset1.setId(12345);
    asset1.setLink("www.youtube.com");
    asset1.setAuthor("Ellie H");
    asset1.setTitle("Beans");
    asset1.setType("video");

    mc.addNewAsset(asset1);
    String authorToFind = "Ellie H";
    Asset expectedAsset = new Asset();
    expectedAsset.setAuthor(authorToFind);

    when(assetRepository.findAll()).thenReturn(List.of(expectedAsset));

    List<Asset> actualAssets = mc.getAssetByAuthor(authorToFind);

    assertEquals(expectedAsset.getAuthor(), actualAssets.get(0).getAuthor(),
        "Should return an asset with the specified author.");

  }

  /**
   * Test to validate that upon searching for a specific language, all assets with the same language
   * are retrieved as a list.
   *
   * @throws Exception , could be any unchecked exception.
   */
  @Test
  void testGetAssetListByAuthor() throws Exception {

    // asset to be added
    Asset asset1 = new Asset();
    asset1.setId(12345);
    asset1.setLink("www.youtube.com");
    asset1.setAuthor("Ellie H");
    asset1.setTitle("Beans");
    asset1.setType("Video");

    mc.addNewAsset(asset1);

    // asset to be added
    Asset asset2 = new Asset();
    asset2.setId(12332);
    asset2.setLink("randomLink");
    asset2.setAuthor("Ellie H");
    asset2.setTitle("Beans");
    asset2.setType("document");

    mc.addNewAsset(asset2);

    // asset to be added
    Asset asset3 = new Asset();
    asset3.setId(1254);
    asset3.setLink("randomLink2");
    asset3.setAuthor("Ava S");
    asset3.setTitle("Beans");
    asset3.setType("mp3");

    mc.addNewAsset(asset3);

    // asset to be added
    Asset asset4 = new Asset();
    asset4.setId(1344);
    asset4.setLink("www.youtube.com");
    asset4.setAuthor("Ellie H");
    asset4.setTitle("notBeans");
    asset4.setType("Video");

    mc.addNewAsset(asset4);

    List<Asset> expectedAssets = new ArrayList<>();
    when(assetRepository.findAll()).thenReturn(expectedAssets);

    String authorToFind = "Ellie H";

    // make mock assets and add to list containing expected result
    Asset expectedAsset1 = new Asset();
    expectedAsset1.setTitle("fish");
    expectedAsset1.setAuthor(authorToFind);
    expectedAssets.add(expectedAsset1);

    Asset expectedAsset2 = new Asset();
    expectedAsset2.setTitle("cat");
    expectedAsset2.setAuthor(authorToFind);
    expectedAssets.add(expectedAsset2);

    Asset expectedAsset3 = new Asset();
    expectedAsset3.setTitle("sheep");
    expectedAsset3.setAuthor(authorToFind);
    expectedAssets.add(expectedAsset3);

    // get assets with language "English" and add to list
    List<Asset> actualAssets = mc.getAssetByAuthor(authorToFind);

    for (int i = 0; i < expectedAssets.size(); i++) {
      System.out.println("Asset title: " + expectedAssets.get(i).getTitle() + "\n"
          + " Expected result language: " + expectedAssets.get(i).getAuthor());
      System.out.println("Asset title: " + actualAssets.get(i).getTitle() + "\n"
          + " Expected result language: " + actualAssets.get(i).getAuthor() + "\n");

    }


    // check that all assets in database with language "English" are returned
    for (int i = 0; i < expectedAssets.size(); i++) {
      assertEquals(expectedAssets.get(i).getAuthor(), actualAssets.get(i).getAuthor(),
          "Should return a list of assets with the searched language English.");
    }

  }

  /**
   * Test to validate that upon accessing the /asset/find/all path, all data stored about assets in
   * the database, is retrieved as a JSON successfully.
   *
   * @throws Exception , could be any unchecked exception.
   */
  @Test
  @WithMockUser(username = "testUser", authorities = {"ADMIN"})
  void testGetAllAssets() throws Exception {
    // asset to be added
    Asset asset1 = new Asset();
    asset1.setId(12345);
    asset1.setLink("www.youtube.com");
    asset1.setAuthor("Ellie H");
    asset1.setTitle("Beans");
    asset1.setType("Video");

    mc.addNewAsset(asset1);

    // asset to be added
    Asset asset2 = new Asset();
    asset2.setId(12332);
    asset2.setLink("randomLink");
    asset2.setAuthor("Ava S");
    asset2.setTitle("Beans");
    asset2.setType("document");

    mc.addNewAsset(asset2);

    // Mock the behaviour of the findAll method to return the mock assets
    when(assetRepository.findAll()).thenReturn(Arrays.asList(asset1, asset2));
    // Perform the GET request
    mvc.perform(MockMvcRequestBuilders.get("/asset/find/all"))
        // Expects that the request was successful
        .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(jsonPath("$.length()").value(2))
        // Check the JSON properties of the first asset in the array
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(12345))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].type").value("Video"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Beans"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].link").value("www.youtube.com"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].lineNum").value(156))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].progLang").value("English"));
  }

  /**
   * Test to validate that upon accessing the /asset/find/{id} path, all data stored about asset
   * with unique id in the database, is retrieved as a JSON successfully.
   *
   * @throws Exception , could be any unchecked exception.
   */
  @Test
  void testGetAssetById() throws Exception {

    // asset to be added
    Asset asset1 = new Asset();
    asset1.setId(12345);
    asset1.setTitle("Beans");

    mc.addNewAsset(asset1);

    // asset to be added
    Asset asset2 = new Asset();
    asset2.setId(12332);
    asset2.setTitle("Toast");

    mc.addNewAsset(asset2);


    // Mock the behaviour of the findById method to return the asset
    when(assetRepository.findById(12332)).thenReturn(Optional.of(asset2));
    // Perform the GET request
    mvc.perform(MockMvcRequestBuilders.get("/asset/find/{id}", 12332))
        // Check the JSON properties of the returned asset
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(12332))
        .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Toast"));

  }

  /**
   * Test to validate that upon accessing the /asset/deleteAsset path, all data stored about any
   * asset in the database, is deleted.
   *
   * @throws Exception , could be any unchecked exception.
   */
  @Test
  @WithMockUser(username = "testUser", authorities = {"ADMIN"})
  void testDeleteAssetModel() throws Exception {

    // asset to be added
    Asset asset1 = new Asset();
    asset1.setId(12345);
    asset1.setTitle("Beans");

    mc.addNewAsset(asset1);

    // Perform the GET request
    mvc.perform(MockMvcRequestBuilders.get("/asset/deleteAsset"))
        // Expect view name to be "deleteAsset"
        .andExpect(MockMvcResultMatchers.view().name("deleteAsset"))
        // Expect the model to contain an attribute named "deleteAsset"
        .andExpect(MockMvcResultMatchers.model().attributeExists("deleteAsset"));

  }

  /**
   * Test to validate that upon accessing the /asset/deleteAsset path, all data stored about an
   * asset in the database, is deleted using its id.
   *
   * @throws Exception , could be any unchecked exception.
   */
  @Test
  @WithMockUser(username = "testUser", authorities = {"ADMIN"})
  void testDeleteAssetById() throws Exception {

    // asset to be added
    Asset asset1 = new Asset();
    asset1.setId(12345);
    asset1.setTitle("Beans");

    mc.addNewAsset(asset1);

    // mock repository behaviour
    Mockito.when(assetRepository.existsById(12345)).thenReturn(true);

    // Stimulate the HTTP delete request and check if it is successful
    mvc.perform(MockMvcRequestBuilders.delete("/asset/delete/{id}", 12345))
        .andExpect(MockMvcResultMatchers.status().isOk())
        // Expect view name to be "resultDeleteAsset"
        .andExpect(MockMvcResultMatchers.view().name("resultDeleteAsset"));
  }

  /**
   * Test to validate that upon accessing the /asset/editAsset/{id}, the edit asset page is rendered
   * depending on a given asset id.
   *
   * @throws Exception , could be any unchecked exception.
   */
  @Test
  @WithMockUser(username = "testUser", authorities = {"ADMIN"})
  void testEditAssetForm() throws Exception {
    // asset to be added
    Asset asset1 = new Asset();
    asset1.setId(12345);

    mc.addNewAsset(asset1);

    // Mock the behaviour of the findById method to return the asset
    when(assetRepository.findById(12345)).thenReturn(Optional.of(asset1));
    // Perform the GET request
    mvc.perform(MockMvcRequestBuilders.get("/asset/editAsset/{id}", 12345))
        // Check the JSON properties of the returned asset
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.view().name("editAsset"))
        .andExpect(MockMvcResultMatchers.model().attributeExists("asset"))
        .andExpect(MockMvcResultMatchers.model().attribute("asset", asset1))
        .andExpect(MockMvcResultMatchers.model().attributeExists("id"))
        .andExpect(MockMvcResultMatchers.model().attribute("id", 12345));


  }

  /**
   * Test to validate the exception string response upon accessing the /asset/editAsset/{id} with no
   * asset, the edit asset page is not rendered.
   *
   * @throws Exception , could be any unchecked exception.
   */
  @Test
  @WithMockUser(username = "testUser", authorities = {"ADMIN"})
  void testEditAssetForm_Exception() throws Exception {

    Mockito.when(assetRepository.findById(123)).thenReturn(Optional.empty());

    mvc.perform(MockMvcRequestBuilders.get("/asset/editAsset/{id}", 123))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.view().name("assetNotFound"));
  }


  /**
   * Test to validate that upon accessing the /type/find/all path, all data stored about types in
   * the database, is retrieved as a JSON successfully.
   *
   * @throws Exception , could be any unchecked exception.
   */
  @Test
  @WithMockUser(username = "testUser", authorities = {"ADMIN"})
  void testGetAllTypes() throws Exception {
    // type to be added
    Type type1 = new Type();
    type1.setId(12345);
    type1.setTypeName("Bean");

    mc.addNewType(type1);

    // type to be added
    Type type2 = new Type();
    type2.setId(12332);
    type2.setTypeName("Toast");

    mc.addNewType(type2);

    // Mock the behaviour of the findAll method to return the mock types
    when(typeRepository.findAll()).thenReturn(Arrays.asList(type1, type2));
    // Perform the GET request
    mvc.perform(MockMvcRequestBuilders.get("/type/find/all"))
        // Expects that the request was successful with 2 types in database
        .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(jsonPath("$.length()").value(2))
        // Check the JSON properties of the first and second type in the array
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(12345))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].typeName").value("Bean"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(12332))
        .andExpect(MockMvcResultMatchers.jsonPath("$[1].typeName").value("Toast"));

  }

  /**
   * Test to validate the string response of the method which allows for population of the attribute
   * data for a specific type.
   *
   * @throws Exception , could be any checked exception.
   */
  @Test
  @WithMockUser(username = "testUser", authorities = {"ADMIN"})
  void testTypeForm() throws Exception {
    mvc.perform(MockMvcRequestBuilders.get("/type/createType"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.view().name("createType"))
        .andExpect(MockMvcResultMatchers.model().attributeExists("createType"));
  }

  /**
   * Test to validate that upon accessing the /type/find/{id} path, all data stored about type with
   * unique id in the database, is retrieved as a JSON successfully.
   *
   * @throws Exception , could be any unchecked exception.
   */
  @Test
  @WithMockUser(username = "testUser", authorities = {"ADMIN"})
  void testGetTypeById() throws Exception {

    // type to be added
    Type type1 = new Type();
    type1.setId(12345);
    type1.setTypeName("Beans");

    mc.addNewType(type1);

    // type to be added
    Type type2 = new Type();
    type2.setId(12332);
    type2.setTypeName("Toast");

    mc.addNewType(type2);


    // Mock the behaviour of the findById method to return the type
    when(typeRepository.findById(12332)).thenReturn(Optional.of(type2));
    // Perform the GET request
    mvc.perform(MockMvcRequestBuilders.get("/type/find/{id}", 12332))
        // Check the JSON properties of the returned type
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(12332))
        .andExpect(MockMvcResultMatchers.jsonPath("$.typeName").value("Toast"));

  }

  /**
   * Test to validate that upon accessing the /type/deleteAsset path, all data stored about any type
   * in the database, is deleted.
   *
   * @throws Exception , could be any unchecked exception.
   */
  @Test
  void testDeleteTypeModel() throws Exception {

    // asset to be added
    Type type1 = new Type();
    type1.setId(12345);
    type1.setTypeName("Beans");

    mc.addNewType(type1);

    // Perform the GET request
    mvc.perform(MockMvcRequestBuilders.get("/type/deleteType"))
        // Expect view name to be "deleteType"
        .andExpect(MockMvcResultMatchers.view().name("deleteType"))
        // Expect the model to contain an attribute named "deleteType"
        .andExpect(MockMvcResultMatchers.model().attributeExists("deleteType"));

  }

  /**
   * Test to validate that upon accessing the /type/deleteAsset path, all data stored about a type
   * in the database, is deleted using its id.
   *
   * @throws Exception , could be any unchecked exception.
   */
  @Test
  @WithMockUser(username = "testUser", authorities = {"ADMIN"})
  void testDeleteTypeById() throws Exception {

    // asset to be added
    Type type1 = new Type();
    type1.setId(12345);
    type1.setTypeName("Beans");

    mc.addNewType(type1);

    // mock repository behaviour
    Mockito.when(typeRepository.existsById(12345)).thenReturn(true);

    // Stimulate the HTTP delete request and check if it is successful
    mvc.perform(MockMvcRequestBuilders.delete("/type/delete/{id}", 12345))
        .andExpect(MockMvcResultMatchers.status().isOk())
        // Expect view name to be "resultDeleteType"
        .andExpect(MockMvcResultMatchers.view().name("resultDeleteType"));
  }

  /**
   * Test to validate that action logs are successfully added to database and that upon accessing
   * the /log/find/all, all data stored about actions in the database, is retrieved as a JSON
   * successfully.
   *
   * @throws Exception , could be any unchecked exception.
   */
  @Test
  @WithMockUser(username = "testUser", authorities = {"ADMIN"})
  void testAddAndGetAllLog() throws Exception {

    mc.addActionLog(134, null, "this is an action");
    mc.addActionLog(null, 154, "this is another action");

    ActionLog al = new ActionLog();
    al.setAssetId(134);
    al.setAction("this is an action");

    ActionLog al2 = new ActionLog();
    al2.setTypeId(154);
    al2.setAction("this is another action");

    // Mock the behaviour of the findAll method to return the mock types
    when(actionLogRepository.findAll()).thenReturn(Arrays.asList(al, al2));
    // Perform the GET request
    mvc.perform(MockMvcRequestBuilders.get("/log/find/all"))
        // Expects that the request was successful with 2 types in database
        .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(jsonPath("$.length()").value(2))
        // Check the JSON properties of the first and second type in the array
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].assetId").value(134))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].action").value("this is an action"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[1].typeId").value(154))
        .andExpect(MockMvcResultMatchers.jsonPath("$[1].action").value("this is another action"));

  }

  /**
   * Test to validate that upon accessing the /log/find/{id} path, all data stored about action with
   * unique id in the database, is retrieved as a JSON successfully.
   *
   * @throws Exception , could be any unchecked exception.
   */
  @Test
  @WithMockUser(username = "testUser", authorities = {"ADMIN"})
  void testGetLogById() throws Exception {

    // create mock logs
    ActionLog al = new ActionLog();
    al.setAssetId(134);
    al.setAction("this is an action");

    ActionLog al2 = new ActionLog();
    al2.setTypeId(154);
    al2.setAction("this is another action");

    mc.addActionLog(134, null, "this is an action");
    mc.addActionLog(null, 154, "this is another action");

    // Mock the behaviour of the findById method to return the log
    when(actionLogRepository.findById(134)).thenReturn(Optional.of(al));
    // Perform the GET request
    mvc.perform(MockMvcRequestBuilders.get("/log/find/{id}", 134))
        // Check the JSON properties of the returned log
        .andExpect(MockMvcResultMatchers.jsonPath("$.assetId").value(134))
        .andExpect(MockMvcResultMatchers.jsonPath("$.action").value("this is an action"));

  }

  /**
   * Test to validate that upon accessing the /type/editType/{id}, the edit type page is rendered
   * depending on a given type id.
   *
   * @throws Exception , could be any unchecked exception.
   */
  @Test
  @WithMockUser(username = "testUser", authorities = {"ADMIN"})
  void testEditTypeForm() throws Exception {
    // asset to be added
    Type type1 = new Type();
    type1.setId(12345);

    mc.addNewType(type1);

    // Mock the behaviour of the findById method to return the asset
    when(typeRepository.findById(12345)).thenReturn(Optional.of(type1));
    // Perform the GET request
    mvc.perform(MockMvcRequestBuilders.get("/type/editType/{id}", 12345))
        // Check the JSON properties of the returned asset
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.view().name("editType"))
        .andExpect(MockMvcResultMatchers.model().attributeExists("type"))
        .andExpect(MockMvcResultMatchers.model().attribute("type", type1))
        .andExpect(MockMvcResultMatchers.model().attributeExists("id"))
        .andExpect(MockMvcResultMatchers.model().attribute("id", 12345));

  }

  /**
   * Test to validate the exception string response upon accessing the /type/editType/{id} with no
   * type, the edit type page is not rendered.
   *
   * @throws Exception , could be any unchecked exception.
   */
  @Test
  @WithMockUser(username = "testUser", authorities = {"ADMIN"})
  void testEditTypeForm_Exception() throws Exception {

    Mockito.when(typeRepository.findById(123)).thenReturn(Optional.empty());

    mvc.perform(MockMvcRequestBuilders.get("/type/editType/{id}", 123))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.view().name("typeNotFound"));
  }


  /**
   * Test to validate the string response of the method which allows for population of the attribute
   * data for a specific user.
   *
   * @throws Exception , could be any checked exception.
   */
  @Test
  @WithMockUser(username = "testUser", authorities = {"ADMIN"})
  void testUserForm() throws Exception {
    mvc.perform(MockMvcRequestBuilders.get("/user/createUser"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.view().name("createUser"))
        .andExpect(MockMvcResultMatchers.model().attributeExists("createUser"));
  }

  /**
   * Test to validate that upon accessing the /comment/find/all path, all data stored about comments
   * in the database, is retrieved as a JSON successfully.
   *
   * @throws Exception , could be any unchecked exception.
   */
  @Test
  @WithMockUser(username = "testUser", authorities = {"ADMIN"})
  void testGetAllComments() throws Exception {
    // comment to be added
    AssetComment comment1 = new AssetComment();
    comment1.setId(11);
    comment1.setItemId(2);
    comment1.setComment("Comment");
    comment1.setTimestamp(LocalDateTime.of(2024, 3, 5, 10, 30, 00));

    assetCommentRepository.save(comment1);

    // comment to be added
    AssetComment comment2 = new AssetComment();
    comment2.setId(12);
    comment2.setItemId(4);
    comment2.setComment("Comment2");
    comment2.setTimestamp(LocalDateTime.of(2024, 3, 5, 11, 30, 00));

    assetCommentRepository.save(comment2);

    // Mock the behaviour of the findAll method to return the mock types
    when(assetCommentRepository.findAll()).thenReturn(Arrays.asList(comment1, comment2));
    // Perform the GET request
    mvc.perform(MockMvcRequestBuilders.get("/comment/find/all"))
        // Expects that the request was successful with 2 types in database
        .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(jsonPath("$.length()").value(2))
        // Check the JSON properties of the first and second type in the array
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(11))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].itemId").value(2))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].comment").value("Comment"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].timestamp")
            .value(comment1.getTimestamp().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
        .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(12))
        .andExpect(MockMvcResultMatchers.jsonPath("$[1].itemId").value(4))
        .andExpect(MockMvcResultMatchers.jsonPath("$[1].comment").value("Comment2"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[1].timestamp")
            .value(comment2.getTimestamp().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)));
  }

  /**
   * Test to validate that upon accessing the /comment/find/{id} path, all data stored about comment
   * with unique id in the database, is retrieved as a JSON successfully.
   *
   * @throws Exception , could be any unchecked exception.
   */
  @Test
  @WithMockUser(username = "testUser", authorities = {"ADMIN"})
  void testGetCommentById() throws Exception {

    // comment to be added
    AssetComment comment1 = new AssetComment();
    comment1.setId(11);
    comment1.setItemId(2);
    comment1.setComment("Comment");
    comment1.setTimestamp(LocalDateTime.of(2024, 3, 5, 10, 30, 00));

    assetCommentRepository.save(comment1);

    // comment to be added
    AssetComment comment2 = new AssetComment();
    comment2.setId(12);
    comment2.setItemId(4);
    comment2.setComment("Comment2");
    comment2.setTimestamp(LocalDateTime.of(2024, 3, 5, 11, 30, 00));

    assetCommentRepository.save(comment2);


    // Mock the behaviour of the findById method to return the asset
    when(assetCommentRepository.findById(11)).thenReturn(Optional.of(comment1));
    // Perform the GET request
    mvc.perform(MockMvcRequestBuilders.get("/comment/find/{id}", 11))
        // Check the JSON properties of the returned asset
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(11))
        .andExpect(MockMvcResultMatchers.jsonPath("$.comment").value("Comment"));

  }

  /**
   * Test successful string response of method which runs the map for the post request of create new
   * comment.
   *
   * @throws Exception , could be any checked exception.
   */
  @Test
  @WithMockUser(username = "testUser", authorities = {"ADMIN"})
  void testAddNewComment() throws Exception {
    String requestBody = "{\"itemid\":1, \"comment\":\"Comment\"}";

    MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/comment/add")
        .contentType(MediaType.APPLICATION_JSON).content(requestBody)).andReturn();

    assertEquals("Comment saved successfully", result.getResponse().getContentAsString());
  }

  /**
   * Test error string response of method which runs the map for the post request of create new
   * comment.
   *
   * @throws Exception , could be any checked exception.
   */
  @Test
  void testAddNewComment_Exception() throws Exception {

    // asset to be added
    AssetComment comment1 = new AssetComment();
    comment1.setId(11);
    comment1.setItemId(2);
    comment1.setComment("Comment");
    comment1.setTimestamp(LocalDateTime.of(2024, 3, 5, 10, 30, 00));


    // Mock the behaviour of assetRepository.save() to throw an exception
    when(assetCommentRepository.save(comment1))
        .thenThrow(new RuntimeException("This is a made up exception"));

    // Call the addNewAsset method
    ResponseEntity<String> response = mc.addNewComment(comment1);

    // Verify that the response is as expected
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertEquals("Error: This is a made up exception", response.getBody());
  }

  /**
   * Test to make sure a conflict error message is produced when user chooses an asset title which
   * already exists in the database.
   *
   * @throws Exception , could be any checked exception.
   */
  @Test
  void testAddAsset_ConflictingTitles() {
    // Duplicate titled assets to be added
    Asset asset1 = new Asset();
    asset1.setTitle("Duplicate title asset");

    mc.addNewAsset(asset1);

    // Mock the behavior of assetRepository.existsByTitle() to return true, indicating the title
    // already exists
    when(assetRepository.existsByTitle(asset1.getTitle())).thenReturn(true);

    Asset asset2 = new Asset();
    asset2.setTitle("Duplicate title asset");

    ResponseEntity<String> response = mc.addNewAsset(asset2);

    assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    assertEquals("An asset with the same title already exists", response.getBody());

  }

}
