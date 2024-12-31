import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Loan } from '../models/loan.model';
import { LoanApplication } from '../models/loanapplication.model';

@Injectable({
  providedIn: 'root'
})
export class LoanService {
  // ddbdadbafcbdecbbdcdaadffeddbddafcfcc ayman
  loanUrl:string='https://8080-ddbdadbafcbdecbbdcdaadffeddbddafcfcc.premiumproject.examly.io/api'
  // eaddbbbccafeecadcdaadffeddbddafcfcc suman
  // loanUrl:string='https://8080-ddbdadbafcbdecbbdcdaadffeddbddafcfcc.premiumproject.examly.io/api' sanaullah
  // loanUrl:string='https://8080-eaddbbbccafeecadcdaadffeddbddafcfcc.premiumproject.examly.io/api'

  getAllLoans():Observable<Loan[]>{
    return this.http.get<Loan[]>(this.loanUrl+"/loan")
  }
  deleteLoan(loanId:number):Observable<any>{
    return this.http.delete(this.loanUrl+"/loan/"+loanId,{responseType: 'text'})
  }
  getLoanById(id:number):Observable<Loan>{
    return this.http.get<Loan>(this.loanUrl+"/loan/"+id)
  }
  addLoan(requestObject:Loan):Observable<Loan>{
    return this.http.post<Loan>(this.loanUrl+"/loan",requestObject)
  }
  updateLoan(id:number,requestObject:Loan):Observable<Loan>{
    return this.http.put<Loan>(this.loanUrl+"/loan/"+id,requestObject)
  }
  getAppliedLoans(userId:number):Observable<LoanApplication[]>{
    return this.http.get<LoanApplication[]>(this.loanUrl+"/loanapplication/user/"+userId)
  }
  deleteLoanApplication(loanId:number):Observable<any>{
    return this.http.delete(this.loanUrl+"/loanapplication/"+loanId, {responseType: 'text'})
  }
  addLoanApplication(data:LoanApplication):Observable<LoanApplication>{
    return this.http.post<LoanApplication>(this.loanUrl+"/loanapplication",data)
  }
  getAllLoanApplication():Observable<LoanApplication[]>{
    return this.http.get<LoanApplication[]>(this.loanUrl+"/loanapplication")
  }
  // updateLoanStatus(id:string,loanApplication:LoanApplication):Observable<LoanApplication>{
  //   return this.http.put<LoanApplication>(this.loanUrl+"/loanapplication/"+id,loanApplication)
  // }
  updateLoanStatus(id: string, status: number): Observable<LoanApplication> {
    return this.http.put<LoanApplication>(`${this.loanUrl}/loanapplication/${id}/status`, status);
}

  constructor(private http:HttpClient) { }
}
