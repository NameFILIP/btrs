package com.infinitiessoft.btrs.custom;

//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Random;
//
//import org.jboss.seam.annotations.In;
//import org.jboss.seam.annotations.Logger;
//import org.jboss.seam.annotations.Name;
//import org.jboss.seam.international.StatusMessages;
//import org.jboss.seam.log.Log;
//
//import com.infinitiessoft.btrs.action.ExpenseHome;
//import com.infinitiessoft.btrs.action.ExpenseTypeList;
//import com.infinitiessoft.btrs.action.ReportHome;
//import com.infinitiessoft.btrs.action.RoleList;
//import com.infinitiessoft.btrs.action.UserList;
//import com.infinitiessoft.btrs.enums.StatusEnum;
//import com.infinitiessoft.btrs.custom.ExpenseAmountCalculator;
//import com.infinitiessoft.btrs.model.Expense;
//import com.infinitiessoft.btrs.model.ExpenseType;
//import com.infinitiessoft.btrs.model.Report;
//import com.infinitiessoft.btrs.model.User;


//@Name("reportsGenerator")
public class GenerateRandomReports {
	
//	@In(create = true)
//	ReportHome reportHome;
//
//	@In(create = true)
//	RoleList roleList;
//
//	@In(create = true)
//	UserList userList;
//
//	@In(create = true)
//	ExpenseHome expenseHome;
//
//	@In(create = true)
//	ExpenseTypeList expenseTypeList;
//
//	@Logger Log log;
//
//	@In(create = true)
//	ExpenseAmountCalculator expenseAmountCalculator;
//
//	public void generateReports(int amount)	{
//
//		log.debug("Generating #0 reports", amount);
//
//		List<User> accountants = roleList.getAccountants();
//		List<User> users = userList.getResultList();
//		users.removeAll(accountants);
//
//		List<ExpenseType> expenseTypes = expenseTypeList.getResultList();
//
//		StatusEnum[] statuses = StatusEnum.values();
//
//		Random random = new Random();
//
//		log.debug("Initial data: accountants: #0, users: #1, expenseTypes: #2, statuses: #3", accountants, users, expenseTypes, statuses);
//
//		List<Date> randomDates = new ArrayList<Date>();
//		for (int i = 0; i < amount; i++) {
//
//			long days = random.nextInt(365);
//			long hours = random.nextInt(24);
//			long minutes = random.nextInt(60);
//			long seconds = random.nextInt(60);
//			long milliseconds = random.nextInt(1000);
//			long oneYear = days * hours * minutes * seconds * milliseconds;
//			Date d = new Date(System.currentTimeMillis() - 5 * oneYear);
//			log.debug("Random date: #0", d);
//			randomDates.add(d);
//		}
//		log.debug("List of random dates: #0", randomDates);
//
//
//		for (int i = 0; i < amount; i++) {
//
//			log.debug("Generating report number #0", i);
//
//			int userIndex = random.nextInt(users.size());
//			int accountantIndex = random.nextInt(accountants.size());
//			int statusIndex = random.nextInt(statuses.length);
//
//			User user = users.get(userIndex);
//			User accountant = accountants.get(accountantIndex);
//			StatusEnum status = statuses[statusIndex];
//			Date date = randomDates.get(i);
//			String reportName = "Report-" + i;
//
//			Report report = reportHome.getInstance();
//			report.setOwner(user);
//			report.setReviewer(accountant);
//			report.setReason(reportName);
//			report.setRoute(reportName);
//			report.setStartDate(date);
//			report.setEndDate(date);
//			report.setComment(reportName);
//			report.setCreatedDate(date);
//			report.setCurrentStatus(status);
//
//			int expensesSize = random.nextInt(12);
//			List<Expense> expenses = new ArrayList<Expense>();
//
//			for (int j = 0; j < expensesSize; j++) {
//
//				int expenseTypeIndex = random.nextInt(expenseTypes.size());
//				ExpenseType expenseType = expenseTypes.get(expenseTypeIndex);
//				String expenseName = reportName + "-Expense-" + j;
//
//				int totalAmount = random.nextInt(1500);
//				int taxAmount = expenseAmountCalculator.calculateTax(totalAmount, expenseType.getTaxPercent());
//
//				Expense expense = new Expense();
//				expense.setExpenseType(expenseType);
//				expense.setReport(report);
//				expense.setComment(expenseName);
//				expense.setTotalAmount(totalAmount);
//				expense.setTaxAmount(taxAmount);
//
//				expenses.add(expense);
//			}
//
//			report.setExpenses(expenses);
//
//			log.debug("Report is about to be persisted: #0", report);
//			reportHome.generatorPersist();
//
//			reportHome.clearInstance();
//		}
//		StatusMessages.instance().clear();
//		StatusMessages.instance().add(amount + " reports generated");
//	}
//
}
