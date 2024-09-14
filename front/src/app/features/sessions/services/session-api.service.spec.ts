import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { SessionApiService } from './session-api.service';
import { Session } from '../interfaces/session.interface';
import { expect as jestExpect } from '@jest/globals';

describe('SessionApiService Unit Tests', function() {
  let sessionApiSvc: SessionApiService;
  let httpCtrl: HttpTestingController;

  const mockSession: Session = {
    name: '',
    description: '',
    date: new Date(),
    teacher_id: 0,
    users: [],
  };

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [SessionApiService]
    });

    sessionApiSvc = TestBed.inject(SessionApiService);
    httpCtrl = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpCtrl.verify();
  });

  it('should create a new Session', function() {
    sessionApiSvc.create(mockSession).subscribe((response) => {
      jestExpect(response).toStrictEqual(mockSession);
    });

    const request = httpCtrl.expectOne('api/session');
    jestExpect(request.request.method).toBe('POST');
    request.flush(mockSession);
  });

  it('should update an existing Session', function() {
    const sessionId = '1';

    sessionApiSvc.update(sessionId, mockSession).subscribe((response) => {
      jestExpect(response).toStrictEqual(mockSession);
    });

    const request = httpCtrl.expectOne(`api/session/${sessionId}`);
    jestExpect(request.request.method).toBe('PUT');
    request.flush(mockSession);
  });

  it('should delete a Session', function() {
    const sessionId = '1';

    sessionApiSvc.delete(sessionId).subscribe();

    const request = httpCtrl.expectOne(`api/session/${sessionId}`);
    jestExpect(request.request.method).toBe('DELETE');
  });
});
