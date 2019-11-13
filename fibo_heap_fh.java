import java.util.*;
public class fibo_heap_fh
{
    private node_fibo_heap maximum_nodefiboheap; // declaration of variable to denote the maximum node in the heap
    private int nodes_in_heap; // declaration of variable to signify the number of nodes that are stored in the heap

    public void node_insertion(node_fibo_heap nodefiboheap_to_insert) //Function to insert a new node in the fibonacci heap
    {
        if (maximum_nodefiboheap != null) //to check if the maximum node is not null
        {
            nodefiboheap_to_insert.l_nodefiboheap = maximum_nodefiboheap;
            nodefiboheap_to_insert.r_nodefiboheap = maximum_nodefiboheap.r_nodefiboheap;
            maximum_nodefiboheap.r_nodefiboheap = nodefiboheap_to_insert; // to insert to the right of the maximum nodefiboheap_to_insert

            if ( nodefiboheap_to_insert.r_nodefiboheap !=null) //to check if the right of nodefiboheap_to_insert is not null
            {
                nodefiboheap_to_insert.r_nodefiboheap.l_nodefiboheap = nodefiboheap_to_insert;
            }
            if ( nodefiboheap_to_insert.r_nodefiboheap ==null)
            {
                nodefiboheap_to_insert.r_nodefiboheap = maximum_nodefiboheap;
                maximum_nodefiboheap.l_nodefiboheap = nodefiboheap_to_insert;
            }
            if (nodefiboheap_to_insert.key > maximum_nodefiboheap.key)
            {
                maximum_nodefiboheap = nodefiboheap_to_insert;
            }
        } else
            {
            maximum_nodefiboheap = nodefiboheap_to_insert;
            }
            nodes_in_heap++;
    }


    public void node_cut(node_fibo_heap n1, node_fibo_heap n2)  //Function to perform the cut operation which cut n1 from n2
    {

    }

    public void node_childCut_check(node_fibo_heap n2) // Function to perform the cascading cut on a particular node.
    {
        node_fibo_heap n1 = n2.p_nodefiboheap;
        if (n1 != null) // to check if the parent node is not null
            {
                if (!n2.childCut) // if n2 is false , then set it true
            {
                n2.childCut = true;
            } else
                {
                    node_cut(n2, n1); // if a particular node is already set as true , then cut it from parent node

                    node_childCut_check(n1);  //  also cut the parent node
            }
        }
    }

    public void node_inc_key_val(node_fibo_heap n1, int inc_val) //Function to increase the value of key for the given node in fibonacci heap
    {
        if (inc_val < n1.key) {
        }
        n1.key = inc_val;
        node_fibo_heap n2 = n1.p_nodefiboheap;
        if ((n2 != null) && (n1.key > n2.key)) 
        {
            node_cut(n1, n2);
            node_childCut_check(n2);
        }

        if (n1.key > maximum_nodefiboheap.key)
        {
            maximum_nodefiboheap = n1;
        }
    }

    public node_fibo_heap node_rem_maximum() //Function to remove the maximum node from the fibonacci heap
    {
        node_fibo_heap n2 = maximum_nodefiboheap;
        if (n2 != null)
        {
            int child_num = n2.d_node;
            node_fibo_heap n1 = n2.c_nodefiboheap;
            node_fibo_heap tempRight;

            for (int j = child_num; j > 0; j--) // Loop to execute till the number of children of a node are greater than zero
            {
                tempRight = n1.r_nodefiboheap;
                n1.l_nodefiboheap.r_nodefiboheap = n1.r_nodefiboheap; // remove n1 from child node list
                n1.r_nodefiboheap.l_nodefiboheap = n1.l_nodefiboheap;
                n1.l_nodefiboheap = maximum_nodefiboheap; // add n1 to root list of fibonacci heap
                n1.r_nodefiboheap = maximum_nodefiboheap.r_nodefiboheap;
                maximum_nodefiboheap.r_nodefiboheap = n1;
                n1.r_nodefiboheap.l_nodefiboheap = n1;
                n1.p_nodefiboheap = null;    // to set the parent node to null
                n1 = tempRight;
            }

            n2.l_nodefiboheap.r_nodefiboheap = n2.r_nodefiboheap; // to remove n2 from the root list of fibonacci heap
            n2.r_nodefiboheap.l_nodefiboheap = n2.l_nodefiboheap;

            if (n2 == n2.r_nodefiboheap)
            {
                maximum_nodefiboheap = null;

            } else
                {
                maximum_nodefiboheap = n2.r_nodefiboheap;
                node_same_degree_merge();
            }
            nodes_in_heap--;
            return n2;
        }
        return null;
    }

