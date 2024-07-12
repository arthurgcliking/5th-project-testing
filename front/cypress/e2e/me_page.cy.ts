import { ADMIN_ACCOUNT_DETAILS, interceptApiCalls, login } from './utils';
import { USER_ACCOUNT_DETAILS } from './utils';


describe('Account page as an admin', () => {
  beforeEach(() => {
    interceptApiCalls();
    login(ADMIN_ACCOUNT_DETAILS);
  });

  it('should show their info and NOT include a delete button', () => {
    cy.get('span.link').contains('Account').click();
    cy.url().should('include', '/me');

    cy.get('mat-card-title h1').should('contain', 'User information');
    cy.get('p')
      .eq(0)
      .should('contain', `Name: ${ADMIN_ACCOUNT_DETAILS.firstName} ${ADMIN_ACCOUNT_DETAILS.lastName}`);
    cy.get('p').eq(1).should('contain', `Email: ${ADMIN_ACCOUNT_DETAILS.email}`);
    cy.get('p').eq(2).should('contain', 'You are admin');
    // cy.get('p').eq(3).should('contain', 'Create at: January 12, 2024');
    // cy.get('p').eq(4).should('contain', 'Last update: January 12, 2024');

    cy.get('button[mat-raised-button]').should('not.exist');
  });
});


describe('Account page as a regular user', () => {
  beforeEach(() => {
    interceptApiCalls();
    login(USER_ACCOUNT_DETAILS);
  });

  it('should show their info and include a delete button', () => {
    cy.get('span.link').contains('Account').click();
    cy.url().should('include', '/me');

    cy.get('mat-card-title h1').should('contain', 'User information');
    cy.get('p')
      .eq(0)
      .should('contain', `Name: ${USER_ACCOUNT_DETAILS.firstName} ${USER_ACCOUNT_DETAILS.lastName}`);
    cy.get('p').eq(1).should('contain', `Email: ${USER_ACCOUNT_DETAILS.email}`);
    cy.get('p').eq(2).should('contain', 'Delete my account:');
    // cy.get('p').eq(3).should('contain', 'Created at: January 12, 2024');
    // cy.get('p').eq(4).should('contain', 'Last update: January 12, 2024');

    cy.get('button[mat-raised-button]').should('exist');
  });
});
