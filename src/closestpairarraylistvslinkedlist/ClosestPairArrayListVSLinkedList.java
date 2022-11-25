
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
 * Date: 16/11/2022
 * 
 * WorkShop: Closest Pair
 * In this workshop I created a proyect that is able to find the closest pair of nodes in two diferent ways, using the brute force and by divide and conquer.
 * To them write the number of iterations and the time it took to both method to complete the same task, so them we can compare it and make an analysis and comparisons between both methods.
 *
 * References:
 * https://www.geeksforgeeks.org/arrays-sort-in-java-with-examples/
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
        
        int N = 2; //Set the size of the array of nodes.
        int x = 4; //Set the limit of the random function in x.
        int y = 8; //Set the limit of the random function in y.
        
        create("resultsBruteArray.txt"); //Creates the file with the results from the brute method using Array lists.
        create("resultsDivideArray.txt"); //Creates the file with the results from the divide and conquer method using Array lists.
        create("resultsBruteLinked.txt"); //Creates the file with the results from the brute method using Linked lists.
        create("resultsDivideLinked.txt"); //Creates the file with the results from the divide and conquer method using Linked lists.
         
        for (int i = 0; i < 12; i++) {
            iteraciones = 0; //Resets the iterations counter.
            //Perform 6 tests with the same N to then calculate the average of the times and the iterations.
            for (int j = 0; j < 6; j++) {
                minDist = Double.POSITIVE_INFINITY; //Resets the minimun distance.
                Nodos = createNodesArray(N, x, y); //Create the array of nodes.
                inicio = System.nanoTime(); //Takes the exact time in wich the divide and conquer method started.
                parCercanoRecursivoArray(Nodos); //Start of the recursive process.
                fin = System.nanoTime(); //Takes the exact time in wich the divide and conquer method started.
                DivideTimes[j] = fin - inicio; //Save the ejecution times in an array.
            }
            writeResults(N, iteraciones/6, Average(DivideTimes), outDivideArray); //Writes on the divide and conquer results file.
            printAndBruteArray(Nodos); //Print in console the results of the program.
            writeResults(N, iteraciones, fin - inicio, outBruteArray); //Writes on the brute force results file.
            N = N * 2; //Duplicate the size of the array of nodes.
            x = x * 2; //Duplicate the random limit of x.
        }
        
        System.out.println("Linked");
        N = 2; //Reset the size of the array of nodes.
        x = 4; //Reset the limit of the random function in x.
        y = 8; //Reset the limit of the random function in y.
        
        for (int i = 0; i < 12; i++) {
            System.out.println(i);
            iteraciones = 0;
            minDist = Double.POSITIVE_INFINITY;
            listaNodos = createNodesLinked(N, x, y);
            inicio = System.nanoTime();
            parCercanoRecursivoLinked(listaNodos);
            fin = System.nanoTime();
            writeResults(N, iteraciones, fin - inicio, outDivideLinked);
            printAndBruteLinked(listaNodos);
            writeResults(N, iteraciones, fin - inicio, outBruteLinked);
            N = N * 2;
            x = x * 2;
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
    Recursive function that finds the closest pair of nodes in an array list by using the divide and conquer method.
    Input:
    listaN - The array from which we want to find the closest pair.
    Outputs:
    minDist - The minimun distance between two nodes of the array finded by using divide and conquer method.
    FirstNode - The first node of the closest pair in the array finded by using divide and conquer method.
    SecondNode - The second node of the closest pair in the array finded by using divide and conquer method.
    */
    {
        if (listaN.size() <= 3){
            bruteArray(listaN); //Find the minimum distance of the subdivision of the list to analyze when it's size is equal or less than 3.
        }else{
            parCercanoRecursivoArray(divideArray(listaN, 0)); //Call the recursive function with the first half of the array.
            parCercanoRecursivoArray(divideArray(listaN, 1)); //Call the recursive function with the second half of the array.
            middleArray(listaN); //Create the middle of the array and find its minium distance.
        }   
    }
    
    public static void parCercanoRecursivoLinked(LinkedList listaN)
    /*
    Recursive function that finds the closest pair of nodes in a linked list by using the divide and conquer method.
    Input:
    listaN - The array from which we want to find the closest pair.
    Outputs:
    minDist - The minimun distance between two nodes of the array finded by using divide and conquer method.
    FirstNode - The first node of the closest pair in the array finded by using divide and conquer method.
    SecondNode - The second node of the closest pair in the array finded by using divide and conquer method.
    */
    {
        if (listaN.size(listaN) <= 3){
            bruteLinked(listaN); //Find the minimum distance of the subdivision of the list to analyze when it's size is equal or less than 3.
        }else{
            parCercanoRecursivoLinked(divideLinked(listaN, 0)); //Call the recursive function with the first half of the array.
            parCercanoRecursivoLinked(divideLinked(listaN, 1)); //Call the recursive function with the second half of the array.
            middleLinked(listaN); //Create the middle of the array and find its minium distance.
        }   
    }
    
    public static ArrayList<Node> createNodesArray(int N, int ranX, int ranY)
    /*
    Function that create the array of nodes using an array list.
    Inputs: 
    N - The size of the array.
    ranX - The limit of the random function in x.
    ranY - The limit of the random function in y.
    Output:
    Nodes - Principal array of nodes.
    */
    {
        //Create the array of nodes.
        ArrayList<Node> Nodes = new ArrayList<Node>();
        for (int i = 0; i < N; i++) {
            int x = ran.nextInt(ranX);
            int y = ran.nextInt(ranY);
            Node n = new Node(x, y);
            Nodes.add(n);
        }
        
        Collections.sort(Nodes, new SortByXandY()); //Calls a function that sorts the array.

        return Nodes; 
    }
    
    public static LinkedList createNodesLinked(int N, int ranX, int ranY)
    /*
    Function that create the array of nodes using a linked list.
    Inputs: 
    N - The size of the array.
    ranX - The limit of the random function in x.
    ranY - The limit of the random function in y.
    Output:
    Nodes - Principal array of nodes.
    */
    {
        //Create the array of nodes.
        LinkedList list = new LinkedList();
        for (int i = 0; i < N; i++) {
            int x = ran.nextInt(ranX);
            int y = ran.nextInt(ranY);
            LinkedNode n = new LinkedNode(x, y);
            list.insertNode(list, n);
        }
        
        list.sortList(list);

        return list; 
    }
       
    public static void bruteArray(ArrayList<Node> listaN)
    /*
    Function that finds the minimun distance between two nodes of an array list.
    Inputs:
    listaN - The array from which we want to find the minimum distance.
    Outputs:
    dist - The minimun distance between two nodes of the array.
    nodeA - The first node of the closest pair in the array.
    nodeB - The second node of the closest pair in the array.
    */ 
    {
        //Calculate the distance between each pair of nodes in the array to find the minimun distance and the two closest nodes.
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
    Function that finds the minimun distance between two nodes of an linked list.
    Inputs:
    listaN - The array from which we want to find the minimum distance.
    Outputs:
    dist - The minimun distance between two nodes of the array.
    nodeA - The first node of the closest pair in the array.
    nodeB - The second node of the closest pair in the array.
    */ 
    {
        //Calculate the distance between each pair of nodes in the array to find the minimun distance and the two closest nodes.
        for (int j = 0; j < listaN.size(listaN)-1; j++) {
            for (int k = j+1; k < listaN.size(listaN); k++) {
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
    Function that divides an array list of nodes in two halfs.
    Inputs:
    list - The array we want to divide.
    Half - An integer that determine if the output is going to be the first half or the second half of the array.
    Outputs:
    halfList - A half of the original array, the first half if Half is 0 and the second half if Half is 1.
    */
    {
        ArrayList<Node> halfList = new ArrayList<Node>();
        
        //Determine wich half of the array is going to return the function depending on the int Half.
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
    list - The array we want to divide.
    Half - An integer that determine if the output is going to be the first half or the second half of the array.
    Outputs:
    halfList - A half of the original array, the first half if Half is 0 and the second half if Half is 1.
    */
    {
        LinkedList halfList = new LinkedList();
        int size = list.size(list);
        //Determine wich half of the array is going to return the function depending on the int Half.
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
    Function that create the middle array list of the given array.
    Inputs:
    listaN - The array from which we want to create the middle.
    Outputs:
    Middle - The middle of the given array depending on the minimun distance.
    */
    {
        ArrayList<Node> Middle = new ArrayList<Node>();
        double middle = (listaN.get(listaN.size()/2-1).x + listaN.get(listaN.size()/2).x)/2;
        double firstMiddle = middle - Math.sqrt(minDist);
        double secondMiddle = middle + Math.sqrt(minDist);
        
        //Creates the middle array depending on the range determinated by firstMiddle and secondMiddle.
        for (int j = 0; j < listaN.size(); j++) {
            if(listaN.get(j).x > firstMiddle && listaN.get(j).x < secondMiddle){
                Middle.add(listaN.get(j));
            }
        }

        bruteArray(Middle); //Perform the brute function with the middle array.
    }    
    
    public static void middleLinked(LinkedList listaN)
    /*
    Function that create the middle linked list of the given array.
    Inputs:
    listaN - The array from which we want to create the middle.
    Outputs:
    Middle - The middle of the given array depending on the minimun distance.
    */
    {
        LinkedList Middle = new LinkedList();
        double middle = (listaN.get(listaN, listaN.size(listaN)/2-1).x + listaN.get(listaN, listaN.size(listaN)/2).x)/2;
        double firstMiddle = middle - Math.sqrt(minDist);
        double secondMiddle = middle + Math.sqrt(minDist);
        
        //Creates the middle array depending on the range determinated by firstMiddle and secondMiddle.
        for (int j = 0; j < listaN.size(listaN); j++) {
            if(listaN.get(listaN, j).x > firstMiddle && listaN.get(listaN, j).x < secondMiddle){
                LinkedNode readed = listaN.get(listaN, j);
                LinkedNode newNode = new LinkedNode(readed.x,readed.y);
                Middle.insertNode(Middle, newNode);
            }
        }

        bruteLinked(Middle); //Perform the brute function with the middle array.
    }  
    
    public static void printAndBruteArray(ArrayList<Node> listaN)
    /*
    Function that prints the results of the recursive function, performs the brute force method, and prints its results for comparison, all of this using an array list.
    Inputs:
    listaN - The array from which we want to print the collected data and perform the brute function.
    Outputs:
    minDist - The minimun distance between two nodes of the array finded by using the brute force method.
    FirstNode - The first node of the closest pair in the array finded by using the brute force method.
    SecondNode - The second node of the closest pair in the array finded by using the brute force method.
    Prints in console the minimun distance and the closest pair of nodes of the array, showing first the results of the recursive function and them
    the results of putting thw whole array in the brute function.
    */
    {        
        //Prints the results of the recursive function.
        System.out.println("\n" + "Resultados divide and conquer; ");
        System.out.println("Distancia: " + Math.sqrt(minDist) + " - Primer nodo: (" + FirstNode.x + "," + FirstNode.y + ") - Segundo nodo: (" + SecondNode.x + "," + SecondNode.y + ")");
        
        minDist = Double.POSITIVE_INFINITY; //Resets the minimun distance.
        iteraciones  = 0; //Resets the iterations counter.
        inicio = System.nanoTime(); //Takes the exact time in wich the brute force method started.
        bruteArray(listaN); //Performs the brute function to the whole array.
        fin = System.nanoTime(); //Takes the exact time in wich the brute force method started.

        //Prints the results of putting the whole array in the brute force method.
        System.out.println("Resultados brute; ");
        System.out.println("Distancia: " + Math.sqrt(minDist) + " - Primer nodo: (" + FirstNode.x + "," + FirstNode.y + ") - Segundo nodo: (" + SecondNode.x + "," + SecondNode.y + ")");
    }  
    
    public static void printAndBruteLinked(LinkedList listaN)
    /*
    Function that prints the results of the recursive function, performs the brute force method, and prints its results for comparison, all of this using a linked list.
    Inputs:
    listaN - The array from which we want to print the collected data and perform the brute function.
    Outputs:
    minDist - The minimun distance between two nodes of the array finded by using the brute force method.
    FirstNode - The first node of the closest pair in the array finded by using the brute force method.
    SecondNode - The second node of the closest pair in the array finded by using the brute force method.
    Prints in console the minimun distance and the closest pair of nodes of the array, showing first the results of the recursive function and them
    the results of putting thw whole array in the brute function.
    */
    {        
        //Prints the results of the recursive function.
        System.out.println("\n" + "Resultados divide and conquer; ");
        System.out.println("Distancia: " + Math.sqrt(minDist) + " - Primer nodo: (" + FirstNode.x + "," + FirstNode.y + ") - Segundo nodo: (" + SecondNode.x + "," + SecondNode.y + ")");
        
        minDist = Double.POSITIVE_INFINITY; //Resets the minimun distance.
        iteraciones  = 0; //Resets the iterations counter.
        inicio = System.nanoTime(); //Takes the exact time in wich the brute force method started.
        bruteLinked(listaN); //Performs the brute function to the whole array.
        fin = System.nanoTime(); //Takes the exact time in wich the brute force method started.

        //Prints the results of putting the whole array in the brute force method.
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
