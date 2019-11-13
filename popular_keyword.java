import java.io.*;
import java.util.*;
import java.util.regex.*;

public class popular_keyword
{
    public static void main(String[] args)
    {
        long prog_sTime = System.currentTimeMillis(); //to track the start time of the program

        String  input_filepath = args[0];  //for the path of the input file

        HashMap<String, node_fibo_heap> hash = new HashMap(); //to store the popular keywords and the node

        fibo_heap_fh fibo = new fibo_heap_fh();  //Creation of a fibonacci heap object


        File output_file = new File("output_file.txt");  //to denote the name of an output file
        BufferedWriter writer=null; // writer pointer

        // Exception handling
        try {

            BufferedReader br = new BufferedReader(new FileReader(input_filepath));
            String str = br.readLine();

            Pattern input_pattern = Pattern.compile("([$])([a-z_]+)(\\s)(\\d+)"); // regex for the input
            Pattern digit_pattern = Pattern.compile("(\\d+)");
            writer = new BufferedWriter( new FileWriter(output_file));

            while (str != null)
            {
                Matcher input_match = input_pattern.matcher(str); // for pattern matching of the input file
                Matcher digit_match = digit_pattern.matcher(str);
                if (input_match.find())
                {
                    String keyword = input_match.group(2);
                    int key = Integer.parseInt(input_match.group(4));

                    if ( !hash.containsKey(keyword)) //Check if it contains the key
                    {
                        node_fibo_heap n = new node_fibo_heap(keyword,key); //Create new node
                        fibo.node_insertion(n);
                        hash.put(keyword,n);
                    }
                    else
                    {
                        int inc_key_val = hash.get(keyword).key + key;
                        fibo.node_inc_key_val(hash.get(keyword),inc_key_val); // call the function in node_fibo_heap class
                    }
                }
                else if (digit_match.find())
                {
                    int num_rem_nodes = Integer.parseInt(digit_match.group(1)); //Number of Nodes to be removed

                    ArrayList<node_fibo_heap> rem_nodefiboheaps = new ArrayList<node_fibo_heap>(num_rem_nodes);  //Removed Nodes

                    int k=0;
                    while (k<num_rem_nodes)
                    {
                        node_fibo_heap n = fibo.node_rem_maximum(); //Removed node

                        hash.remove(n.pop_keyword());  //remove the keyword from the hashmap

                        node_fibo_heap n1= new node_fibo_heap(n.pop_keyword(),n.key); //Creation of a  new node for insertion

                        rem_nodefiboheaps.add(n1); //add the new node for insertion into removed nodes list

                        //Add the , until the last keyword
                        if ( k <num_rem_nodes-1) {
                            writer.write(n.pop_keyword() + ",");

                        }

                        else {

                            writer.write(n.pop_keyword());


                        }
                        k++;

                    }

                    //insertion step
                    for ( node_fibo_heap i : rem_nodefiboheaps)
                    {

                        fibo.node_insertion(i);
                        hash.put(i.pop_keyword(),i);


                    }

                    //go to new line in writer pointer
                    writer.newLine();
                }

                //Go to Next Line
                str = br.readLine();
            }
        }

        catch(Exception exp){
            System.out.println(exp);
        }
        //Close the writer
        finally {
            if ( writer != null ) {
                try {
                    writer.close();
                } catch (IOException ioe2) {

                }
            }
        }

        //Print the time required
        long prog_eTime   = System.currentTimeMillis();
        long prog_tTime = prog_eTime - prog_sTime;
        System.out.println(" Total Time in milli seconds: "+ prog_tTime);


    }


}