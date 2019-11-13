public class node_fibo_heap
{
    node_fibo_heap l_nodefiboheap, r_nodefiboheap, c_nodefiboheap, p_nodefiboheap;
    int d_node = 0;
    boolean childCut = false;
    private String keyword;
    int key;

    node_fibo_heap(String keyword, int key)
    {
        this.l_nodefiboheap = this;
        this.r_nodefiboheap = this;
        this.p_nodefiboheap = null;
        this.d_node = 0;
        this.keyword = keyword;
        this.key = key;

    }

    public  String  pop_keyword()
    {
        return this.keyword;
    }

}