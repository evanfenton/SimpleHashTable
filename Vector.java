
//implements a generic vector data structure
//where an array can be extended or shrunk
public class Vector<T> implements Cloneable{

    //array for holding values
    private T [] vector;

    //length of the vector
    private int length;

    //constructor
    public Vector(){
        vector= (T[])new Object[1];
        length= 0;
    }

    //extend the array and add the specified element to the end
    public void add(T added){

        T [] newVector= (T[])new Object[length+1];
        int i;

        if(length==0){
            this.vector[0]= added;
            length++;
            return;
        }

        for(i=0; i<length; i++){
            newVector[i]= this.vector[i];
        }

        newVector[i]= added;
        this.vector= newVector;
        length++;
    }

    //return the element at the specified index
    public T get(int index){
        if(index >= length || index<0)
            return null;
        else
            return vector[index];
    }

    public void remove(T rm){

        T [] newVector= (T[])new Object[length-1];
        int i= indexOf(rm);

        for(int j=0; j < length; j++){
            if(j<i)
                newVector[j]= vector[j];
            else
                newVector[j]= vector[j+1];
        }

        vector= newVector;
        length--;
    }

    //remove the element at the specified index and all elements after
    public void removeAllAfter(int start){

        if(start==0){
            vector= (T[])new Object[1];
            length= 0;
            return;
        }

        T [] newVector= (T[])new Object[start];

        for(int i=0; i < start; i++)
            newVector[i]= vector[i];

        vector= newVector;
        length= start;
    }

    //get the index of a specified element
    public int indexOf(T obj){
        for(int i= 0; i<length; i++){
            if(obj == vector[i])
                return i;
        }

        return -1;
    }

    //get the current length of the vector
    public int length(){
        return length;
    }

    //return true if the vector contains the specified value
    //or false if not
    public boolean contains(T val){

        for(int i=0; i<length; i++){
            if(vector[i] == val)
                return true;
        }

        return false;
    }

    //clone the vector
    @Override
    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }
}