package com.mindhub.homebanking;
import com.mindhub.homebanking.Repository.*;
import com.mindhub.homebanking.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.mindhub.homebanking.models.TransactionType.CREDIT;
import static com.mindhub.homebanking.models.TransactionType.DEBIT;

@SpringBootApplication
public class HomebankingApplication {

	@Autowired
	private PasswordEncoder passwordEnconder;



	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);

	}

	@Bean
	public CommandLineRunner initData(ClientRepository repositoryClient, AccountRepository repositoryAccount, TransactionRepository repositoryTransaction, LoanRepository repositoryLoan, ClientLoanRepository repositoryClientLoan, CardRepository repositoryCard) {
		return (args) -> {
			// save a couple of customers
			Client clienteuno = new Client("Melba", "Morel", "melba@mindhub.com", passwordEnconder.encode("pepito"));
			Client clientedos = new Client("Alicia Susana", "Diaz", "ali5040@mindhub.com", passwordEnconder.encode("hermosa"));
			Client admin1 = new Client ("Ricky Oscar", "Flores", "adminElJefe@bank.com", passwordEnconder.encode("dameuncartucho"));
			repositoryClient.save(clienteuno);
			repositoryClient.save(clientedos);
			repositoryClient.save(admin1);

			Account cuentauno = new Account("VIN001", LocalDateTime.now(), 5000, clienteuno,false, AccountType.CURRENT);
			Account cuentados = new Account("VIN002", LocalDateTime.now().plusDays(1), 7500, clienteuno, false, AccountType.SAVING);
			Account cuentaPrueba = new Account ("VINPrueba", LocalDateTime.now(), 180000, clienteuno, false, AccountType.CURRENT);
			Account cuentatres = new Account ("VIN003", LocalDateTime.now().plusDays(3), 9300, clientedos, false, AccountType.CURRENT);
			Account cuentacuatro = new Account("VIN004", LocalDateTime.now().minusDays(-5), 573, clientedos, false, AccountType.SAVING);
			repositoryAccount.save(cuentauno);
			repositoryAccount.save(cuentados);
			repositoryAccount.save(cuentatres);
			repositoryAccount.save(cuentacuatro);
			repositoryAccount.save(cuentaPrueba);

            Transaction transaction1 = new Transaction (20, "Pending balance", LocalDateTime.now(), CREDIT, cuentauno, 4000, false);
			Transaction transaction2 = new Transaction(678, "HBO Max service", LocalDateTime.now(), DEBIT, cuentauno, 5000, false);
			Transaction transaction3 = new Transaction(11600, "May Rent", LocalDateTime.now(), CREDIT, cuentauno, 5000, false);
			Transaction transaction4 = new Transaction(2165, "Bullanga", LocalDateTime.now(), DEBIT, cuentauno, 5000, false );
			Transaction transaction5 = new Transaction(1964, "Supermarket", LocalDateTime.now(), DEBIT, cuentauno, 5000, false );
			Transaction transaction6 = new Transaction(500, "Not listed", LocalDateTime.now(), DEBIT, cuentauno, 5000, false );
			Transaction transaction7 = new Transaction(2500, "Received transfer", LocalDateTime.now(), CREDIT, cuentauno, 5000, false );
			repositoryTransaction.save(transaction1);
			repositoryTransaction.save(transaction2);
			repositoryTransaction.save(transaction3);
			repositoryTransaction.save(transaction4);
			repositoryTransaction.save(transaction5);
			repositoryTransaction.save(transaction6);
			repositoryTransaction.save(transaction7);

			Transaction transaction8 = new Transaction (5200, "Not listed", LocalDateTime.now(), DEBIT, cuentados, 4000, false);
			Transaction transaction9 = new Transaction(35000, "Monthly fee", LocalDateTime.now(), CREDIT, cuentados, 4000, false);
			Transaction transaction10 = new Transaction(15000, "Monthly fee", LocalDateTime.now(), CREDIT, cuentados, 4000, false );
			Transaction transaction11 = new Transaction(320, "Not listed", LocalDateTime.now(), DEBIT, cuentados, 4000, false );
			Transaction transaction12 = new Transaction(23900, "Received transfer", LocalDateTime.now(), CREDIT, cuentados, 4000, false );
			Transaction transaction13 = new Transaction(9632, "Supermarket", LocalDateTime.now(), DEBIT, cuentados, 4000, false );
			Transaction transaction14 = new Transaction(400, "Monthly fee", LocalDateTime.now(), CREDIT, cuentados, 4000, false );
			repositoryTransaction.save(transaction8);
			repositoryTransaction.save(transaction9);
			repositoryTransaction.save(transaction10);
			repositoryTransaction.save(transaction11);
			repositoryTransaction.save(transaction12);
			repositoryTransaction.save(transaction13);
			repositoryTransaction.save(transaction14);


			Loan loanHipotecario = new Loan ("Hipotecario", 500000, List.of(12,24,36,48,60), 1.30);
			Loan loanPersonal = new Loan ("Personal",  100000, List.of(6,12,24), 1.15);
			Loan loanAutomotriz = new Loan ("Automotriz", 300000, List.of(6,12,24,36), 1.20);
			repositoryLoan.save(loanHipotecario); //1
			repositoryLoan.save(loanPersonal); //2
			repositoryLoan.save(loanAutomotriz); //3

			ClientLoan clientLoan1 = new ClientLoan (400000, 60, clienteuno, loanHipotecario);
			ClientLoan clientLoan2 = new ClientLoan (50000, 12, clienteuno, loanPersonal);
			ClientLoan clientLoan3 = new ClientLoan (100000, 24, clientedos, loanPersonal);
			ClientLoan clientLoan4 = new ClientLoan (200000, 36, clientedos, loanAutomotriz);
			repositoryClientLoan.save(clientLoan1);
			repositoryClientLoan.save(clientLoan2);
			repositoryClientLoan.save(clientLoan3);
			repositoryClientLoan.save(clientLoan4);

			Card card1 = new Card (CardType.DEBIT, ColorType.GOLD, "4565 7895 3215 4567", 615, LocalDate.now(), LocalDate.now().plusYears(5), clienteuno, false);
			Card card2 = new Card (CardType.CREDIT, ColorType.TITANIUM,  "4562 3515 7894 6532", 895, LocalDate.now(), LocalDate.now().plusYears(5), clienteuno, false);
			Card card3 = new Card (CardType.CREDIT, ColorType.SILVER, "7815 3425 9546 8575", 412, LocalDate.now(), LocalDate.now().plusYears(2), clienteuno, false);

			repositoryCard.save(card1);
			repositoryCard.save(card2);
			repositoryCard.save(card3);


		};
	}




}
