
package closestpairarraylistvslinkedlist;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/* Algoritmos y Complejidad IST 4310
 * NRC: 3265
 * Name: David Daniel Henriquez Leal
 * Student code: 200157506
 * Date: 25/11/2022
 * 
 * WorkShop: Closest Pair - Array List VS Linked List
 * In this workshop I created a proyect that is able to find the closest pair of nodes using two different algorithm design techniques, Brute Force and Divide and Conquer, in two different lists, Array Lists and Linked Lists.
 * To them write the number of iterations and the time it took to the four methods to complete the same task, so them we can compare it and make an analysis and comparisons between the four methods.
 *
 * References:
 * https://www.geeksforgeeks.org/arrays-sort-in-java-with-examples/
 * https://www.geeksforgeeks.org/implementing-a-linked-list-in-java-using-class/#:~:text=Like%20arrays%2C%20Linked%20List%20is,Node%20as%20a%20separate%20class.
 * https://www.geeksforgeeks.org/what-is-linked-list/
 * https://www.geeksforgeeks.org/introduction-to-divide-and-conquer-algorithm-data-structure-and-algorithm-tutorials/
 * https://www.geeksforgeeks.org/how-to-sort-a-linkedlist-in-java/
 */

public class ClosestPairArrayListVSLinkedList {
    static Random ran = new Random();
    private static double minDist ;
    private static Node FirstNode;
    private static Node SecondNode;
    private static long iteraciones, inicio, fin;

    public static void main(String[] args) throws FileNotFoundException {
        long DivideTimes[] = new long[6];
        ArrayList<Node> Nodos = new ArrayList<Node>();
        LinkedList listaNodos = new LinkedList();
        PrintWriter outDivideArray = new PrintWriter ("resultsDivideArray.txt");
        PrintWriter outBruteArray = new PrintWriter ("resultsBruteArray.txt");
        PrintWriter outDivideLinked = new PrintWriter ("resultsDivideLinked.txt");
        PrintWriter outBruteLinked = new PrintWriter ("resultsBruteLinked.txt");
        
        int N = 2; //Set the size of the list of nodes.
        int x = 4; //Set the limit of the random function in x.
        int y = 8; //Set the limit of the random function in y.
        
        create("resultsBruteArray.txt"); //Creates the file with the results from the brute method using Array Lists.
        create("resultsDivideArray.txt"); //Creates the file with the results from the divide and conquer method using Array Lists.
        create("resultsBruteLinked.txt"); //Creates the file with the results from the brute method using Linked Lists.
        create("resultsDivideLinked.txt"); //Creates the file with the results from the divide and conquer method using Linked Lists.
         
        for (int i = 0; i < 12; i++) {
            iteraciones = 0; //Resets the iterations counter.
            //Perform 6 tests with the same N to then calculate the average of the times and the iterations.
            for (int j = 0; j < 6; j++) {
                minDist = Double.POSITIVE_INFINITY; //Resets the minimun distance.
                Nodos = createNodesArray(N, x, y); //Create the Array List of nodes.
                inicio = System.nanoTime(); //Takes the exact time in wich the divide and conquer method started.
                parCercanoRecursivoArray(Nodos); //Start of the recursive process using Array Lists.
                fin = System.nanoTime(); //Takes the exact time in wich the divide and conquer method started.
                DivideTimes[j] = fin - inicio; //Save the ejecution times in an array.
            }
            writeResults(N, iteraciones/6, Average(DivideTimes), outDivideArray); //Writes on the divide and conquer using Array Lists results file.
            printAndBruteArray(Nodos); //Print in console the results of the program.
            writeResults(N, iteraciones, fin - inicio, outBruteArray); //Writes on the brute force using Array Lists results file.
            N = N * 2; //Duplicate the size of the Array List of nodes.
            x = x * 2; //Duplicate the random limit of x.
        }
        
        N = 2; //Reset the size of the list of nodes.
        x = 4; //Reset the limit of the random function in x.
        y = 8; //Reset the limit of the random function in y.
        
        for (int i = 0; i < 12; i++) {
            iteraciones = 0; //Resets the iterations counter.
            minDist = Double.POSITIVE_INFINITY; //Resets the minimun distance.
            listaNodos = createNodesLinked(N, x, y); //Create the Linked List of nodes.
            inicio = System.nanoTime(); //Takes the exact time in wich the divide and conquer method started.
            parCercanoRecursivoLinked(listaNodos); //Start of the recursive process using Linked List.
            fin = System.nanoTime(); //Takes the exact time in wich the divide and conquer method started.
            writeResults(N, iteraciones, fin - inicio, outDivideLinked); //Writes on the divide and conquer using Linked Lists results file.
            printAndBruteLinked(listaNodos); //Print in console the results of the program.
            writeResults(N, iteraciones, fin - inicio, outBruteLinked);  //Writes on the brute force using Linked Lists results file.
            N = N * 2; //Duplicate the size of the Linked List of nodes.
            x = x * 2; //Duplicate the random limit of x.
        }
           
        outDivideArray.close();
        outBruteArray.close();
        outDivideLinked.close();
        outBruteLinked.close();
    }
    