    public void node_same_degree_merge() //Function to merge the nodes of same degree
    {
        int table_size =45;
        List<node_fibo_heap> table =
                new ArrayList<node_fibo_heap>(table_size);

        // Initialize degree table
            int j = 0;
            while(j < table_size)
            {
            table.add(null);
            j++;
        }



        // Find the number of root nodes.
        int num_root_nodes = 0;
        node_fibo_heap n1 = maximum_nodefiboheap;


        if (n1 != null) {
            num_root_nodes++;
            n1 = n1.r_nodefiboheap;

            while (n1 != maximum_nodefiboheap) {
                num_root_nodes++;
                n1 = n1.r_nodefiboheap;
            }
        }

        // For each node in root list
        for ( int i = num_root_nodes; i > 0; i--)
        {

            int degree = n1.d_node;
            node_fibo_heap next_nodefiboheap = n1.r_nodefiboheap;

            // check if the degree is there in degree table, if not add,if yes then combine and merge
            while(true) {
                node_fibo_heap n2 = table.get(degree);
                if (n2 == null) {
                    break;
                }

                //Check whose key value is greater
                if (n1.key < n2.key) {
                    node_fibo_heap temp_swap = n2;
                    n2 = n1;
                    n1 = temp_swap;
                }


                node_child(n2, n1);

                //set the degree to null as n1 and n2are combined now
                table.set(degree, null);
                degree++;
            }

            //store the new n1(n1+n2) in the respective degree table position
            table.set(degree, n1);

            // Move forward through list.
            n1 = next_nodefiboheap;

        }
        //Deleting the max node
        maximum_nodefiboheap = null;

        // combine entries of the degree table
        for (int k = 0; k < table_size; k++) {
            node_fibo_heap n = table.get(k);
            if (n == null) {
                continue;
            }

            //till max node is not null
            if (maximum_nodefiboheap != null) {

                // First remove node from root list.
                n.l_nodefiboheap.r_nodefiboheap = n.r_nodefiboheap;
                n.r_nodefiboheap.l_nodefiboheap = n.l_nodefiboheap;

                // Now add to root list, again.
                n.l_nodefiboheap = maximum_nodefiboheap;
                n.r_nodefiboheap = maximum_nodefiboheap.r_nodefiboheap;
                maximum_nodefiboheap.r_nodefiboheap = n;
                n.r_nodefiboheap.l_nodefiboheap = n;

                // Check if this is a new maximum
                if (n.key > maximum_nodefiboheap.key) {
                    maximum_nodefiboheap = n;
                }
            } else {
                maximum_nodefiboheap = n;
            }
        }
    }

    public void node_child(node_fibo_heap n2, node_fibo_heap n1)
    {
        // remove n2 from root list of heap
        n2.l_nodefiboheap.r_nodefiboheap = n2.r_nodefiboheap;
        n2.r_nodefiboheap.l_nodefiboheap = n2.l_nodefiboheap;

        // make n2 a child of n1
        n2.p_nodefiboheap = n1;

        if (n1.c_nodefiboheap == null) {
            n1.c_nodefiboheap = n2;
            n2.r_nodefiboheap = n2;
            n2.l_nodefiboheap = n2;
        } else {
            n2.l_nodefiboheap = n1.c_nodefiboheap;
            n2.r_nodefiboheap = n1.c_nodefiboheap.r_nodefiboheap;
            n1.c_nodefiboheap.r_nodefiboheap = n2;
            n2.r_nodefiboheap.l_nodefiboheap = n2;
        }

        // increase degree of n1 by 1
        n1.d_node++;

        // make childCut of n2 as false
        n2.childCut = false;
    }

}