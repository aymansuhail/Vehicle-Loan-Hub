package com.examly.springapp.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.examly.springapp.model.LoanApplication;

@Repository
public interface LoanApplicationRepo extends JpaRepository<LoanApplication,Long>{
    // LoanApplication  findByLoanApplicationIdOrAddressAndSubmissionDate(Long loanApplicationId,String address,LocalDate submissionDate);
   
    @Query("select la from LoanApplication la where la.user.userId=?1 and la.loan.loanId=?2")
    LoanApplication findByUserIdAndLoanId(Integer userId, Long loanId);
    @Query("select la from LoanApplication la where la.user.userId=?1")
    List<LoanApplication> findApplicationsByUserId(int userId);
}