    private static void create (String name)
    /*
    Function that creates a file with the given names.
    Input:
    name - The name we want to give to the file.
    Output:
    A text file with the given name.
    */
    {
        try{
            String fname = (name); 
            File f = new File (fname); 
            f.createNewFile(); 
        }catch (IOException err){
            err.printStackTrace(); //Complains if there is an Input/Output Error.
        }
    }
    
    private static void writeResults(int N, long iterations, long time, PrintWriter writer) throws FileNotFoundException
    /*
    Function that write the data collected from both the brute force and divide-conquer method of finding the closest pair.
    Input:
    N - the size of the array of nodes.
    iterations - the number of iterations it take to the method to complete the task.
    end - The exact moment the method ended.
    start - The exact moment the method started.
    writer - The printwriter of the file we want to update.
    Output:
    Writes the given data in the results files.
    */
    {
         writer.printf("%s\n", N + " " + iterations + " " + time);
    }
    
    public static void parCercanoRecursivoArray(ArrayList<Node> listaN)
    /*
    Recursive function that finds the closest pair of nodes in an Array List by using the divide and conquer method.
    Input:
    listaN - The Array List from which we want to find the closest pair.
    Outputs:
    minDist - The minimun distance between two nodes of the Array List finded by using divide and conquer method.
    FirstNode - The first node of the closest pair in the Array List finded by using divide and conquer method.
    SecondNode - The second node of the closest pair in the Array List finded by using divide and conquer method.
    */
    {
        if (listaN.size() <= 3){
            bruteArray(listaN); //Find the minimum distance of the subdivision of the list to analyze when it's size is equal or less than 3.
        }else{
            parCercanoRecursivoArray(divideArray(listaN, 0)); //Call the recursive function with the first half of the Array List.
            parCercanoRecursivoArray(divideArray(listaN, 1)); //Call the recursive function with the second half of the Array List.
            middleArray(listaN); //Create the middle of the array and find its minium distance.
        }   
    }
    
    public static void parCercanoRecursivoLinked(LinkedList listaN)
    /*
    Recursive function that finds the closest pair of nodes in a Linked List by using the divide and conquer method.
    Input:
    listaN - The Linked List from which we want to find the closest pair.
    Outputs:
    minDist - The minimun distance between two nodes of the Linked List finded by using divide and conquer method.
    FirstNode - The first node of the closest pair in the Linked List finded by using divide and conquer method.
    SecondNode - The second node of the closest pair in the Linked List finded by using divide and conquer method.
    */
    {
        if (listaN.size(listaN) <= 3){
            bruteLinked(listaN); //Find the minimum distance of the subdivision of the list to analyze when it's size is equal or less than 3.
        }else{
            parCercanoRecursivoLinked(divideLinked(listaN, 0)); //Call the recursive function with the first half of the Linked List.
            parCercanoRecursivoLinked(divideLinked(listaN, 1)); //Call the recursive function with the second half of the Linked List.
            middleLinked(listaN); //Create the middle of the array and find its minium distance.
        }   
    }
    
