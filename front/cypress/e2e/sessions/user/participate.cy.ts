import { USER_DETAILS, TEST_SESSION, interceptsSetup } from '../../utils';

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
