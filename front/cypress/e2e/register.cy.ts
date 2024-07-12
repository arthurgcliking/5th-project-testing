describe('Register spec', () => {
  beforeEach(() => {
    cy.visit('/register');
  });

  it('Registers successfully', () => {
    cy.intercept('POST', '/api/auth/register', {
      statusCode: 200,
    });

    cy.get('input[formControlName=firstName]').type("John");
    cy.get('input[formControlName=lastName]').type("Doe");
    cy.get('input[formControlName=email]').type("john.doe@studio.com");
    cy.get('input[formControlName=password]').type("test!1234{enter}{enter}");

    cy.url().should('include', '/login');
  });

  it('Shows error on failed registration', () => {
    cy.intercept('POST', '/api/auth/register', {
      statusCode: 400,
      body: { message: 'Registration error' },
    }).as('registerRequest');

    cy.get('input[formControlName=firstName]').type("John");
    cy.get('input[formControlName=lastName]').type("Doe");
    cy.get('input[formControlName=email]').type("john.doe@studio.com");
    cy.get('input[formControlName=password]').type("test!1234{enter}{enter}");

    cy.get('.error').should('contain', 'An error occurred');
  });

  it('Shows error when mandatory fields are missing', () => {
    // Only type in the firstName field and leave others empty
    cy.get('input[formControlName=firstName]').type("John");

    // Check if the submit button is disabled
    cy.get('button[type=submit]').should('be.disabled');

    // Try to submit the form using force
    cy.get('button[type=submit]').click({ force: true });

    // Ensure form is still marked as invalid
    cy.get('form').should('have.class', 'ng-invalid');

    // Check for the invalid class on the other required fields
    cy.get('input[formControlName=lastName]').should('have.class', 'ng-invalid');
    cy.get('input[formControlName=email]').should('have.class', 'ng-invalid');
    cy.get('input[formControlName=password]').should('have.class', 'ng-invalid');
  });
});
