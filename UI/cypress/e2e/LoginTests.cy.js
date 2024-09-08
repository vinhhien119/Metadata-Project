describe("Login Component", () => {
  beforeEach(() => {
    cy.visit("http://localhost:3000/"); // Visit the login page
  });

  it("should display login form elements", () => {    
    cy.contains("Username").should("exist");
    cy.contains("Password").should("exist");
    cy.contains("Login").should("exist");
    cy.contains("Create A New Account").should("exist");
  });

  it("should display error message for invalid login", () => {
    cy.contains("Login").click();
    cy.contains("Invalid username or password, attempts remaining: 2").should("exist");
  });

  it('should redirect to asset/find page on successful login', () => {
    cy.intercept('POST', 'http://localhost:3000/', {
      statusCode: 200,
      body: { message: 'Login successful', token: 'fakeToken' }
    }).as('loginRequest');
  
    cy.get('button').click();
    
    cy.wait('@loginRequest').then((interception) => {
      // Assert that the URL has changed to '/asset/find'
      cy.url().should('include', 'http://localhost:3000/asset/find');
    });
  });
});
