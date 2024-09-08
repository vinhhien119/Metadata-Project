describe("Create Asset Page", () => {
  beforeEach(() => {
    cy.visit("http://localhost:3000/asset/add"); 
  });

  it("displays mandatory attribute elements correctly", () => {
    cy.contains("Type").should("exist");
    cy.contains("Title").should("exist");
    cy.contains("Link").should("exist");
    cy.contains("Author").should("exist");
  }); 

  it("displays custom attribute elements correctly", () => {
    cy.contains("Custom Attribute 1").should("exist");
    cy.contains("Custom Attribute 2").should("exist");
    cy.contains("Custom Attribute 3").should("exist");
    cy.contains("Custom Attribute 4").should("exist");   
  }); 

  it("displays association options correctly", () => {
    cy.contains("Relation 1:").should("exist");
    cy.contains("Relation 2:").should("exist");
    cy.contains("Relation 3:").should("exist");
    cy.contains("Relation 4:").should("exist");   

    cy.contains("Asset Id 1:").should("exist");
    cy.contains("Asset Id 2:").should("exist");
    cy.contains("Asset Id 3:").should("exist");
    cy.contains("Asset Id 4:").should("exist");
  }); 

  it("displays button elements correctly", () => {    
    cy.contains("Save").should("exist");
    cy.contains("Cancel").should("exist");
  }); 

  it("should reset fields when cancel is clicked", () => {

    cy.get("input[id='outlined-textarea']").eq(0).type("Title");
    cy.get("input[id='outlined-textarea']").eq(1).type("Link");
    cy.get("input[id='outlined-textarea']").eq(2).type("Author");

    cy.get("button").contains("Cancel").click();

    cy.get("input[id='outlined-textarea']").should("have.value", "");
  });

  it("should save the asset when save is clicked with all mandatory fields filled", () => {
    // Fill all mandatory fields
    cy.get("button").contains("Type").click();
    cy.get("input[id='outlined-select-type']").click();
    cy.get("input[id='outlined-textarea']").eq(0).type("Title");
    cy.get("input[id='outlined-textarea']").eq(1).type("Link");
    cy.get("input[id='outlined-textarea']").eq(2).type("Author");

    cy.intercept("POST", "http://localhost:3000/asset/add").as("addAssetRequest");

    cy.get("button").contains("Save").click();
    cy.wait("@addAssetRequest").then((xhr) => {
      expect(xhr.response.statusCode).to.equal(200); 
    });

    cy.url().should("include", "http://localhost:3000/asset/find/");
  });
});
