import { Pipe, PipeTransform } from '@angular/core';
import { LoanApplication } from '../models/loanapplication.model';

@Pipe({
  name: 'filter'
})
export class FilterPipe implements PipeTransform { 
  transform(loans: LoanApplication[], filterStatus: string): LoanApplication[] { 
    if (!loans || filterStatus === '') { 
      return loans; 
    } 
    return loans.filter(loan => loan.loanStatus.toString() === filterStatus); 
  }
}
