package academy;

import java.util.Scanner;

public class CoffeeMachine {
    public static final int ESPRESSO_WATER = 250;
    public static final int ESPRESSO_COFFEE_BEANS = 16;
    public static final int ESPRESSO_COST = 4;

    public static final int LATTE_WATER = 350;
    public static final int LATTE_MILK = 75;
    public static final int LATTE_COFFEE_BEANS = 20;
    public static final int LATTE_COST = 7;

    public static final int CAPPUCCINO_WATER = 200;
    public static final int CAPPUCCINO_MILK = 100;
    public static final int CAPPUCCINO_COFFEE_BEANS = 12;
    public static final int CAPPUCCINO_COST = 6;

    private int water;
    private int milk;
    private int coffeeBeans;
    private int disposableCups;
    private int money;
    private MachineState machineState;

    public CoffeeMachine (int water, int milk, int coffeeBeans, int disposableCups, int money, MachineState machineState){
        this.water = water;
        this.milk = milk;
        this.coffeeBeans = coffeeBeans;
        this.disposableCups = disposableCups;
        this.money = money;
        this.machineState = machineState;
    }

    public void printRemaining(){
        System.out.println("\nThe coffee machine has:\n" +
                water + " ml of water\n" +
                milk + " ml of milk\n" +
                coffeeBeans + " g of coffee beans\n" +
                disposableCups + " disposable cups\n$" +
                money + " of money");
    }

    public void makeEspresso(){
        water -= ESPRESSO_WATER;
        coffeeBeans -= ESPRESSO_COFFEE_BEANS;
        money += ESPRESSO_COST;
        disposableCups--;
    }

    public void makeLatte(){
        water -= LATTE_WATER;
        milk -= LATTE_MILK;
        coffeeBeans -= LATTE_COFFEE_BEANS;
        money += LATTE_COST;
        disposableCups--;
    }

    public void makeCappuccino(){
        water -= CAPPUCCINO_WATER;
        milk -= CAPPUCCINO_MILK;
        coffeeBeans -= CAPPUCCINO_COFFEE_BEANS;
        money += CAPPUCCINO_COST;
        disposableCups--;
    }

    public void buy(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nWhat do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
        String option = scanner.next();
        switch (option) {
            case "1":
                if (water < ESPRESSO_WATER)
                    System.out.println("Sorry, not enough water!");
                else if (coffeeBeans < ESPRESSO_COFFEE_BEANS)
                    System.out.println("Sorry, not enough coffee beans!");
                else {
                    System.out.println("I have enough resources, making you a coffee!");
                    this.makeEspresso();
                }
                break;
            case "2":
                if (water < LATTE_WATER)
                    System.out.println("Sorry, not enough water!");
                else if (milk < LATTE_MILK)
                    System.out.println("Sorry, not enough milk!");
                else if (coffeeBeans < LATTE_COFFEE_BEANS)
                    System.out.println("Sorry, not enough coffee beans!");
                else {
                    System.out.println("I have enough resources, making you a coffee!");
                    this.makeLatte();
                }
                break;
            case "3":
                if (water < CAPPUCCINO_WATER)
                    System.out.println("Sorry, not enough water!");
                else if (milk < CAPPUCCINO_MILK)
                    System.out.println("Sorry, not enough milk!");
                else if (coffeeBeans < CAPPUCCINO_COFFEE_BEANS)
                    System.out.println("Sorry, not enough coffee beans!");
                else {
                    System.out.println("I have enough resources, making you a coffee!");
                    this.makeCappuccino();
                }
                break;
            case "back":
                break;
        }
    }

    public void fill(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nWrite how many ml of water you want to add:");
        int fillAmount = scanner.nextInt();
        this.water += fillAmount;
        System.out.println("Write how many ml of milk you want to add:");
        fillAmount = scanner.nextInt();
        this.milk += fillAmount;
        System.out.println("Write how many grams of coffee beans you want to add:");
        fillAmount = scanner.nextInt();
        this.coffeeBeans += fillAmount;
        System.out.println("Write how many disposable cups of coffee you want to add:");
        fillAmount = scanner.nextInt();
        this.disposableCups += fillAmount;
    }

    public void take(){
        System.out.println("\nI gave you $" + this.money);
        this.money = 0;
    }

    public void chooseAction() {
        String action = "";
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nWrite action (buy, fill, take, remaining, exit):");
        action = scanner.next();
        this.machineState = MachineState.getMachineStateByActionInput(this.machineState, action);
    }

    public void run(){
        while(this.machineState != MachineState.EXITING){
            switch(this.machineState){
                case CHOOSING_ACTION:
                    this.chooseAction();
                    break;
                case CHOOSING_COFFEE:
                    this.buy();
                    this.machineState = MachineState.CHOOSING_ACTION;
                    break;
                case FILLING:
                    this.fill();
                    this.machineState = MachineState.CHOOSING_ACTION;
                    break;
                case TAKING_MONEY:
                    this.take();
                    this.machineState = MachineState.CHOOSING_ACTION;
                    break;
                case PRINTING_REMAINING:
                    this.printRemaining();
                    this.machineState = MachineState.CHOOSING_ACTION;
                    break;
                case EXITING:
                    break;
            }
        }
    }

    public static void main(String[] args) {
        CoffeeMachine coffeeMachine = new CoffeeMachine(400, 540, 120, 9, 550, MachineState.CHOOSING_ACTION);
        coffeeMachine.run();
    }
}

enum MachineState {
    CHOOSING_ACTION(""),
    CHOOSING_COFFEE("buy"),
    FILLING("fill"),
    TAKING_MONEY("take"),
    PRINTING_REMAINING("remaining"),
    EXITING("exit");

    public String actionInput;

    MachineState(String actionInput) {
        this.actionInput = actionInput;
    }

    public static MachineState getMachineStateByActionInput(MachineState currentState, String actionInput) {
        for(MachineState value: MachineState.values()) {
            if(actionInput.equals(value.actionInput))
                return value;
        }
        return currentState;
    }
}
