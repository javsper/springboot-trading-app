import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {BehaviorSubject, catchError, tap, throwError} from "rxjs";
import {User} from "../model/user.model";
import {environment} from "../../environments/environment";

export interface AuthResponse {
  username: string;
  token: string;
  authorities: string;
}

@Injectable({
  providedIn:"root"
})
export class LoginService {
  private loginUrl = environment.apiUrl+'login'
  user = new BehaviorSubject<User>(null)

  constructor(private http: HttpClient) {
  }

  login(username: string, password: string) {
    return this.http.post<AuthResponse>(this.loginUrl,
      {
        username: username, password: password
      }).pipe(catchError(this.handleError), tap(responeData => {
      this.handleResponse(responeData.username, responeData.token, responeData.authorities)
    }))
  }

  autoLogin() {
    const raw = localStorage.getItem('userData');
    if (!raw) {
      return;
    }
    try {
      const userData = JSON.parse(raw) as {
        username: string;
        token: string;
        authorities: string;
      };
      if (!userData?.username || !userData?.token) {
        localStorage.removeItem('userData');
        return;
      }
      this.user.next(
        new User(userData.username, userData.token, userData.authorities)
      );
    } catch {
      localStorage.removeItem('userData');
    }
  }

  logout() {
    this.user.next(null);
    localStorage.removeItem('userData');
  }

  private handleResponse(username: string, token: string, authorities: string) {

    const user = new User(username, token, authorities);
    this.user.next(user);
    localStorage.setItem('userData', JSON.stringify(user));
  }

  private handleError(errorRes: HttpErrorResponse) {
    return throwError(errorRes);
  }
}
