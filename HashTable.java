
// implements a hash table data structure
public class HashTable{

    // stores the data
    private String [] table;

    // constructor
    public HashTable(int size){
        table= new String[size];
    }

    // insert the word into the hash table at address
    // given by the hash function if possible or,
    // linearly probe until finding an empty spot
    public void insertLinear(String element){

        int startIndex, index;

        startIndex= hashFunction(element);

        index= startIndex;

        if(table[index] != null) {
            if(index == table.length -1)
                index=0;
            else
                index++;
        }

        while(table[index] != null && index != startIndex){
            if(index == table.length -1) { index=0; }
            else{ index++; }
        }

        table[index]= element;
    }

    // search the hash table for a given element
    // that was inserted with linear probing
    public int searchLinear(String element){

        int startIndex, index, count= 0;

        startIndex= hashFunction(element);

        index= startIndex;

        do{
            count++;
            index %= table.length;

        }while (!table[index++].equals(element) && index != startIndex);

        return count;
    }

    // insert data but with quadratic probing instead
    // of linear
    public void insertQuadratic(String element){

        int startIndex, index, p=1;

        startIndex= hashFunction(element);

        index= startIndex;

        if(table[index] != null) {
            index = startIndex + quadratic(p);
            index %= table.length;
            p++;
        }

        while(table[index] != null && index!=startIndex){
            index = startIndex + quadratic(p);
            index %= table.length;
            p++;

            while(index < 0){ index += table.length; }
        }

        table[index]= element;
    }

    // search for an element inserted with
    // quadratic probing
    public int searchQuadratic(String element){

        int startIndex, index, p=1, count=0;

        startIndex= hashFunction(element);

        index= startIndex;

        if(!table[index].equals(element)) {
            index = startIndex + quadratic(p);
            index %= table.length;
            count++;

            while (table[index]!= null && !table[index].equals(element) &&
                    index != startIndex) {

                index = startIndex + quadratic(p);
                index %= table.length;
                p++;
                count++;

                while (index < 0) { index += table.length; }
            }

        }

        return ++count;
    }

    // quadratic probing function
    private int quadratic(int probe){
        return (int)((Math.pow(-1, probe-1))*(Math.pow((probe+1)/2, 2)));
    }

    // hash function used with the table
    private int hashFunction(String element){

        // uses an operation similar to fold and add
        int foldAdd=0;

        String str= changeCases(element);

        int [] asciiCodes= new int[element.length()];

        for(int i=0; i< str.length(); i++){
            asciiCodes[i]= str.charAt(i);
        }

        for(int i=0; i< str.length(); i++) {

            if (i % 2 == 0)
                foldAdd += asciiCodes[i] * 100;
            else
                foldAdd += asciiCodes[i] * 3;

            // random stuff that produced higher hashing efficiency
            if(i==3 || i==6 || i==5){
                foldAdd = reverseDigits(foldAdd);
            }
            else if(i==0){
                foldAdd += ((reverseDigits(foldAdd))>>7) | foldAdd;
            }
            else if(scramble(foldAdd) % 69 == 0 && i < str.length()-2) {
                foldAdd += asciiCodes[i] | asciiCodes[i+1];
            }
            else {
                foldAdd += ((reverseDigits(foldAdd))<<3) & foldAdd;
            }
        }

        return (((foldAdd^69)<<2) % table.length);
    }

    // get the number of records stored in the hash table
    private int getNumOfRecords(){
        int num=0;
        for(int i=0; i< table.length; i++) {
            if (table[i] != null) { num++; }
        }
        return num;
    }

    // get the load factor of the hash table
    public double getLoadFactor(){
        double tableLength= table.length;
        return (getNumOfRecords()/tableLength)*100;
    }

    // convert lowercase, odd indexed characters to uppercase
    private String changeCases(String str){

        char [] result= str.toCharArray();

        for(int i=0; i< str.length(); i++){
            if(i % 2 != 0 && (result[i] >= 'a' || result[i] <= 'z')){
                result[i] -= 32;
            }
        }
        return String.valueOf(result);
    }

    // reverse the digits of a given integer
    private int reverseDigits(int num){
        int revNum=0;
        Vector<Integer> digits= getDigits(num);

        for(int i= 0, j= digits.length()-1; i< digits.length(); i++, j--){
            revNum += (digits.get(i))*(Math.pow(10, j));
        }

        return revNum;
    }

    // returns a vector holding the individual digits of a given integer
    private Vector<Integer> getDigits(int num){
        int digit= num;
        Vector<Integer> digits= new Vector<>();

        while(digit > 0){
            digits.add(digit % 10);
            digit = digit /10;
        }

        return digits;
    }

    // performs some random operations on an integer
    // and returns the result
    private int scramble(int num){
        int scrambled=0;
        Vector<Integer> digits= getDigits(num);

        for(int i= 0, j=0; i< digits.length(); i++, j++){
            if(j == 3){
                j= digits.length()-2;
            }
            else if(j == 2){
                j= digits.length()-1;
            }
            else if(j==7){
                j= 5;
            }
            scrambled += (digits.get(i))*(Math.pow(10, j));
        }

        return scrambled;
    }

}