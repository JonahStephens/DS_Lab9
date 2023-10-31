import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MemoryManagerTest {
	
	MemoryManager manager;

	@Test
	void test() {
		manager = new MemoryManager(20);
		MemoryAllocation A = manager.requestMemory(5, "A");
		assertEquals(5, A.getLength());
		assertEquals("A", A.getOwner());
		assertEquals(0, A.getPosition());
		manager.returnMemory(A);
		MemoryAllocation B = manager.requestMemory(20, "B");
		assertFalse(B == null);
		manager.returnMemory(B);
		
		MemoryAllocation C = manager.requestMemory(1000, "C");
		assertNull(C);
	}
	
	@Test
	void testNoAdjacentFree() {
		manager = new MemoryManager(20);
		MemoryAllocation A = manager.requestMemory(5, "A");
		MemoryAllocation B = manager.requestMemory(5, "B");
		MemoryAllocation C = manager.requestMemory(5, "C");
		MemoryAllocation D = manager.requestMemory(5, "D");
		manager.returnMemory(B);
		
		MemoryAllocation AC = manager.requestMemory(5, "AC");
		assertEquals(5, AC.getLength());
		assertEquals(5, AC.getPosition());
		assertEquals("AC", AC.getOwner());
	}
	
	@Test
	void testLeftAdjacentFree() {
		manager = new MemoryManager(20);
		MemoryAllocation A = manager.requestMemory(5, "A");
		MemoryAllocation B = manager.requestMemory(5, "B");
		MemoryAllocation C = manager.requestMemory(5, "C");
		MemoryAllocation D = manager.requestMemory(5, "D");
		manager.returnMemory(B);
		manager.returnMemory(C);
		
		MemoryAllocation AD = manager.requestMemory(10, "AD");
		assertEquals(10, AD.getLength());
		assertEquals(5, AD.getPosition());
		assertEquals("AD", AD.getOwner());
		
		manager.returnMemory(A);
		manager.returnMemory(D);
		manager.returnMemory(AD);
		MemoryAllocation fill = manager.requestMemory(20, "fill");
		MemoryAllocation extra = manager.requestMemory(1, "Extra");
		assertNull(extra);
	}
	
	@Test
	void testRightAdjacentFree() {
		manager = new MemoryManager(20);
		MemoryAllocation A = manager.requestMemory(5, "A");
		MemoryAllocation B = manager.requestMemory(5, "B");
		MemoryAllocation C = manager.requestMemory(5, "C");
		MemoryAllocation D = manager.requestMemory(5, "D");
		manager.returnMemory(C);
		manager.returnMemory(B);
		
		MemoryAllocation AD = manager.requestMemory(10, "AD");
		assertEquals(10, AD.getLength());
		assertEquals(5, AD.getPosition());
		assertEquals("AD", AD.getOwner());
		
		manager.returnMemory(A);
		manager.returnMemory(D);
		manager.returnMemory(AD);
		MemoryAllocation fill = manager.requestMemory(20, "fill");
		MemoryAllocation extra = manager.requestMemory(1, "Extra");
		assertNull(extra);
	}
	
	@Test
	void testBothAdjacentFree() {
		manager = new MemoryManager(20);
		MemoryAllocation A = manager.requestMemory(5, "A");
		MemoryAllocation B = manager.requestMemory(5, "B");
		MemoryAllocation C = manager.requestMemory(5, "C");
		MemoryAllocation D = manager.requestMemory(5, "D");
		manager.returnMemory(B);
		manager.returnMemory(D);
		manager.returnMemory(C);
		
		MemoryAllocation Q = manager.requestMemory(15, "Q");
		assertEquals(15, Q.getLength());
		assertEquals(5, Q.getPosition());
		assertEquals("Q", Q.getOwner());
		
		manager.returnMemory(A);
		manager.returnMemory(Q);
		MemoryAllocation fill = manager.requestMemory(20, "fill");
		MemoryAllocation extra = manager.requestMemory(1, "Extra");
		assertNull(extra);
	}

}
