/**
* Proj5.java
* Alex Masson / Thursday / 4:30 - 6:30 / Dan Wagner
*
* This program is a Mortgage Calculator for Big 12 Bank Midwest.
*/


import java.util.*;
import java.text.*;
import java.io.*;
public class Proj5 {

  public static void main(String[] args) throws IOException{
    Scanner in = new Scanner(System.in);
    String input;

    System.out.println("Big 12 Bank Midwest Mortgage Calculation Software");
    System.out.println("Developed by Alex Masson");
    System.out.println(" ");
    System.out.print("Please enter the file name to be used for output (without .txt): ");
      input = in.nextLine();
      String fileName = input;
      PrintWriter output = new PrintWriter(new FileWriter(fileName + ".txt",true));
    System.out.println(" ");
    System.out.println(" ");
    System.out.println("Please choose from the following choices below:");
    System.out.println("\t1) Promotional Loan ($150,000 @ 3.95% for 25 years) ");
    System.out.println("\t2) Unique Loan (Enter in loan values) ");
    System.out.println("\t3) Quit (Exit the Program) ");
    System.out.println(" ");
while(true){
    System.out.print("\tPlease enter your selection(1-3): ");
      int choice = in.nextInt();

        while (choice > 3 || choice < 1){
          System.out.print("\t\tInvalid Choice. Please Select 1, 2, or 3: ");
          choice = in.nextInt();
        }

        System.out.println(" ");

        if (choice == 1){
          System.out.println("PROMOTIONAL LOAN...:");

          String customerNumber = getCustomerNumber();

          int principal = 150000;
          double interest = 3.95;
          int years = 25;

          double cMP = calcMonthlyPayment(principal, interest, years);

          double cTP = calcTotalPayment(cMP, years);

          displayPayments(customerNumber, principal, years, interest, cMP, cTP);

          output = new PrintWriter(new FileWriter(fileName + ".txt",true));

          writeToFile(output, customerNumber, principal, years, interest, cMP, cTP);

        } else if (choice == 2){

          System.out.println("UNIQUE LOAN...:");

          String customerNumber = getCustomerNumber();

          int principal = getLoanAmount();

          double interest = getInterestRate();

          int years = getLoanTerm();

          double cMP = calcMonthlyPayment(principal, interest, years);

          double cTP = calcTotalPayment(cMP, years);

          displayPayments(customerNumber, principal, years, interest, cMP, cTP);
          output = new PrintWriter(new FileWriter(fileName + ".txt",true));
          writeToFile(output, customerNumber, principal, years, interest, cMP, cTP);
        } else {
          System.out.println("Bye");
          System.exit(0);
          break;
        }

}//end while
  }//end main


  /*This method, which is declared as getCustomerNumber,
  *is used to not only accept input from the user, but also validates
  *the user identification number which is often referred to in this
  *program as the "Customer Number", which is how the customers are distinguished
  *in the output file. This method, when called, prompts the user for the customer
  *number, then sends the number through the first while loop, to see whether or
  *not the customer number meets length requirements. After it clears the first
  *while loop, then the number is passed through the second while loop. In that loop,
  *each individual character in the customer number is converted into a ascii numeric
  *umber, which is how I distinguish whether or not it is a valid character. The
  *method then returns a valid customer number.
  *
  * No parameters for this method.
  *
  * @return The string returned is a valid customer number. */
  public static String getCustomerNumber(){

      Scanner in = new Scanner(System.in);
      String input;
      char c;
      boolean oneWayFlag = false;
      System.out.print("Enter the customer number (6 characters and/or digits): ");
      String customerNumber = in.nextLine();

      while (true){
        if(customerNumber.length() > 6 || customerNumber.length() < 6) {
          System.out.print("\tValid customer numbers are 6 digit characters and/or numbers");
          System.out.print("\tEnter the customer number: ");
          customerNumber = in.nextLine();
        } else {
          break;
        }//end if
      }//end while

      while (true){

          for (int i = 0; i < customerNumber.length(); i++){
            c = customerNumber.charAt(i);
            if((c>=32 && c<=47)||(c>=58 && c<=64)||(c>=91 && c<=96)||(c>=123 && c<=126)){
              System.out.print("\tValid customer numbers are 6 digit characters and/or numbers");
              System.out.print("\tEnter the customer number: ");
              customerNumber = in.nextLine();
            }
          }//end if
        break;
      }//end while

        return(customerNumber);

  }//end getCustomerNumber


  /**
  * This method prompts the user for an interest rate, then validates it *
  * no parameters
  *
  * @return A valid interest rate, following the rules set out in the project guide */
  public static double getInterestRate(){
    Scanner in = new Scanner(System.in);
    String input;
    System.out.print("Enter yearly interest rate (Ex: 5.25): ");
    double interest = in.nextDouble();
    while (true){
      if(interest < 2.00 || interest > 9.00) {
        System.out.print("\tValid interst rates are between 2 and 9 percent");
        System.out.print("\tEnter the interest rate: ");
        interest = in.nextDouble();
      } else {
        break;
      }//end if
    }//end while
    return interest;
  }//end getInterestRate


