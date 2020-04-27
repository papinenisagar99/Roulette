package com.playsafe.roulette;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

@SpringBootApplication
public class RouletteApplication {

    //Outcomes
    static List<String> outcome = new ArrayList<>();

    public static void main(String[] args) throws FileNotFoundException {
        SpringApplication.run(RouletteApplication.class, args);

        //Players list
        List<String> players = new ArrayList<>();

        List<String> betData = new ArrayList<>();

        int count = 1;
        File myObj = new File("source/data.out");

        //Input Scanner
        Scanner myReader = new Scanner(myObj);

        System.out.println("\n\nList of Players:");
        while (myReader.hasNextLine()) {
            String line = myReader.nextLine();
            //Show players on menu
            System.out.println(count + ". " + line);
            //Add players to list
            players.add(line);
            //Increment
            count++;
        }
        myReader.close();

        for (String player : players) {
            //Bets
            List<String> betDetails = new ArrayList<>();
            try {
                //Scanner
                Scanner scan = new Scanner(System.in);
                System.out.println("\n" + player + " Enter Bet (number between 1-36 or type EVEN or ODD : ");
                String bet = String.valueOf(scan.nextLine());
                System.out.println("\n" + player + " Enter Amount : ");
                double amount = scan.nextInt();

                betDetails.add(bet);
                betDetails.add(String.valueOf(amount));
                betDetails.add(player);
                betData.add(betDetails.toString());

            }catch(Exception ex){
                ex.printStackTrace();
                System.out.println("You have entered an invalid input, kindly retry");
            }
        }

        int rand = new Random().nextInt((36 - 1) + 1) + 1;
        System.out.println("\nRandom Generated winning number is : " + rand);

        List<String> result = new ArrayList<>();
        List<List<String>> summary = new ArrayList<>();

        for(String individualBetData: betData) {
            String[] splitted = individualBetData.split(",");
            String myBet = splitted[0].replace("[","");
            String myAmount = splitted[1];
            String myPlayer = splitted[2].replace("]","");

            result = checker(myBet, rand, Double.valueOf(myAmount), myPlayer);
            summary.add(result);
        }
        outcome = result;

        //Results
        System.out.println("Number : " + rand);
        System.out.println("---");
        
        for(List<String> myResult : summary){
            System.out.println(myResult.get(0) + "\t\t" + myResult.get(1) + "\t\t" + myResult.get(2) + "\t\t" + myResult.get(3));

        }



    }

    public static List<String> checker(String bet, Integer randomNumber, Double amount, String player){

        Double prize = 0.0;
        List<String> data = new ArrayList<>();

        //Initialisation
        data.add(player);
        data.add(bet);

        //Random number checker
        String valueType = "";
        if(randomNumber%2==0){
            valueType = "EVEN";
        }
        else{
            valueType = "ODD";
        }


        try{
            if(Integer.valueOf(bet).equals(randomNumber)){
                prize = amount * 36;
                data.add("WIN");
                data.add(prize.toString());
                return data;
            }
            else{
                data.add("LOSE");
                data.add(prize.toString());
                return data;
            }

        }catch (NumberFormatException ex){
            if(bet.equalsIgnoreCase(valueType)){
                prize = amount * 2;
                data.add("WIN");
                data.add(prize.toString());
                return data;
            }
            else if(bet.equalsIgnoreCase(valueType)){
                prize = amount * 2;
                data.add("WIN");
                data.add(prize.toString());
                return data;
            }
            else{
                data.add("LOSE");
                data.add(prize.toString());
                return data;
            }
        }

    }

}