    public static ArrayList<Node> createNodesArray(int N, int ranX, int ranY)
    /*
    Function that create the list of nodes using an Array List.
    Inputs: 
    N - The size of the Array List.
    ranX - The limit of the random function in x.
    ranY - The limit of the random function in y.
    Output:
    Nodes - Principal Array List of nodes.
    */
    {
        //Create the Array List of nodes.
        ArrayList<Node> Nodes = new ArrayList<Node>();
        for (int i = 0; i < N; i++) {
            int x = ran.nextInt(ranX);
            int y = ran.nextInt(ranY);
            Node n = new Node(x, y);
            Nodes.add(n);
        }
        
        Collections.sort(Nodes, new SortByXandY()); //Calls a function that sorts the Array List.

        return Nodes; 
    }
    
    public static LinkedList createNodesLinked(int N, int ranX, int ranY)
    /*
    Function that create the list of nodes using a Linked List.
    Inputs: 
    N - The size of the Linked List.
    ranX - The limit of the random function in x.
    ranY - The limit of the random function in y.
    Output:
    Nodes - Principal Linked List of nodes.
    */
    {
        //Create the Linked List of nodes.
        LinkedList list = new LinkedList();
        for (int i = 0; i < N; i++) {
            int x = ran.nextInt(ranX);
            int y = ran.nextInt(ranY);
            LinkedNode n = new LinkedNode(x, y);
            list.insertNode(list, n);
        }
        
        list.sortList(list); //Calls a function that sorts the Linked List

        return list; 
    }
       
    public static void bruteArray(ArrayList<Node> listaN)
    /*
    Function that finds the minimun distance between two nodes of an Array List.
    Inputs:
    listaN - The Array List from which we want to find the minimum distance.
    Outputs:
    dist - The minimun distance between two nodes of the array.
    nodeA - The first node of the closest pair in the Array List.
    nodeB - The second node of the closest pair in the Array List.
    */ 
    {
        //Calculate the distance between each pair of nodes in the Array List to find the minimun distance and the two closest nodes.
        for (int j = 0; j < listaN.size()-1; j++) {
            for (int k = j+1; k < listaN.size(); k++) {
                iteraciones ++; //Update the iterations counter.
                Node nodeA = listaN.get(j);
                Node nodeB = listaN.get(k);
                double distX = nodeB.x - nodeA.x;
                double distY = nodeB.y - nodeA.y;
                double dist = (distX*distX) + (distY*distY);
               
                //Check if the distance between the two nodes is less than the minimun distance and refresh the data.
                if (dist < minDist){
                    FirstNode = nodeA;
                    SecondNode = nodeB;
                    minDist = dist;
                }
            }
        }
    }
    
    public static void bruteLinked(LinkedList listaN)
    /*
    Function that finds the minimun distance between two nodes of a Linked List.
    Inputs:
    listaN - The Linked List from which we want to find the minimum distance.
    Outputs:
    dist - The minimun distance between two nodes of the array.
    nodeA - The first node of the closest pair in the Linked List.
    nodeB - The second node of the closest pair in the Linked List.
    */ 
    {
        //Calculate the distance between each pair of nodes in the Linked List to find the minimun distance and the two closest nodes.
        int size = listaN.size(listaN);
        for (int j = 0; j < size-1; j++) {
            for (int k = j+1; k < size; k++) {
                iteraciones ++; //Update the iterations counter.
                LinkedNode nodeA = listaN.get(listaN, j);
                LinkedNode nodeB = listaN.get(listaN, k);
                double distX = nodeB.x - nodeA.x;
                double distY = nodeB.y - nodeA.y;
                double dist = (distX*distX) + (distY*distY);
               
                //Check if the distance between the two nodes is less than the minimun distance and refresh the data.
                if (dist < minDist){
                    FirstNode = new Node(nodeA.x, nodeA.y);
                    SecondNode = new Node(nodeB.x, nodeB.y);
                    minDist = dist;
                }
            }
        }
    }
    
