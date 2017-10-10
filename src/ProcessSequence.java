import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

class ProcessSequence {
    private int numberOfElements;
    private int[] array;
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static final String EXIT = "exit";
    static
    {
        System.out.println("Enter the number of elements. Type \"exit\" at any step to terminate the program");
    }

    void process() {
        int count = 0;
        try{
            String s = reader.readLine();
            if(s.toLowerCase().equals(EXIT)) System.exit(0);
            do{
                numberOfElements = Integer.parseInt(s);
                if(numberOfElements <= 0) {
                    System.out.println("Invalid number. Enter a positive number");
                    process();
                    return;
                }
            } while(numberOfElements <= 0);
            array = new int[numberOfElements];
            Thread.sleep(1000);
            System.out.println("You entered: " + numberOfElements);
            definingOption();
            Thread.sleep(500);
            System.out.println("Processing...");
            Thread.sleep(1500);
            System.out.printf("Highest number: %d%n", getHighestNumber(array));
            System.out.printf("Lowest number: %d%n", getLowestNumber(array));
            System.out.printf("Longest sequence of equal numbers: %d%n", getLongestSequence(array));
            System.out.printf("Most frequent number in the array: %s%n", getMostFrequentNumber(array));
            System.out.printf("Greatest common divisor among numbers: %d%n", getGreatestCommonDivisor(array));
        }
        catch(NumberFormatException ex){
            System.out.println("Invalid number. Must be an integer");
            showStartMessage();
            count++;
        }
        catch(InterruptedException|IOException ex){
            System.out.println("Error occurred. Try again");
            count++;
        } finally {
            if(count > 0) process();
        }
    }

    private void definingOption() {
        System.out.println("Generate random array? Y/N");
        try{
            String option = reader.readLine();
            if(option.toLowerCase().equals(EXIT)) System.exit(0);
            if(option.toUpperCase().equals("Y")){
                System.out.println("Enter the lowest number in a random array.");
                option = reader.readLine();
                if(option.toLowerCase().equals(EXIT)) System.exit(0);
                int lowestNumber = Integer.parseInt(option);
                Thread.sleep(500);
                System.out.println("Enter the highest number in a random array.");
                option = reader.readLine();
                if(option.toLowerCase().equals(EXIT)) System.exit(0);
                int highestNumber = Integer.parseInt(option);
                if(highestNumber < lowestNumber) {
                    System.out.println("Highest number must be greater or equal to lowest number");
                    definingOption();
                    return;
                }
                Thread.sleep(500);
                System.out.println("Generating...");
                Thread.sleep(1500);

                for(int i = 0; i<numberOfElements; i++){
                    array[i] = ThreadLocalRandom.current().nextInt(lowestNumber, highestNumber+1);
                }
                System.out.println(Arrays.toString(array));
            }
            else if(option.toUpperCase().equals("N")){
                System.out.println("Enter your own elements.");
                String element;
                for(int i = 0; i<numberOfElements; i++){
                    element = reader.readLine();
                    if(element.toLowerCase().equals(EXIT)) System.exit(0);
                    array[i] = Integer.parseInt(element);
                }
                System.out.println(Arrays.toString(array));
            }
            else{
                System.out.println("Invalid option.");
                definingOption();
            }
        } catch(NumberFormatException e){
            System.out.println("Invalid number. Must be an integer");
            definingOption();
        }
        catch(InterruptedException|IOException ex) {
            System.out.println("Error occurred. Try again");
            process();
        }
    }

    private int getHighestNumber(int[] array){
        int[] arrayCopy = Arrays.copyOf(array, array.length);
        Arrays.sort(arrayCopy);
        return arrayCopy[arrayCopy.length - 1];
    }

    private int getLowestNumber(int[] array){
        int[] arrayCopy = Arrays.copyOf(array, array.length);
        Arrays.sort(arrayCopy);
        return arrayCopy[0];
    }

    private int getLongestSequence(int[] array){
        int currentLength = 1;
        int totalLength = 1;
        for(int i = 0; i<array.length - 1; i++){
            boolean isTheSame = array[i] == array[i + 1];
            if(isTheSame) {
                ++currentLength;
            } else{
                totalLength = currentLength>totalLength ? currentLength : totalLength;
                currentLength = 1;
            }
        }
        return totalLength>currentLength ? totalLength : currentLength;
    }

    private String getMostFrequentNumber(int[] array){
        ArrayList<Integer> list = new ArrayList<>(array.length);
        for(int i : array) list.add(i);
        TreeSet<Integer> set = new TreeSet<>(list);
        if(set.size() == list.size()) return "all numbers appear once";
        TreeMap<Integer, Integer> map = new TreeMap<>();
        for(Integer i : set) {
            map.put(Collections.frequency(list, list.get(list.indexOf(i))), i);
        }
        return map.get(map.lastKey()) + "";
    }

    private int getGreatestCommonDivisor(int[] array){
        int[] arrayCopy = Arrays.copyOf(array, array.length);
        Arrays.sort(arrayCopy);
        ArrayList<Integer> list = new ArrayList<>();
        for(int i = arrayCopy[0]*Integer.signum(arrayCopy[0]); i>0; i--){
            if(arrayCopy[0]%i==0) list.add(i);
        }
        int w = 0;
        int f = 0;
        for(int y: list){
            for(int x: arrayCopy){
                if(x%y!=0) {
                    w++;
                    break;
                }
            }
            if(w==0){
                f = y;
                break;
            }
            w = 0;
        }
        return f;
    }

    private void showStartMessage() {
        System.out.println("Enter the number of elements. Type \"exit\" at any step to terminate the program");
    }
}
