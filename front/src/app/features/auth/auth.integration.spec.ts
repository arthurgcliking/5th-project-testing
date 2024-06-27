import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { AuthService } from '../auth/services/auth.service';
import { SessionService } from '../../services/session.service';
import { LoginRequest } from '../auth/interfaces/loginRequest.interface';
import { RegisterRequest } from '../auth/interfaces/registerRequest.interface';
import { SessionInformation } from '../../interfaces/sessionInformation.interface';


describe('Authentication flow integration tests', () => {
  let loginComponent: LoginComponent;
  let registerComponent: RegisterComponent;
  let authService: AuthService;
  let sessionService: SessionService;
  let httpMock: HttpTestingController;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LoginComponent, RegisterComponent],
      imports: [ReactiveFormsModule, RouterTestingModule, HttpClientTestingModule],
      providers: [AuthService, SessionService]
    }).compileComponents();

    authService = TestBed.inject(AuthService);
    sessionService = TestBed.inject(SessionService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  beforeEach(() => {
    const loginFixture = TestBed.createComponent(LoginComponent);
    loginComponent = loginFixture.componentInstance;
    loginFixture.detectChanges();

    const registerFixture = TestBed.createComponent(RegisterComponent);
    registerComponent = registerFixture.componentInstance;
    registerFixture.detectChanges();
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should register a user and then login', () => {
    const registerRequest: RegisterRequest = { email: 'test@example.com', firstName: 'John', lastName: 'Doe', password: 'password' };
    const loginRequest: LoginRequest = { email: 'test@example.com', password: 'password' };
    const sessionInformation: SessionInformation = { id: 1, username: 'testuser', firstName: 'John', lastName: 'Doe', admin: false, token: 'testtoken', type: 'Bearer' };

    // Register user
    registerComponent.form.setValue(registerRequest);
    registerComponent.submit();

    const registerReq = httpMock.expectOne('api/auth/register');
    expect(registerReq.request.method).toBe('POST');
    expect(registerReq.request.body).toEqual(registerRequest);
    registerReq.flush(null);

    // Login user
    loginComponent.form.setValue(loginRequest);
    loginComponent.submit();

    const loginReq = httpMock.expectOne('api/auth/login');
    expect(loginReq.request.method).toBe('POST');
    expect(loginReq.request.body).toEqual(loginRequest);
    loginReq.flush(sessionInformation);

    expect(sessionService.sessionInformation).toEqual(sessionInformation);
  });
});
