import { Injectable, EventEmitter } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../models/user.model';
import { Login } from '../models/login.model';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  currentUser: any;
  public userRoleChanged: EventEmitter<string> = new EventEmitter<string>();
  apiUrl: string = 'https://8080-ddbdadbafcbdecbbdcdaadffeddbddafcfcc.premiumproject.examly.io/api';

  constructor(private http: HttpClient , private router : Router) {
    this.currentUser = JSON.parse(localStorage.getItem('currentUser'));
  }
  currentUserdetails():Observable<any>{
     return this.currentUser 
  }

  register(user: User): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/register`, user);
  }

  login(login: Login): Observable<any> {
    return new Observable(observer => {
      this.http.post<any>(`${this.apiUrl}/login`, login).subscribe(
        response => {
          if (response && response.token) {
            localStorage.setItem('currentUser', JSON.stringify(response));
            this.currentUser = response;
            this.userRoleChanged.emit(this.currentUser.userRole); // Emit role change
          }
          observer.next(response);
          observer.complete();
        },
        error => {
          observer.error(error);
        }
      );
    });
  }

  logout() {
    localStorage.clear();
    this.currentUser = null;
    this.userRoleChanged.emit(null); // Emit null role on logout
  }

  getToken(): string | null {
    return this.currentUser ? this.currentUser.token : null;
  }

  getAuthorizationHeader(): HttpHeaders {
    return new HttpHeaders({
      'Authorization': `Bearer ${this.getToken()}`
    });
  }

  getUserRole(): string | null {
    return this.currentUser ? this.currentUser.userRole : null;
  }

  getUserId():number | null{
    return this.currentUser ? this.currentUser.userId : null;
  }

  getUserName():string | null{
    return this.currentUser ? this.currentUser.username : null;
  }
  isLoggedIn(): boolean {
    return !!this.currentUser;
  }
  redirectToError(){
    this.router.navigate(["/error"]);
  }
  }
