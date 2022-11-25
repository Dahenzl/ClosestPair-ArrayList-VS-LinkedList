
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
    public static void insertNode(LinkedList list, LinkedNode node){
        if(list.head == null){
            list.head = node;
        } else{
            LinkedNode last = list.head;
            while(last.next != null){
                last = last.next;
            }
            last.next = node;
        }
    }
    
    public static LinkedNode get(LinkedList list, int i){
        LinkedNode result;
        LinkedNode last = list.head;
        for (int j = 0; j < i; j++) {
            last = last.next;
        }   
        result = last;
        return result;
    }
    
    public static int size(LinkedList list){
        if(list.head == null){
            return 0;
        } else{
            int size = 1;
            LinkedNode last = list.head;
            while (last.next != null){
                last = last.next;
                size ++;
            }
            return size;
        }
    }
    
    public static void sortList(LinkedList list){
        LinkedNode current = list.head;
        LinkedNode index = null;
        
        int tempX, tempY;
        if(list.head == null){
            return;
        }else{
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