    public static ArrayList<Node> divideArray(ArrayList<Node> list, int Half)
    /*
    Function that divides an Array List of nodes in two halfs.
    Inputs:
    list - The Array list we want to divide.
    Half - An integer that determine if the output is going to be the first half or the second half of the Array List.
    Outputs:
    halfList - A half of the original Array List, the first half if Half is 0 and the second half if Half is 1.
    */
    {
        ArrayList<Node> halfList = new ArrayList<Node>();
        
        //Determine wich half of the Array List is going to return the function depending on the int Half.
        if (Half == 0){
            for (int i = 0; i < list.size()/2; i++) {
                halfList.add(list.get(i));
            }
        } else{
            for (int i = list.size()/2; i < list.size(); i++) {
                halfList.add(list.get(i));
            }
        }
        return halfList; 
    }
    
    public static LinkedList divideLinked(LinkedList list, int Half)
    /*
    Function that divides a Linked list of nodes in two halfs.
    Inputs:
    list - The Linked list we want to divide.
    Half - An integer that determine if the output is going to be the first half or the second half of the Linked List.
    Outputs:
    halfList - A half of the original Linked List, the first half if Half is 0 and the second half if Half is 1.
    */
    {
        LinkedList halfList = new LinkedList();
        int size = list.size(list);
        //Determine wich half of the Linked List is going to return the function depending on the int Half.
        if (Half == 0){
            for (int i = 0; i < size/2; i++) {
                LinkedNode readed = list.get(list, i);
                LinkedNode newNode = new LinkedNode(readed.x,readed.y);
                halfList.insertNode(halfList, newNode);
            }
        } else{
            for (int i = size/2; i < size; i++) {
                LinkedNode readed = list.get(list, i);
                LinkedNode newNode = new LinkedNode(readed.x,readed.y);
                halfList.insertNode(halfList, newNode);
            }
        }
        size = halfList.size(halfList);
        return halfList; 
    }
    
    public static void middleArray(ArrayList<Node> listaN)
    /*
    Function that create the Middle Array List of the given Array List.
    Inputs:
    listaN - The Array List from which we want to create the middle.
    Outputs:
    Middle - The middle of the given Array List depending on the minimun distance.
    */
    {
        ArrayList<Node> Middle = new ArrayList<Node>();
        double middle = (listaN.get(listaN.size()/2-1).x + listaN.get(listaN.size()/2).x)/2;
        double firstMiddle = middle - Math.sqrt(minDist);
        double secondMiddle = middle + Math.sqrt(minDist);
        
        //Creates the Middle Array List depending on the range determinated by firstMiddle and secondMiddle.
        for (int j = 0; j < listaN.size(); j++) {
            if(listaN.get(j).x > firstMiddle && listaN.get(j).x < secondMiddle){
                Middle.add(listaN.get(j));
            }
        }

        bruteArray(Middle); //Perform the brute function with the Middle Array List.
    }    
    
    public static void middleLinked(LinkedList listaN)
    /*
    Function that create the Middle Linked List of the given Linked list.
    Inputs:
    listaN - The Linked List from which we want to create the middle.
    Outputs:
    Middle - The middle of the given Linked List depending on the minimun distance.
    */
    {
        LinkedList Middle = new LinkedList();
        int size = listaN.size(listaN);
        double middle = (listaN.get(listaN, size/2-1).x + listaN.get(listaN, size/2).x)/2;
        double firstMiddle = middle - Math.sqrt(minDist);
        double secondMiddle = middle + Math.sqrt(minDist);
        
        //Creates the Middle Linked List depending on the range determinated by firstMiddle and secondMiddle.
        for (int j = 0; j < size; j++) {
            LinkedNode readed = listaN.get(listaN, j);
            if(readed.x > firstMiddle && readed.x < secondMiddle){
                LinkedNode newNode = new LinkedNode(readed.x,readed.y);
                Middle.insertNode(Middle, newNode);
            }
        }

        bruteLinked(Middle); //Perform the brute function with the Middle Linked List.
    }  
    
