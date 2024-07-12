import { ADMIN_DETAILS, TEST_SESSION, interceptsSetup } from '../../utils';

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
    cy.get('mat-card').should('have.length', 1);
  });
});
