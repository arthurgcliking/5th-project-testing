import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { MatSnackBarModule, MatSnackBar } from '@angular/material/snack-bar';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';
import { SessionService } from '../../../../services/session.service';
import { DetailComponent } from './detail.component';
import { SessionApiService } from '../../services/session-api.service';
import { TeacherService } from '../../../../services/teacher.service';
import { ActivatedRoute } from '@angular/router';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';

describe('DetailComponent', () => {
  let component: DetailComponent;
  let fixture: ComponentFixture<DetailComponent>;
  let sessionService: SessionService;
  let sessionApiService: SessionApiService;
  let teacherService: TeacherService;

  const mockSessionInformation = {
    admin: true,
    id: 1,
    token: '12345',
    type: 'Bearer',
    username: 'testuser',
    firstName: 'Test',
    lastName: 'User',
  };

  const mockSession = {
    id: 1,
    name: 'Yoga Session',
    description: 'A relaxing yoga session',
    date: new Date('2023-06-25'),
    teacher_id: 1,
    users: [1],
    createdAt: new Date('2023-01-01'),
    updatedAt: new Date('2023-06-01'),
  };

  const mockTeacher = {
    id: 1,
    firstName: 'John',
    lastName: 'Doe',
    // other properties of the teacher
  };

  const mockSessionService = {
    sessionInformation: mockSessionInformation,
    $isLogged: jest.fn().mockReturnValue(of(true)),
    logIn: jest.fn(),
    logOut: jest.fn(),
  };

  const mockSessionApiService = {
    detail: jest.fn().mockReturnValue(of(mockSession)),
    delete: jest.fn().mockReturnValue(of({})),
    participate: jest.fn().mockReturnValue(of(void 0)),
    unParticipate: jest.fn().mockReturnValue(of(void 0))
  };

  const mockTeacherService = {
    detail: jest.fn().mockReturnValue(of(mockTeacher)),
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        HttpClientModule,
        MatSnackBarModule,
        ReactiveFormsModule,
        BrowserAnimationsModule,
        MatCardModule,
        MatIconModule
      ],
      declarations: [DetailComponent],
      providers: [
        { provide: SessionService, useValue: mockSessionService },
        { provide: SessionApiService, useValue: mockSessionApiService },
        { provide: TeacherService, useValue: mockTeacherService },
        { provide: MatSnackBar, useValue: {} },
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: {
                get: jest.fn().mockReturnValue('1'),
              },
            },
          },
        },
      ],
    }).compileComponents();

    sessionService = TestBed.inject(SessionService);
    sessionApiService = TestBed.inject(SessionApiService);
    teacherService = TestBed.inject(TeacherService);
    fixture = TestBed.createComponent(DetailComponent);
    component = fixture.componentInstance;

    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should fetch session details on init', () => {
    component.ngOnInit();
    expect(sessionApiService.detail).toHaveBeenCalledWith('1');
    expect(teacherService.detail).toHaveBeenCalledWith('1');
    expect(component.session).toEqual(mockSession);
    expect(component.teacher).toEqual(mockTeacher);
  });

  it('should display session details', () => {
    component.session = mockSession;
    fixture.detectChanges();
    const compiled = fixture.nativeElement as HTMLElement;
    //console.log(compiled.innerHTML); // Add this line for debugging
    expect(compiled.querySelector('.session-name')?.textContent).toContain('Yoga Session');
    expect(compiled.querySelector('.session-users')?.textContent).toContain('1 attendees');
    expect(compiled.querySelector('.session-date')?.textContent).toContain('June 25, 2023');
    expect(compiled.querySelector('.session-description')?.textContent).toContain('A relaxing yoga session');
    expect(compiled.querySelector('.session-created')?.textContent).toContain('January 1, 2023');
    expect(compiled.querySelector('.session-updated')?.textContent).toContain('June 1, 2023');
  });

  it('should display admin actions if user is admin', () => {
    component.session = mockSession;
    component.isAdmin = true;
    fixture.detectChanges();
    const compiled = fixture.nativeElement as HTMLElement;
    //console.log(compiled.innerHTML); // Add this line for debugging
    expect(compiled.querySelector('.admin-actions')).toBeTruthy();
  });

  it('should not display admin actions if user is not admin', () => {
    component.session = { ...mockSession, users: [] };
    component.isAdmin = false;
    fixture.detectChanges();
    const compiled = fixture.nativeElement as HTMLElement;
    //console.log(compiled.innerHTML); // Add this line for debugging
    expect(compiled.querySelector('.admin-actions')).toBeFalsy();
  });

  it('should call delete method on delete', () => {
    const spy = jest.spyOn(sessionApiService, 'delete').mockReturnValue(of({}));
    component.delete();
    expect(spy).toHaveBeenCalledWith('1');
  });

  it('should call participate method on participate', () => {
    const spy = jest.spyOn(sessionApiService, 'participate').mockReturnValue(of(void 0));
    component.participate();
    expect(spy).toHaveBeenCalledWith('1', '1');
  });

  it('should call unParticipate method on unParticipate', () => {
    const spy = jest.spyOn(sessionApiService, 'unParticipate').mockReturnValue(of(void 0));
    component.unParticipate();
    expect(spy).toHaveBeenCalledWith('1', '1');
  });
});
