describe('Login spec', () => {
  beforeEach(() => {
    cy.visit('/login');
  });

  it('Login successfully', () => {
    cy.intercept('POST', '/api/auth/login', {
      body: {
        id: 1,
        username: 'userName',
        firstName: 'firstName',
        lastName: 'lastName',
        admin: true
      },
    });

    cy.intercept({
      method: 'GET',
      url: '/api/session',
    }, []).as('session');

    cy.get('input[formControlName=email]').type("yoga@studio.com");
    cy.get('input[formControlName=password]').type("test!1234{enter}{enter}");

    cy.url().should('include', '/sessions');
  });

  it('Shows error on incorrect login', () => {
    cy.intercept('POST', '/api/auth/login', {
      statusCode: 401,
      body: { message: 'Invalid credentials' },
    }).as('loginRequest');

    cy.get('input[formControlName=email]').type("wrong@user.com");
    cy.get('input[formControlName=password]').type("wrongPassword{enter}{enter}");

    cy.get('.error').should('contain', 'An error occurred');
  });

  it('Shows error when mandatory fields are missing', () => {
    // Type in email and then clear password to make it invalid
    cy.get('input[formControlName=email]').type("yoga@studio.com");
    cy.get('input[formControlName=password]').type("test!1234").clear();

    // Check for the presence of invalid class on password
    cy.get('input[formControlName=password]').should('have.class', 'ng-invalid');

    // Check for form invalid class
    cy.get('form').should('have.class', 'ng-invalid');

    // Trigger form submission with force
    cy.get('button[type=submit]').click({ force: true });

    // Wait for possible async operations
    cy.wait(500);

    // Re-check the form's state and ensure the password field is invalid
    cy.get('form').should('have.class', 'ng-invalid');
    cy.get('input[formControlName=password]').should('have.class', 'ng-invalid');
  });
});
