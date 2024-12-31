import { Loan } from './loan.model';

describe('Loan Model', () => {

  fit('Frontend_Loan_model_should_create_an_instance', () => {
    // Create a sample Loan object
    const loan: Loan = {
      loanType: 'Home Loan',
      description: 'Loan for purchasing a house',
      interestRate: 3.5,
      maximumAmount: 500000
    };

    // Assertion to check if loan is truthy (not null or undefined)
    expect(loan).toBeTruthy();

    // Assertions to verify specific properties of the loan object
    expect(loan.loanId).toBeUndefined(); // loanId is optional, so it should be undefined
    expect(loan.loanType).toBe('Home Loan');
    expect(loan.description).toBe('Loan for purchasing a house');
    expect(loan.interestRate).toBe(3.5);
    expect(loan.maximumAmount).toBe(500000);
  });

});
