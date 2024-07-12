export const ADMIN_DETAILS = {
  token: 'jwt-admin',
  type: 'Bearer',
  id: 10,
  username: 'admin@studio.com',
  firstName: 'AdminFirst',
  lastName: 'AdminLast',
  admin: true,
  createdAt: '2024-01-12T15:33:42',
  updatedAt: '2024-01-12T15:33:42',
};

export const USER_DETAILS = {
  token: 'jwt-user',
  type: 'Bearer',
  id: 20,
  username: 'user@example.com',
  firstName: 'UserFirst',
  lastName: 'UserLast',
  admin: false,
  createdAt: '2024-01-12T15:33:42',
  updatedAt: '2024-01-12T15:33:42',
};

export const TEST_SESSION = {
  id: 100,
  name: 'Sample Session',
  date: '2024-02-14T10:00:00.000+00:00',
  teacher_id: 10,
  description: 'Sample description for the session',
  users: [20],
  createdAt: '2024-02-14T10:00:00',
  updatedAt: '2024-02-15T10:00:00',
};

export const SESSIONS_LIST = [TEST_SESSION];

export const EDITED_TEST_SESSION = {
  ...TEST_SESSION,
  name: 'Updated Session',
};

export const TEACHERS_LIST = [
  {
    id: 10,
    lastName: 'Doe',
    firstName: 'John',
    createdAt: '2024-02-10T08:00:00',
    updatedAt: '2024-02-10T08:00:00',
  },
  {
    id: 20,
    lastName: 'Smith',
    firstName: 'Jane',
    createdAt: '2024-02-10T08:00:00',
    updatedAt: '2024-02-10T08:00:00',
  },
];

export const interceptsSetup = () => {
  cy.intercept('GET', '/api/session', (req) => {
    req.reply(SESSIONS_LIST);
  });

  cy.intercept('POST', '/api/session', (req) => {
    SESSIONS_LIST.push(TEST_SESSION);
    req.reply(TEST_SESSION);
  });

  cy.intercept('GET', `/api/session/${TEST_SESSION.id}`, TEST_SESSION);

  cy.intercept('POST', `/api/session/${TEST_SESSION.id}/participate/${USER_DETAILS.id}`, { statusCode: 200, body: {} });

  cy.intercept('DELETE', `/api/session/${TEST_SESSION.id}/participate/${USER_DETAILS.id}`, { statusCode: 200, body: {} });

  cy.intercept('DELETE', `/api/session/${TEST_SESSION.id}`, (req) => {
    SESSIONS_LIST.splice(0, 1);
    req.reply(EDITED_TEST_SESSION);
  });

  cy.intercept('PUT', `/api/session/${TEST_SESSION.id}`, (req) => {
    SESSIONS_LIST.splice(0, 1, EDITED_TEST_SESSION);
    req.reply(EDITED_TEST_SESSION);
  });

  cy.intercept('GET', `/api/teacher`, TEACHERS_LIST);

  cy.intercept('GET', `/api/teacher/${TEACHERS_LIST[0].id}`, TEACHERS_LIST[0]);

  cy.intercept('DELETE', '/api/user');

  cy.intercept('POST', '/api/auth/login', (req) => {
    if (req.body.username === ADMIN_DETAILS.username) {
      req.reply(ADMIN_DETAILS);
    } else if (req.body.username === USER_DETAILS.username) {
      req.reply(USER_DETAILS);
    } else {
      req.reply({ statusCode: 401, body: {} });
    }
  });

  cy.intercept('GET', `/api/user/${ADMIN_DETAILS.id}`, (req) => {
    req.reply(ADMIN_DETAILS);
  });

  cy.intercept('GET', `/api/user/${USER_DETAILS.id}`, (req) => {
    req.reply(USER_DETAILS);
  });
};



///ACCOUNT TESTS

export const ADMIN_ACCOUNT_DETAILS = {
  token: 'jwt-admin',
  type: 'Bearer',
  id: 10,
  email: 'admin@studio.com',
  firstName: 'AdminFirst',
  lastName: 'ADMINLAST',
  admin: true,
  createdAt: '2024-01-12T15:33:42',
  updatedAt: '2024-01-12T15:33:42',
};

export const USER_ACCOUNT_DETAILS = {
  token: 'jwt-user',
  type: 'Bearer',
  id: 20,
  email: 'user@user.com',
  firstName: 'UserFirst',
  lastName: 'USERLAST',
  admin: false,
  createdAt: '2024-01-12T15:33:42',
  updatedAt: '2024-01-12T15:33:42',
};

export const interceptApiCalls = () => {
  cy.intercept('GET', '/api/session', []);
  cy.intercept('DELETE', '/api/user');
};

export const login = (accountDetails) => {
  cy.visit('/login');

  cy.intercept('POST', '/api/auth/login', accountDetails);

  cy.intercept('GET', `/api/user/${accountDetails.id}`, accountDetails);

  cy.get('input[formControlName=email]').type(accountDetails.email);
  cy.get('input[formControlName=password]').type('test!1234{enter}{enter}');

  cy.url().should('include', '/sessions');
};
