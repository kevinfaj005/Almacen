import { Injectable, signal, computed } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  private apiUrl = environment.apiUrl + '/login';
  private userSession = signal<any>(this.getUserFromStorage());

  session = computed(() => this.userSession());

  constructor(private http: HttpClient) {}

  private getUserFromStorage(): any {
    const session = localStorage.getItem('user_session');
    return session ? JSON.parse(session) : null;
  }

  login(correo: string, contrasena: string): Observable<any> {
    const body = { correo, contrasena };
    return this.http.post<any>(this.apiUrl, body).pipe(
      tap(res => {
        if (res.success) {
          const userData = res.data;
          localStorage.setItem('user_session', JSON.stringify(userData));
          this.userSession.set(userData);
        }
      })
    );
  }

  isLoggedIn(): boolean {
    return this.userSession() !== null;
  }

  logout(): void {
    localStorage.removeItem('user_session');
    this.userSession.set(null);
  }
}
