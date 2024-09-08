describe("Find Asset Page", () => {
  beforeEach(() => {
    cy.visit("http://localhost:3000/asset/find"); 
  });

  it("should display the table with assets", () => {
    cy.get("table").should("exist");
    cy.get("thead").contains("ID").should("exist");
    cy.get("thead").contains("Type").should("exist");
    cy.get("thead").contains("Link").should("exist");
    cy.get("thead").contains("Title").should("exist");
    cy.get("thead").contains("Author").should("exist");
    cy.get("thead").contains("Actions").should("exist");
  });

it('renders table with asset data', () => {
    cy.get('table').should('exist');
    cy.get('tbody').find('tr').should('have.length.above', 0);
  });

  it('navigates to correct page when view icon is clicked', () => {
    cy.get('tbody').find('tr').first().find('a').click();
    cy.url().should('include', 'http://localhost:3000/asset/open/');
  });

  it('opens edit confirmation dialog when edit icon is clicked', () => {
    cy.get('tbody').find('tr').first().find('button').eq(1).click();
    cy.get('@mui/icons-material/Edit').should('be.visible');
  });

  it('opens delete confirmation dialog when delete icon is clicked', () => {
    cy.get('tbody').find('tr').first().find('button').eq(2).click();
    cy.get('@mui/icons-material/Delete').should('be.visible');
  });
});
