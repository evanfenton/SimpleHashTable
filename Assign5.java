import java.io.*;

// Demonstrates functionality of a hash table data structure
public class Assign5{

    // hash table for tests
    private HashTable hashTable;

    // stores records read from the input file for searching later
    private Vector<String> records;

    // hash table performance statistics
    private double loadFactor, hashEff, avgReadPerSec;
    private int maxReads;

    // constructor
    public Assign5(){
        hashTable= new HashTable(14887);
        records= new Vector<>();
    }

    // read the input file and insert the data into the hash table
    // with either linear or quadratic probing sequences
    public void readInputFile(String inputFile, boolean linear){

        try{
            FileReader reader= new FileReader(inputFile);
            BufferedReader bufferedReader= new BufferedReader(reader);

            String line;

            while((line= bufferedReader.readLine())!=null){

                if(linear)
                    hashTable.insertLinear(line);
                else
                    hashTable.insertQuadratic(line);

                records.add(line);
            }

            bufferedReader.close();

        }catch(FileNotFoundException ex){
            System.err.println("Error: input file was not found.");
        }catch(IOException ex){
            System.err.println("Error while reading input file.");
        }

    }

    // search the hash table for each record inserted
    // in order to measure hashing performance
    public void search(boolean linear){

        int totalReads=0, searchReads;
        double totalReadsD, numOfRecords= records.length();

        for(int i=0; i< numOfRecords; i++){

            if(linear)
                searchReads= hashTable.searchLinear(records.get(i));
            else
                searchReads= hashTable.searchQuadratic(records.get(i));

            totalReads += searchReads;

            if(searchReads > maxReads){
                maxReads= searchReads;
            }
        }

        totalReadsD= totalReads;

        avgReadPerSec= totalReadsD / numOfRecords;

        loadFactor= hashTable.getLoadFactor();

        hashEff= loadFactor / avgReadPerSec;
    }

    // print the results to a given output file
    public void printOutput(String outputFile){

        try {
            FileWriter write = new FileWriter(outputFile, false);
            PrintWriter print = new PrintWriter(write);

            print.println("Average number of reads per second: "+ avgReadPerSec+
                    "\n\nLoad Factor: "+ loadFactor+
                    "\n\nHashing Efficiency: "+ hashEff+
                    "\n\nLongest search chain: "+ maxReads);

            print.close();

        } catch(IOException e){
            System.out.println("There was a problem opening the output file.");
        }

    }

    public static void main(String [] args){

        Assign5 assign5= new Assign5();

        if(args[0].equals("-q")) {
            assign5.readInputFile(args[1], false);
            assign5.search(false);
            assign5.printOutput(args[2]);
        }
        else{
            assign5.readInputFile(args[0], true);
            assign5.search(true);
            assign5.printOutput(args[1]);
        }
    }
}