  /**
  * This method prompts the user for a loan term then validates it *
  * no parameters
  *
  * @return A valid loan term, which is either 5,10,15,20,25 */
  public static int getLoanTerm(){
    Scanner in = new Scanner(System.in);
    String input;
    System.out.print("Enter loan term (Ex: 5) : ");
    int term = in.nextInt();
    while (true){
      if(term != 5 && term != 10 &&term != 15 &&term != 20 && term != 25) {
        System.out.print("\tValid loan terms are 5, 10, 15, 20, and 25 years");
        System.out.print("\tEnter the loan term: ");
        term = in.nextInt();
      } else {
        break;
      }//end if
    }//end while
    return term;
  }//end getLoanTerm


  /**
  * A method which prompts the user for a loan amount then validates it *
  * no parameters
  *
  * @return A valid loan amount, between the amount of $50k and $1 million */
  public static int getLoanAmount(){
    Scanner in = new Scanner(System.in);
    String input;
    System.out.print("Enter loan amount without $ or commas (Ex: 75500): ");
    int loan = in.nextInt();
    while (true){
      if(loan < 50000 || loan > 1000000) {
        System.out.print("\tValid loans are between $50,000 and $1,000,000");
        System.out.print("\tEnter the loan amount: ");
        loan = in.nextInt();
      } else {
        break;
      }//end if
    }//end while
    return loan;
  }//end getLoanAmount


  /**
  * This method calculates the monthly payment for the mortgage, which includes monthly interest rate.
  * This was not fun to figure out
  * @param principal, the principal is the amount of the loan
  * @param interest, the interest is the yearly interest on the loan
  * @param years, years is the amount of years the loan will last
  * @return the returned value is the monthly payment, exactly to the specification of the project*/
  public static double calcMonthlyPayment(int principal, double interest, int years){
    int principal1 = principal;
    double interest1 = interest;
    int years1 = years;
    double exponent = 0;
    double monthlyPayment = 0;
    double top = 0;
    double bottom = 0;
    double divide = 0;
    double monthlyRate = 0;
    int months = years1 * 12;
    interest1 /= 100.0;
    monthlyRate = interest1/ 12.0;
    monthlyPayment = (principal1*monthlyRate) / (1-Math.pow(1+monthlyRate, -months));
    return (monthlyPayment);

  }//end calcMonthlyPayment


  /**
  * This method calculates the total payment on the loan *
  * @param cMP cMP stands for "Calculated Monthly Payment" and is the calculated monthly payment from the previous method.
  * @param years, Years is the amount of years the loan will last
  * @return The total payment on the loan after all is said and done */

  public static double calcTotalPayment(double cMP, int years){
    double cMP1 = cMP;
    int years1 = years;
    int months = years1*12;
    double cTP1 = months * cMP1;
    return cTP1;
  }//end calcTotalPayment

  /**
  * This method displays all of the relevant information to the loan *
  * @param customerNumber the customer number used to indentify the customer
  * @param principal the amount invested into the loan
  * @param years how many years the loan will go for
  * @param interest the interest rate on the loan
  * @param cMP the calculated monthly payment
  * @param cTP the calculated total payment
  *
  * @return nothing is returned */

  public static void displayPayments(String customerNumber, int principal, int years, double interest, double cMP,double cTP){
    System.out.println("Customer Number: " + customerNumber);
    System.out.println("Loan Amount: " + principal);
    System.out.println("Loan Term: " + years);
    System.out.printf("Interest Rate: %.2f",interest);
    System.out.println("%");
    String output = String.format("Monthly Payment: $%.2f\n", + cMP);
    String output1 = String.format("Total Payment: $%.2f\n", + cTP);
    System.out.printf(output);
    System.out.printf(output1);
    System.out.println(" ");
  }//end displayPayments

  /**
  * This method writes all of the relevant information to the loan into a file of the users choice*
  * @param output the name of the print writer used to write to the file
  * @param customerNumber the customer number used to indentify the customer
  * @param principal the amount invested into the loan
  * @param years how many years the loan will go for
  * @param interest the interest rate on the loan
  * @param cMP the calculated monthly payment
  * @param cTP the calculated total payment
  *
  * @return nothing is returned */

  public static void writeToFile(PrintWriter output,String customerNumber, int principal, int years, double interest, double cMP,double cTP)throws IOException{

    output.println("Customer Number: " + customerNumber);
    output.println("Loan Amount: " + principal);
    output.println("Loan Term: " + years);
    output.printf("Interest Rate: %.2f",interest);
    output.println("%");
    String output2 = String.format("Monthly Payment: $%.2f\n", + cMP);
    String output1 = String.format("Total Payment: $%.2f\n", + cTP);
    output.printf(output2);
    output.printf(output1);
    output.println(" ");

    output.close();

  }//end writeToFile

}//end class
