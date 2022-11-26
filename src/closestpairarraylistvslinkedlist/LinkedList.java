
package closestpairarraylistvslinkedlist;

/**
 *
 * @author ddhenriquez
 */
public class LinkedList {
    LinkedNode head; //First node of the Linked List
    
    public LinkedList(){
        head = null;
    }
    
    public static void insertNode(LinkedList list, LinkedNode node)
    /*
    Function that insert a new node to the Linked List.
    Inputs:
    list - The Linked List in which we want to insert the new node.
    node - The node we want to insert to the Linked List.
    Outputs:
    Insert the node to the Linked List.
    */
    {
        //Check if the Linked List is empty
        if(list.head == null){
            list.head = node; //Insert the first node of the Linked List
        } else{
            LinkedNode last = list.head;
            //Search the last node of the Linked List
            while(last.next != null){
                last = last.next;
            }
            last.next = node; //Insert a new node to the Linked List
        }
    }
    
    public static LinkedNode get(LinkedList list, int i)
    /*
    Function that returns the node ubicated in a given position of the Linked List.
    Inputs:
    list - The Linked List from which we want to return the node.
    i - The position of the node in the Linked List.
    Outputs:
    result - The node ubicated in the i position of the Linked List.
    */
    {
        LinkedNode result = list.head;
        //Search through the Linked List the node in the given position.
        for (int j = 0; j < i; j++) {
            result = result.next;
        }   
        return result;
    }
    
    public static int size(LinkedList list)
    /*
    Function that returns the size of the Linked List.
    Inputs:
    list - The Linked List we want to know the size of.
    Outputs:
    size - The size of the given Linked List.
    */
    {
        //Check if the Linked List is empty
        if(list.head == null){
            return 0;
        } else{
            int size = 1;
            LinkedNode last = list.head;
            //Iterates through the linked list until it ends.
            while (last.next != null){
                last = last.next;
                size ++;
            }
            return size;
        }
    }
    
    public static void sortList(LinkedList list)
    /*
    Function that sorts the Linked List in ascending order.
    Inputs:
    list - The Linked Ñist we want to sort.
    Outputs:
    Sort the given Linked List in ascending order.
    */
    {
        LinkedNode current = list.head;
        LinkedNode index = null;
        
        int tempX, tempY;
        //Check if the Linked List is empty
        if(list.head == null){
            return;
        }else{
            //Compare each pair of nodes in the Linked List and swap their data to order them.
            while(current != null){
                index = current.next;
                while(index != null){
                    if((current.x > index.x) || (current.x == index.x && current.y > index.y)){
                        tempX = current.x;
                        tempY = current.y;
                        current.x = index.x;
                        current.y = index.y;
                        index.x = tempX;
                        index.y = tempY;     
                    }
                    index = index.next;
                }
                current = current.next;
            }
        }
    }
}
