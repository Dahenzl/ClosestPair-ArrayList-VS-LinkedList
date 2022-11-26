
package closestpairarraylistvslinkedlist;

import java.util.Comparator;

/**
 *
 * @author ddhenriquez
 */
public class SortByXandY implements Comparator<Node> {
    public int compare(Node a, Node b)
    {
        if(a.x == b.x){
            return a.y - b.y;
        } else{
            return a.x - b.x;
        }
    }
}
