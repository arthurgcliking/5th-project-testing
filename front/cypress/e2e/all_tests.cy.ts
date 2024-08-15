import { ADMIN_DETAILS, TEST_SESSION, TEACHERS_LIST, USER_DETAILS, interceptsSetup } from './utils';

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


describe('Admin - Create Session', () => {
  beforeEach(() => {
    interceptsSetup();
    cy.visit('/login');
    cy.intercept('POST', '/api/auth/login', ADMIN_DETAILS);
    cy.get('input[formControlName=email]').type('admin@studio.com');
    cy.get('input[formControlName=password]').type('admin123{enter}{enter}');
    cy.url().should('include', '/sessions');
  });

  it('should create a new session', () => {
    cy.get('button[mat-raised-button] span').contains('Create').click();
    cy.get('input[formControlName="name"]').type('New Session');
    const formattedDate = '2024-02-14';
    cy.get('input[formControlName="date"]').type(formattedDate);
    cy.get('mat-select[formControlName="teacher_id"]').click();
    cy.get('mat-option').contains(TEACHERS_LIST[0].firstName).click();
    cy.get('textarea[formControlName="description"]').type('New description');
    cy.get('button[mat-raised-button]').contains('Save').click();
    cy.get('snack-bar-container').contains('Session created !').should('exist');
    cy.get('snack-bar-container button span').contains('Close').click();
    cy.get('mat-card').should('have.length', 3);
  });
});




describe('Admin - Delete Session', () => {
  beforeEach(() => {
    interceptsSetup();
    cy.visit('/login');
    cy.intercept('POST', '/api/auth/login', ADMIN_DETAILS);
    cy.get('input[formControlName=email]').type('admin@studio.com');
    cy.get('input[formControlName=password]').type('admin123{enter}{enter}');
    cy.url().should('include', '/sessions');
  });

  it('should delete a session', () => {
    cy.get('button').contains('Detail').click();
    cy.get('button').contains('Delete').click();
    cy.get('snack-bar-container').contains('Session deleted !').should('exist');
    cy.get('snack-bar-container button span').contains('Close').click();
    //cy.get('mat-card').should('have.length', 1);
  });
});




describe('Admin - Update Session', () => {
  beforeEach(() => {
    interceptsSetup();
    cy.visit('/login');
    cy.intercept('POST', '/api/auth/login', ADMIN_DETAILS);
    cy.get('input[formControlName=email]').type('admin@studio.com');
    cy.get('input[formControlName=password]').type('admin123{enter}{enter}');
    cy.url().should('include', '/sessions');
  });

  it('should update an existing session', () => {
    cy.get('button[mat-raised-button] span').contains('Edit').click();
    cy.get('input[formControlName="name"]').clear().type('Updated Session');
    cy.get('button[mat-raised-button]').contains('Save').click();
    cy.get('snack-bar-container').contains('Session updated !').should('exist');
    cy.get('snack-bar-container button span').contains('Close').click();
    cy.get('mat-card-title').should('contain', 'Updated Session');
  });
});





describe('Admin - View Sessions', () => {
  beforeEach(() => {
    interceptsSetup();
    cy.visit('/login');
    cy.intercept('POST', '/api/auth/login', ADMIN_DETAILS);
    cy.get('input[formControlName=email]').type('admin@studio.com');
    cy.get('input[formControlName=password]').type('admin123{enter}{enter}');
    cy.url().should('include', '/sessions');
  });

  it('should view the list of sessions', () => {
    cy.get('mat-card').should('have.length', 1);
    //cy.get('mat-card-title').should('contain', TEST_SESSION.name);
  });
});



describe('User - Participate in Session', () => {
  beforeEach(() => {
    interceptsSetup();
    cy.visit('/login');
    cy.intercept('POST', '/api/auth/login', USER_DETAILS);
    cy.get('input[formControlName=email]').type('user@example.com');
    cy.get('input[formControlName=password]').type('user123{enter}{enter}');
    cy.url().should('include', '/sessions');
  });

  it('should participate in a session', () => {
    cy.get('button[mat-raised-button]').click();
    cy.get('button[mat-raised-button]').contains('Do not participate');
    cy.get('span').contains('attendees').then((span) => {
      const text = span.text();
      const attendeesCount = Number(text.match(/\d+/)[0]);
      expect(attendeesCount).equal(1);
    });
  });
});




describe('User - View Sessions', () => {
  beforeEach(() => {
    interceptsSetup();
    cy.visit('/login');
    cy.intercept('POST', '/api/auth/login', USER_DETAILS);
    cy.get('input[formControlName=email]').type('user@example.com');
    cy.get('input[formControlName=password]').type('user123{enter}{enter}');
    cy.url().should('include', '/sessions');
  });

  it('should view the list of sessions and session details', () => {
    cy.get('mat-card').should('have.length', 1);
    //cy.get('mat-card-title').should('contain', TEST_SESSION.name);
    cy.get('button[mat-raised-button] span').contains('Detail').click();
    cy.get('button').contains('Delete').should('not.exist');
  });
});
