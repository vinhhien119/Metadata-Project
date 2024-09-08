describe("Sign Up", () => {
  beforeEach(() => {
    cy.visit("http://localhost:3000/signup/"); // Visit the Sign Up page
  });

  it("displays sign-up form elements correctly", () => {
    cy.contains("First Name").should("exist");
    cy.contains("Last name").should("exist");
    cy.contains("Username").should("exist");
    cy.contains("Password").should("exist");
    cy.contains("Retype Password").should("exist");
    cy.contains("SignUp").should("exist");
    cy.contains("Return to login").should("exist");
  }); 

  it('displays "Create new user success" when passwords do not match', () => {
    cy.contains("SignUp").click();
    cy.contains("Create new user success").should("exist");
  });

  it('redirects to login page when "Return to login" button is clicked', () => {
    cy.contains("Return to login").click();
    cy.url().should('eq', 'http://localhost:3000/'); 
  });

  it('registers user when passwords match', () => {
    cy.intercept('POST', 'http://localhost:3000/signup/', {
      statusCode: 200,
      body: { status: 'success' }
    }).as('register');
  
    cy.get('input[name="First Name"]').type('John');
    cy.get('input[name="Last Name"]').type('Hi');
    cy.get('input[name="Username"]').type('johnhi');
    cy.get('input[name="Password"]').type('password');
    cy.get('input[name="Retype Password"]').type('password');
    cy.get('button').contains('Sign Up').click();
  
    cy.wait('@register').then(interception => {
      expect(interception.response.statusCode).to.eq(200);
    });
  
    cy.get('p').should('contain.text', 'Create new user success');
  });
  
});
