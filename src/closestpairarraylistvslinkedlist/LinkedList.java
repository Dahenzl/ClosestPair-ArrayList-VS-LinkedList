
package closestpairarraylistvslinkedlist;

/**
 *
 * @author ddhenriquez
 */
public class LinkedList {
    LinkedNode head;
    
    public LinkedList(){
        head = null;
    }
    public static LinkedList insertNode(LinkedList list, int x, int y){
        LinkedNode newNode = new LinkedNode(x, y);
        if(list.head == null){
            list.head = newNode;
        } else{
            LinkedNode last = list.head;
            while(last.next != null){
                last = last.next;
            }
            last.next = newNode;
        }
        return list;
    }
    
    public static LinkedNode get(LinkedList list, int i){
        LinkedNode result;
        LinkedNode last = list.head;
        int j = 0;
        while(j < i){
            last = last.next;
        }
        result = last;
        return result;
    }
}
