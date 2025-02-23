/**
 * This class represents the hex cells that the drone will fly through in a Stack ADT, implemented with an array
 * data structure
 * 
 * @param <T> The generic type of the data stored in the stack
 * 
 * @author Jairdan C
 * @version 1.2
 * @since Nov 18, 2024
 */
public class DroneStack<T> implements StackADT<T> {

	//INSTANCE VARIABLES
	/**
	 * The array that will store the data items of the stack, the first data item is
	 * stored at index position 0
	 */
	private T[] arrayStack;
	/**
	 * The number of data items stored in the stack
	 */
	private int count;
	/**
	 * The maximum size of the array stack, if this limit is reached or exceeded an OverflowException will be thrown
	 */
	private int maximumCapacity;
	
	
	//CONSTRUCTORS
	/**
	 * The default constructor without any specifications made on the initial or max capacities, creates an empty stack, default values are initial capacity: 10, max capacity: 1000
	 */
	@SuppressWarnings("unchecked")
	public DroneStack() {

		this.arrayStack = (T[]) new Object[10];
		this.maximumCapacity = 1000;
		this.count = 0;

	}

	/**
	 * The constructor which allows the specifications of the initial and max capacities, creates an empty stack
	 * @param intitalCapacity The size of the initial array stack
	 * @param maxCap The maximum allowed size of the array stack, used for memory safety and prevention of infinite loops
	 */
	@SuppressWarnings("unchecked")
	public DroneStack(int intitialCapacity, int maxCap) {

		this.arrayStack = (T[]) new Object[intitialCapacity];
		this.maximumCapacity = maxCap;
		this.count = 0;

	}

	//PUBLIC METHODS
	
	/**
	 * Adds data item to the top of the stack and increments size by one. If the array is full a expanded array will be created seeing that it does not exceed the maximum capacity of the DroneStack object
	 * @param dataItem The item added to the top of the stack
	 * @throws OverflowException The exception thrown if the array exceeds the maximum capacity
	 */
	public void push(T dataItem) throws OverflowException {
		try {
			arrayStack[count] = dataItem;
			count++;
		} catch (ArrayIndexOutOfBoundsException e) {
			this.expandStack(); //Call to the private function to expand the array
			this.push(dataItem); //Try to push the item again
		}
	}

	/**
	 * Removes and returns the item at the top of the stack. If the array is empty an exception will be thrown
	 * @return The item which was removed from the top of the stack, of generic type T
	 * @throws EmptyStackException The exception thrown if the array is empty
	 */
	public T pop() throws EmptyStackException {
		T temp;
		if (this.isEmpty()) {
			throw new EmptyStackException("Cannot make pop() call to stack: Stack is empty");
		} else {
			count--;
			temp = arrayStack[count];
			arrayStack[count] = null;
			return temp;
		}
	}

	/**
	 * Returns the item at the top of the stack without removing it. If the array is empty an exception will be thrown
	 * @return The item which is at the top of the stack, of generic type T
	 * @throws EmptyStackException The exception thrown if the array is empty
	 */
	public T peek() throws EmptyStackException {
		if (this.isEmpty()) {
			throw new EmptyStackException("Cannot make peek() call to stack: Stack is empty");
		} else {
			return arrayStack[count - 1];
		}
	}

	/**
	 * Returns whether the stack is empty
	 * @return boolean value of true if the stack is empty, false otherwise
	 */
	public boolean isEmpty() {
		if (count == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Returns the number of items in the stack
	 * @return int value equal to the number of items in the stack
	 */
	public int size() {
		return count;
	}

	/**
	 * Returns the items of the stack as a String separated by a comma, in the order of top to bottom
	 * @return String containing the items of the stack as a String separated by a comma, in the order of top to bottom
	 * @throws EmptyStackException
	 */
	public String toString() throws EmptyStackException {

		String returnString = "";
		
		if (this.isEmpty()) {
			throw new EmptyStackException("Cannot make toString() call to stack: Stack is empty");
		} else {
			for (int i = count - 1; i >= 0; i--) {
				returnString += arrayStack[i] + ",";
			}
			returnString = returnString.substring(0, returnString.length()-1); //removing the last comma of the string
			return returnString;
		}

	}
	
	//HELPER or PRIVATE METHODS
	
	/**
	 * A private method to expand the array
	 * @throws OverflowException If the capacity of the new array exceeds the maximum capacity limit of the DroneStack object
	 */
	@SuppressWarnings("unchecked")
	private void expandStack() throws OverflowException {

		T[] newArrayStack;

		if (arrayStack.length < 50) {
			newArrayStack = (T[]) new Object[arrayStack.length * 4];
		} else {
			newArrayStack = (T[]) new Object[arrayStack.length + 50];
		}

		if (newArrayStack.length > maximumCapacity) {
			throw new OverflowException("Cannot make push() call to stack: Memory limit exceeded by stack");
		}

		for (int i = 0; i < arrayStack.length; i++) {
			newArrayStack[i] = arrayStack[i];
		}
		arrayStack = newArrayStack;

	}

}
