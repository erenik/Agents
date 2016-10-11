/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fileapplication;

/**
 *
 * @author Arash
 */

import java.io.*;

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("Hi");
        SearchAndAppend("Arash Mousavi");
        
//        try {
//
//            Writer output = null;
//            String text = "Rajesh Kumar";
//            File file = new File("write.txt");
//            output = new BufferedWriter(new FileWriter(file));
//            output.write(text);
//            output.close();
//            System.out.println("Your file has been written");
//
//        } catch (IOException e) {
//        }



//        Writer output = null;
//   String text = "Rajesh Kumar";
//   File file = new File("write.txt");
//   output = new BufferedWriter(new FileWriter(file));
//   output.write(text);
//   output.close();
//   System.out.println("Your file has been written");
//
    }

    private static void SearchAndAppend(String name) {

        boolean found = false;
        try {
            BufferedReader in = new BufferedReader(new FileReader("write.txt"));
            String str;
            while ((str = in.readLine()) != null) {
                if (str.equalsIgnoreCase(name)) {
                    found = true;
                    System.out.println("Found Your Name:::" + str);
                    break;
                }
                System.out.println("Line Content:::" + str);
            }
            in.close();
        } catch (IOException e) {
        }

        if (found) {
            System.out.println("Record Alread Exists");
        } else {

            try {

                Writer output = null;
                String text = "\n" + name;
                File file = new File("write.txt");
                output = new BufferedWriter(new FileWriter(file, true));
                output.write(text);
                output.close();
                System.out.println("Your file has been written");

            } catch (IOException e) {
            }
        }
    }

}
