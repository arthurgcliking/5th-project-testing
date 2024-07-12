import { ADMIN_DETAILS, TEST_SESSION, interceptsSetup } from '../../utils';

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
    cy.get('mat-card-title').should('contain', TEST_SESSION.name);
  });
});
