import { LoanApplication } from './loanapplication.model';

describe('LoanApplication Model', () => {

  fit('Frontend_LoanApplication_model_should_create_an_instance', () => {
    // Create a sample LoanApplication object
    const loanApplication: LoanApplication = {
      submissionDate: '2024-07-02',
      income: 75000,
      model: 'Model X',
      purchasePrice: 100000,
      loanStatus: 1,
      address: '1234 Elm Street',
      file: 'document.pdf'
    };

    expect(loanApplication).toBeTruthy();

    expect(loanApplication.loanApplicationId).toBeUndefined(); 
    expect(loanApplication.loanStatus).toBe(1);
    expect(loanApplication.address).toBe('1234 Elm Street');
    expect(loanApplication.file).toBe('document.pdf');
  });

});