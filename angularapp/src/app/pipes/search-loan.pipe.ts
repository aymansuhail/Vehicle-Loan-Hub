import { Pipe, PipeTransform } from '@angular/core';
import { Loan } from 'src/app/models/loan.model';

@Pipe({
  name: 'searchLoan'
})
export class SearchLoanPipe implements PipeTransform {

  transform(loans: Loan[], searchTerm: string): Loan[] {
    if (!loans || !searchTerm) {
      return loans;
    }
    return loans.filter(loan =>
      loan.loanType.toLowerCase().indexOf(searchTerm.toLowerCase()) !== -1);
  }

}
