
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Feedback } from '../models/feedback.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FeedbackService {
  apiUrl:string='https://8080-ddbdadbafcbdecbbdcdaadffeddbddafcfcc.premiumproject.examly.io/api/feedback'
  constructor(private http:HttpClient) { }
  sendFeedback(feedback:Feedback):Observable<Feedback>{
    return this.http.post<Feedback>(this.apiUrl,feedback);
  }
  
  getAllFeedbacksByUserId(userId:number):Observable<Feedback[]>{
    return this.http.get<Feedback[]>(this.apiUrl+"/user/"+userId);
  }

  deleteFeedback(feedbackId:number):Observable<any>{
    return this.http.delete(this.apiUrl+"/"+feedbackId, {responseType: 'text'});
  }

  getFeedbacks():Observable<Feedback[]>{
    return this.http.get<Feedback[]>(this.apiUrl);
  }
  
  
}
