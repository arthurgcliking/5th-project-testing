import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { MatToolbarModule } from '@angular/material/toolbar';
import { RouterTestingModule } from '@angular/router/testing';
import { expect } from '@jest/globals';
import { of } from 'rxjs';

import { AppComponent } from './app.component';

describe('AppComponent', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        HttpClientModule,
        MatToolbarModule
      ],
      declarations: [
        AppComponent
      ],
    }).compileComponents();
  });

  it('should create the app', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });

  it('should render app title', () => {
    const fixture = TestBed.createComponent(AppComponent);
    fixture.detectChanges();
    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.querySelector('mat-toolbar span')?.textContent).toContain('Yoga app');
  });

  it('should render navigation links for logged-out users', () => {
    const fixture = TestBed.createComponent(AppComponent);
    fixture.detectChanges();
    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.querySelector('span[routerLink="login"]')?.textContent).toContain('Login');
    expect(compiled.querySelector('span[routerLink="register"]')?.textContent).toContain('Register');
  });

  it('should render navigation links for logged-in users', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;

    // Mock $isLogged method to return true as an observable
    jest.spyOn(app, '$isLogged').mockReturnValue(of(true));

    fixture.detectChanges();
    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.querySelector('span[routerLink="sessions"]')?.textContent).toContain('Sessions');
    expect(compiled.querySelector('span[routerLink="me"]')?.textContent).toContain('Account');
    expect(compiled.querySelector('span.link:not([routerLink])')?.textContent).toContain('Logout');
  });
});
