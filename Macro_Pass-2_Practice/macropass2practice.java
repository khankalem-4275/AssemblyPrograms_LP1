import java.util.*;
import java.io.*;

public class macropass2practice {
    public static void main(String[] args) throws IOException{
        BufferedReader b1 = new BufferedReader(new FileReader("intermediate.txt"));
        BufferedReader b2 = new BufferedReader(new FileReader("mnt.txt"));
        BufferedReader b3 = new BufferedReader(new FileReader("mdt.txt"));
        BufferedReader b4 = new BufferedReader(new FileReader("kpdt.txt"));

        FileWriter f1 = new FileWriter("result.txt");
        HashMap<Integer,String> aptab = new HashMap<Integer,String>();
        HashMap<String,Integer> aptabInverse = new HashMap<String,Integer>();
        HashMap<String,Integer> mdtpHash = new HashMap<String,Integer>();
        HashMap<String,Integer> kpdtpHash = new HashMap<String,Integer>();
        HashMap<String,Integer> kpHash = new HashMap<String,Integer>();
        HashMap<String,Integer> macronameHash = new HashMap<String,Integer>();
        Vector<String> mdt = new Vector<String>(); // macro-definition-table
        Vector<String> kpdt = new Vector<String>();

        String s,s1;
        int i,pp,kp,mdtp,kpdtp,paramNo;

        while((s = b3.readLine()) != null){
            mdt.addElement(s);
        }
        while((s = b4.readLine()) != null){
            kpdt.addElement(s);
        }
        while((s = b2.readLine()) != null){
            String word[] = s.split("\t");
            s1 = word[0] + word[1];
            macronameHash.put(word[0],1);
            kpHash.put(s1,Integer.parseInt(word[2]));
            mdtpHash.put(s1,Integer.parseInt(word[3]));
            kpdtpHash.put(s1,Integer.parseInt(word[4]));
        }

        while((s = b1.readLine()) != null){
            String b1Split[] = s.split("\\s");
            if(macronameHash.containsKey(b1Split[0])){
                pp = b1Split[1].split(",").length - b1Split[1].split("=").length +1;
                kp = kpHash.get(b1Split[0] + Integer.toString(pp));
                mdtp = mdtpHash.get(b1Split[0] + Integer.toString(pp));
                kpdtp = kpdtpHash.get(b1Split[0] + Integer.toString(pp));
                String actualParams[] = b1Split[1].split(",");
                paramNo = 1;
                for(int j=0;j<pp;j++)
                {
                    aptab.put(paramNo, actualParams[paramNo - 1]);
                    aptabInverse.put(actualParams[paramNo - 1],paramNo);
                    paramNo++;
                }
                i = kpdtp - 1;
                for(int j=0;j<kp;j++)
                {
                    String temp[] = kpdt.get(i).split("\t");
                    aptab.put(paramNo,temp[1]);
                    aptabInverse.put(temp[0],paramNo);
                    paramNo++;
                }
                i = pp + 1;
                while(i <= actualParams.length)
                {
                    String initialisedParams[] = actualParams[i-1].split("=");
                    aptab.put(aptabInverse.get(initialisedParams[0].substring(1,initialisedParams[0].length())),
                    initialisedParams[1].substring(0,initialisedParams[1].length()));
                    i++;
                }
                i = mdtp - 1;
                while(mdt.get(i).compareToIgnoreCase("MEND") != 0){
                    f1.write("+");
                    for(int j=0;j<mdt.get(i).length();j++){
                        if(mdt.get(i).charAt(j) == '#'){
                            f1.write(aptab.get(Integer.parseInt("" + mdt.get(i).charAt(++j))));
                        }
                        else
                        {
                            f1.write(mdt.get(i).charAt(j));
                        }
                    }
                    f1.write("\n");
                    i++;
                }
                aptab.clear();
                aptabInverse.clear();
            }
            else
            {
               f1.write("+" + s + "\n"); 
            }
        }
        b1.close(); // intermediate.txt
        b2.close(); // mnt.txt
        b3.close(); // mdt.txt
        b4.close(); // kpdt.txt
        f1.close();
    }
}
