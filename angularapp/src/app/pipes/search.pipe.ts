import { Pipe, PipeTransform } from '@angular/core';
import { LoanApplication } from '../models/loanapplication.model';

@Pipe({
  name: 'search'
})
export class SearchPipe implements PipeTransform { 
  transform(loans: any[], searchTerm: string): any[] { 
    console.log('Loans:', loans); // Log the loans array
    console.log('Search Term:', searchTerm); // Log the search term

    if (!loans || !searchTerm) {
       return loans; 
    } 
    
    const filteredLoans = loans.filter(loan => 
      loan.loan.loanType.toLowerCase().includes(searchTerm.toLowerCase())
    );

    console.log('Filtered Loans:', filteredLoans); // Log the filtered loans array
    return filteredLoans;
  } 
}
