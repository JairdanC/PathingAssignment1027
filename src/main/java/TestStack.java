
public class TestStack {

	public static void main(String[] args) {
		boolean testPassed = true;
		Integer val = null;

		DroneStack<Integer> s = new DroneStack<Integer>(10, 40);

		// Test 1. Small stack, test push and peek
		try {
			for (int i = 0; i < 11; ++i) s.push(i);
			val = s.peek();
			if (val.intValue() != 10 || s.size() != 11) testPassed = false;	
		} catch (Exception e) {
			testPassed = false;
		}
		print(testPassed,1);
		
		// Test 2. Small stack. Test pop.
		testPassed = true;
		try {
			for (int i = 0; i < 5; ++i) val = s.pop();
			if (val.intValue() != 6 || s.isEmpty()) testPassed = false;
			DroneStack<String> stackString = new DroneStack<String>();
			stackString.push("item 1");
			String result = stackString.pop();
			if (!result.equals("item 1")) testPassed = false;
			stackString.pop();
			testPassed = false;
		} catch (EmptyStackException e) {
		} catch (Exception e1) {
			testPassed = false;
		}
		print(testPassed,2);
		
		// Test 3. Pop on an empty stack
		testPassed = true;
		try {
			for (int i = 0; i < 10; ++i) val = s.pop();
		} catch (EmptyStackException e) {
			try {
				val = s.peek();
				testPassed = false;
			}
			catch (EmptyStackException e1) {}
			catch (Exception e2) {testPassed = false;}
		} 
		catch (Exception e3) {
			testPassed = false;
		}
		print(testPassed,3);
		
		// Test 4. Test upper bound on size of stack
		DroneStack<String> stack = new DroneStack<String>(20, 359);
		testPassed = true;
		try {
			// Size of stack should be increased to 360, throwing an exception
			for (int i = 0; i < 360; ++i)
				stack.push("" + i);
			testPassed = false;
		} catch (OverflowException e) {} 
		catch (Exception e) {
			testPassed = false;
		}

		try {
			stack = new DroneStack<String>(30, 120);
			// Size of the stack should be increased to 290, not throwing an
			// exception
			for (int i = 0; i < 120; ++i)
				stack.push("" + i);
			if (stack.size() != 120) testPassed = false;
			stack.push("0");
			testPassed = false;
		} catch(OverflowException e1) {
		} catch (Exception e) {
				testPassed = false;
		}
		print(testPassed,4);
		
		// Test 5. Large stack, test push, pop, size
		testPassed = true;
		try {
			s = new DroneStack<Integer>();
			for (int i = 0; i < 960; ++i)
				s.push(i);

			if (s.size() != 960)
				testPassed = false;
			for (int i = 959; i >= 0; --i) {
				val = s.pop();
				if (val.intValue() != i) {
					testPassed = false;
					break;
				}
			}
		} catch (Exception e) {
			testPassed = false;
		}
		print(testPassed,5);
		
		try {
			String out, result = "";
			stack = new DroneStack<String>(10, 160);
			// Size of the stack should be increased to 290, not throwing an
			// exception
			stack.push("item 1");
			out = stack.toString();
			if (!out.equals("item 1")) testPassed = false;
			
			for (int i = 0; i < 50; ++i)
				stack.push("" + i);
			out = stack.toString();
			for (int i = 49; i >= 0; --i) result = result + i + ",";
			result = result + "item 1";
			if (!result.equals(out)) testPassed = false;
		} catch (Exception e) {
				testPassed = false;
		}
		print(testPassed,6);		
	}
	
	private static void print(boolean testPassed, int test) {
		if (testPassed) System.out.println("Test "+test+" passed");
		else System.out.println("Test "+test+" failed");
	}

}
