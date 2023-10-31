public class MemoryManager
{
   protected Node head;
    
   protected final String Free = "Free";

   class Node {
	   MemoryAllocation val;
	   Node next;
	   Node prev;
	   
	   public Node(MemoryAllocation val, Node prev, Node next) {
		   this.val = val;
		   this.next = next;
		   this.prev = prev;
	   }
   }
   
    /* size- how big is the memory space.  
     *  Memory starts at 0
     *
     */
   public MemoryManager(long size)
   {
	   head = new Node(null, null, null);
	   MemoryAllocation freeAllocation = new MemoryAllocation(Free, 0, size);
	   Node free = new Node(freeAllocation, head, head);
	   head.next = free;
	   head.prev = free;
   }



    /**
       takes the size of the requested memory and a string of the process requesting the memory
       returns a memory allocation that satisfies that request, or
       returns null if the request cannot be satisfied.
     */
    
   public MemoryAllocation requestMemory(long size,String requester)
   {
      Node current = head.next;
      
      while(current != head) {
    	  
    	  if(current.val.owner == Free && current.val.len >= size) {
    		  
    		  System.out.println(current.val.len);
    		  
    		  MemoryAllocation m = new MemoryAllocation(requester, current.val.pos, size);
    		  
    		  Node n = new Node(m, current.prev, current);
    		  current.prev.next = n;
    		  current.prev = n;
    		  
    		  current.val.pos += size;
    		  current.val.len -= size;
    		  
    		  return m;
    	  }
    	  
    	  current = current.next;
      }
      
      return null;
   }


    
    /**
       takes a memoryAllcoation and "returns" it to the system for future allocations.
       Assumes that memory allocations are only returned once.       

     */
   public void returnMemory(MemoryAllocation mem)
   {
	   Node current = head.next;
	   
	   while(current != head) {
		 
		   
		   if(current.val.equals(mem)) {
			   
			   
			   boolean prevFree = false;
			   boolean nextFree = false;
					   
			   if(current.prev.val != null) {
				   prevFree = current.prev.val.owner == Free;
			   }
			   
				if(current.next.val != null) {
					nextFree = current.next.val.owner == Free;
				}
			   
			   if(prevFree && nextFree) {
				   current.prev.val.len += current.val.len + current.next.val.len;
				   
				   current.prev.next = current.next.next;
				   current.next.next.prev = current.prev;
			   }else if(prevFree) {
				   current.prev.val.len += current.val.len;
				   
				   current.prev.next = current.next;
				   current.next.prev = current.prev;
			   }else if(nextFree) {
				   current.next.val.pos = current.val.pos;
				   current.next.val.len += current.val.len;
				   
				   current.prev.next = current.next;
				   current.next.prev = current.prev;
			   }else {
				   current.val.owner = Free;
			   }
		   }
		   
		   current = current.next;
	   }
	   
   }
    



}