    public static void printAndBruteArray(ArrayList<Node> listaN)
    /*
    Function that prints the results of the recursive function, performs the brute force method, and prints its results for comparison, all of this using an Array List.
    Inputs:
    listaN - The Array List from which we want to print the collected data and perform the brute function.
    Outputs:
    minDist - The minimun distance between two nodes of the Array List finded by using the brute force method.
    FirstNode - The first node of the closest pair in the Array List finded by using the brute force method.
    SecondNode - The second node of the closest pair in the Array List finded by using the brute force method.
    Prints in console the minimun distance and the closest pair of nodes of the Array List, showing first the results of the recursive function and them
    the results of putting thw whole array in the brute function.
    */
    {        
        //Prints the results of the recursive function using Array Lists.
        System.out.println("\n" + "Resultados divide and conquer; ");
        System.out.println("Distancia: " + Math.sqrt(minDist) + " - Primer nodo: (" + FirstNode.x + "," + FirstNode.y + ") - Segundo nodo: (" + SecondNode.x + "," + SecondNode.y + ")");
        
        minDist = Double.POSITIVE_INFINITY; //Resets the minimun distance.
        iteraciones  = 0; //Resets the iterations counter.
        inicio = System.nanoTime(); //Takes the exact time in wich the brute force method started.
        bruteArray(listaN); //Performs the brute function to the whole Array List.
        fin = System.nanoTime(); //Takes the exact time in wich the brute force method started.

        //Prints the results of putting the whole Array List in the brute force method.
        System.out.println("Resultados brute; ");
        System.out.println("Distancia: " + Math.sqrt(minDist) + " - Primer nodo: (" + FirstNode.x + "," + FirstNode.y + ") - Segundo nodo: (" + SecondNode.x + "," + SecondNode.y + ")");
    }  
    
    public static void printAndBruteLinked(LinkedList listaN)
    /*
    Function that prints the results of the recursive function, performs the brute force method, and prints its results for comparison, all of this using a Linked List.
    Inputs:
    listaN - The Linked List from which we want to print the collected data and perform the brute function.
    Outputs:
    minDist - The minimun distance between two nodes of the Linked List finded by using the brute force method.
    FirstNode - The first node of the closest pair in the Linked List finded by using the brute force method.
    SecondNode - The second node of the closest pair in the Linked List finded by using the brute force method.
    Prints in console the minimun distance and the closest pair of nodes of the Linked List, showing first the results of the recursive function and them
    the results of putting thw whole array in the brute function.
    */
    {        
        //Prints the results of the recursive function.
        System.out.println("\n" + "Resultados divide and conquer; ");
        System.out.println("Distancia: " + Math.sqrt(minDist) + " - Primer nodo: (" + FirstNode.x + "," + FirstNode.y + ") - Segundo nodo: (" + SecondNode.x + "," + SecondNode.y + ")");
        
        minDist = Double.POSITIVE_INFINITY; //Resets the minimun distance.
        iteraciones  = 0; //Resets the iterations counter.
        inicio = System.nanoTime(); //Takes the exact time in wich the brute force method started.
        bruteLinked(listaN); //Performs the brute function to the Linked List.
        fin = System.nanoTime(); //Takes the exact time in wich the brute force method started.

        //Prints the results of putting the whole Linked List in the brute force method.
        System.out.println("Resultados brute; ");
        System.out.println("Distancia: " + Math.sqrt(minDist) + " - Primer nodo: (" + FirstNode.x + "," + FirstNode.y + ") - Segundo nodo: (" + SecondNode.x + "," + SecondNode.y + ")");
    }  
    
    public static long Average(long Times[])
    /*
    Function that returns the average of an array.
    Inputs:
    Times[] - The array from which we want to calculate the average.
    Outputs:
    average - The average of the array elements.      
    */
    {
        long sum = 0;
        //Calculate the average of the array.
        for (int i = 0; i < Times.length; i++) {
            sum += Times[i];
        }
        long average = sum/Times.length;
        return average;
    } 
}
