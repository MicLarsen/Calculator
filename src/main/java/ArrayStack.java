/**
 *
 * @author Michael
 */
public class ArrayStack<T> {

    private T[] data = null;
    private int top = 0;

    public ArrayStack(int size) {
        data = (T[]) new Object[size];
    }

    public void push(T element) {
        data[top++] = element;
    }

    public T pop() {
        if(top == 0 ) {
            return null;
        }
        top--;
        return data[top]; // got sideeffects
    }

    public boolean isEmpty() {
        return top == 0;
    }
    
    public T peek() {
          if(top == 0 ) {
            return null;
        }
        return data[top-1]; // no side effects
    }
    
    public int size() {
        return data.length;
    }
    
    public T print(int i) {
        return data[i];
    }
    
    
}
