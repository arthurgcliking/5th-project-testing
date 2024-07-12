import { ADMIN_DETAILS, TEST_SESSION, TEACHERS_LIST, interceptsSetup } from '../../utils';

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
