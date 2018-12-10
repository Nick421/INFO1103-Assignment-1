/**
 * info1103 - assignment 1
 * staff
 * 
 */

import java.util.Scanner;

public class CoffeeBot {

    public static void main(String[] args) {

        String name;
        String response;

        Scanner scan = new Scanner(System.in);

        // Parse command line arguments
        if (args.length == 0) {
          System.out.printf("No arguments. System terminating.\n");
          return;
        } else if (args.length == 1) {
          System.out.printf("Not enough arguments. System terminating.\n");
          return;
        } else if (args.length < 2) {
          System.out.printf("Too many arguments. System terminating.\n");
          return;
        }

        // Set prices
        double cupPrice = 2.00;
        double shotPrice = 1.00;

        // Set amount of supplies
        int cupSupply = Integer.parseInt(args[0]);
        int shotSupply = Integer.parseInt(args[1]);

        if (cupSupply < 0 && shotSupply < 0) {
          System.out.printf("Negative supply chain. System terminating.\n");
          return;
        } else if (cupSupply < 0) {
          System.out.printf("Negative supply of coffee cups. System terminating.\n");
          return;
        } else if (shotSupply < 0) {
          System.out.printf("Negative supply of coffee shots. System terminating.\n");
          return;
        }

        // Greet the customer
        System.out.printf("Hello, what's your name? ");
        name = scan.next();

        // Check whether to proceed with an order
        while (true) {
          System.out.printf("Would you like to order some coffee, %s? (y/n) ", name);
          response = scan.next();

          if (response.equals("y")) {
            System.out.printf("Great! Let's get started.\n");
            break;
          } else if (response.equals("n")) {
            System.out.printf("Come back next time, %s.\n", name);
            return;
          } else {
            System.out.printf("Invalid response. Try again.\n");
          }
        }

        int cups = 0;
        int shots = 0;

        System.out.printf("\n");
        System.out.printf("Order selection\n");
        System.out.printf("---------------\n");
        System.out.printf("\n");

        if (cupSupply == 1) {
          System.out.printf("There is %d coffee cup in stock and each costs $2.00.\n", cupSupply);
        } else {
          System.out.printf("There are %d coffee cups in stock and each costs $2.00.\n", cupSupply);
        }

        if (shotSupply == 1) {
          System.out.printf("There is %d coffee shot in stock and each costs $1.00.\n", shotSupply);
        } else {
          System.out.printf("There are %d coffee shots in stock and each costs $1.00.\n", shotSupply);
        }

        System.out.printf("\n");

        // Ask how many cups to order
        System.out.printf("How many cups of coffee would you like? ");
        cups = scan.nextInt();

        if (cups < 0) {
          System.out.printf("Does not compute. System terminating.\n");
          return;
        } else if (cups == 0) {
          System.out.printf("No cups, no coffee. Goodbye.\n");
          return;
        } else if (cups > cupSupply) {
          System.out.printf("Not enough stock. Come back later.\n");
          return;
        } else {
          cupSupply -= cups;
        }

        System.out.printf("\n");

        int[] coffees = new int[cups];

        // Select the order
        for (int c = 0; c < cups; c++) {
          while (true) {
            System.out.printf("How many coffee shots in cup %d? ", c + 1);
            int amount = scan.nextInt();
            if (amount < 0) {
              System.out.printf("Does not compute. Try again.\n");
            }
            else if (amount > shotSupply) {
              if (shotSupply == 1) {
                System.out.printf("There is only 1 coffee shot left. Try again.\n");
              } else {
                System.out.printf("There are only %d coffee shots left. Try again.\n", shotSupply);
              }
            } else {
              shots += amount;
              shotSupply -= amount;
              coffees[c] = amount;
              break;
            }
          }
        }

        System.out.printf("\n");
        System.out.printf("Order summary\n");
        System.out.printf("-------------\n");
        System.out.printf("\n");

        // Display the order summary
        for (int c = 0; c < cups; c++) {
          double price = cupPrice + coffees[c] * shotPrice;
          if (coffees[c] == 1) {
            System.out.printf("Cup %d has 1 shot and will cost $%.2f\n", c + 1, price);
          } else {
            System.out.printf("Cup %d has %d shots and will cost $%.2f\n", c + 1, coffees[c], price);
          }
        }

        double totalPrice = cups * cupPrice + shots * shotPrice;

        System.out.printf("\n");
        if (cups == 1) {
          System.out.printf("1 coffee to purchase.\n");
        } else {
          System.out.printf("%d coffees to purchase.\n", cups);
        }
        System.out.printf("Purchase price is $%.2f\n", totalPrice);

        // Check whether to confirm the order
        while (true) {
          System.out.printf("Proceed to payment? (y/n) ");
          response = scan.next();

          if (response.equals("y")) {
            break;
          } else if (response.equals("n")) {
            System.out.printf("Come back next time, %s.\n", name);
            return;
          } else {
            System.out.printf("Invalid response. Try again.\n");
          }
        }

        System.out.printf("\n");
        System.out.printf("Order payment\n");
        System.out.printf("-------------\n");
        System.out.printf("\n");

        // Process payment
        double totalTendered = 0;

        double[] tenderValues = {
          100.00, 50.00, 20.00, 10.00, 5.00, 2.00, 1.00, 0.50, 0.20, 0.10, 0.05
        };
		String[] tenderString = {
          "100.00", "50.00", "20.00", "10.00", "5.00", "2.00", "1.00", "0.50", "0.20", "0.10", "0.05"
        };

        while (true) {
          double remaining = totalPrice - totalTendered;
          System.out.printf("$%.2f remains to be paid. Enter coin or note: ", remaining);
          response = scan.next();

		  // find if it is a valid denomination
          int index = 0;
          while (index < tenderString.length) {
          	 if ( tenderString[index].equals( response ) ) {
				break;
			 }
		     index++;
		  }
		  // not found
		  if (index == tenderString.length) {
            System.out.printf("Invalid coin or note. Try again.\n");
			continue; 
		  }
		  
		  // is found, use index for other array
          totalTendered += tenderValues[index];

          if (totalTendered >= totalPrice) {
            break;
          }
        }

        System.out.printf("\n");
        System.out.printf("You gave $%.2f\n", totalTendered);

        if (totalTendered == totalPrice) {
          System.out.printf("Perfect! No change given.\n");
        } else {
          System.out.printf("Your change:\n");
          double change = totalTendered - totalPrice;
          for (double value : tenderValues) {
            int amount = (int) Math.floor(change / value);
            if (amount >= 1) {
              System.out.printf("%d x $%.2f\n", amount, value);
            }
            change -= amount * value;
          }
        }

        System.out.printf("\n");
        System.out.printf("Thank you, %s.\n", name);
        System.out.printf("See you next time.\n");
    }
}
