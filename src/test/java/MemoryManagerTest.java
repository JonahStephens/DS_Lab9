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
		
	}

}
