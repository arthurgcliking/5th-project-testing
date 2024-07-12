import { ADMIN_DETAILS, TEST_SESSION, EDITED_TEST_SESSION, interceptsSetup } from '../../utils';

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
