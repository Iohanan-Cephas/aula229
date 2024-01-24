package model.services;

import java.time.LocalDate;

import model.entities.Contract;
import model.entities.Installment;

public class ContractService {
	private OnlinePaymentService onlinePaymentService;
	
	public ContractService(OnlinePaymentService onlinePaymentService) {
		this.onlinePaymentService = onlinePaymentService;
	}
	
	public void processContract(Contract contract, Integer months) {
		double basicQuote = contract.getTotalValue() / months;
		
		for(int i = 0; i < months; i++) {
			LocalDate dueDate = contract.getDate().plusMonths(i+1);
			double interest = onlinePaymentService.interest(basicQuote, i+1); 
			double fee = onlinePaymentService.paymentFee(basicQuote + interest);
			double amount = interest + fee + basicQuote;
			
			contract.getInstallments().add(new Installment(dueDate, amount));
		}
	}
}
