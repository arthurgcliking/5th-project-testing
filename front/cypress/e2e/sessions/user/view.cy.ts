import { USER_DETAILS, TEST_SESSION, interceptsSetup } from '../../utils';

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
    cy.get('mat-card-title').should('contain', TEST_SESSION.name);
    cy.get('button[mat-raised-button] span').contains('Detail').click();
    cy.get('button').contains('Delete').should('not.exist');
  });
});
