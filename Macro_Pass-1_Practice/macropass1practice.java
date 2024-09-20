import java.util.*;
import java.io.*;
import java.io.FileWriter;
import java.io.FileReader;

public class macropass1practice {
    public static void main(String[] args) throws IOException {
    BufferedReader b1 = new BufferedReader(new FileReader("input.txt"));
    FileWriter f1 = new FileWriter("intermediate.txt");
    FileWriter f2 = new FileWriter("kpdt.txt");
    FileWriter f3 = new FileWriter("mdt.txt");
    FileWriter f4 = new FileWriter("mnt.txt");
    HashMap<String,Integer> pntab = new HashMap<String,Integer>();
    String s;
    int paramNo=1,pp=0,kp=0,kpdtp=0,mdtp=1,flag=0;
    while((s=b1.readLine()) != null){
        String word[] = s.split("\\s");
        if(word[0].compareToIgnoreCase("MACRO") == 0){
            flag=1; // "MACRO" has been encountered
            if(word.length <= 2){
                f4.write(word[1] + "\t" + pp + "\t" + kp + "\t" + mdtp + "\t" + (kp == 0 ? kpdtp:(kpdtp + 1)) + "\n");
                continue;
            }
            String params[] = word[2].split(",");
            for(int i=0;i<params.length;i++){
                if(params[i].contains("=")){
                   kp++;
                   String keywordParams[] = params[i].split("=");
                   pntab.put(keywordParams[0].substring(1,keywordParams.length),paramNo++);
                   if(keywordParams.length == 2){
                        f2.write(keywordParams[0].substring(1,keywordParams.length) + "\t" + keywordParams[1] + "\n");
                   }
                   else{
                    f2.write(keywordParams[0].substring(1,keywordParams.length) + "\t" + "-" + "\n");
                   }
                }
                else{
                    pntab.put(params[i].substring(1,params[i].length()),paramNo++);
                    pp++;
                }
            }
            f4.write(word[1] + "\t" + pp + "\t" + kp + "\t" + mdtp + "\t" + (kp == 0 ? kpdtp:(kpdtp+1)) + "\n");
            kpdtp = kpdtp + kp;
        }
        else if(word[0].compareToIgnoreCase("MEND") == 0){
            f3.write(s + "\n");
            flag=pp=kp=0;
            mdtp++;
            paramNo = 1;
            pntab.clear();
        }
        else if (flag == 1){
            for(int i=0;i<s.length();i++){
                if(s.charAt(i) == '&'){
                    i++;
                    String temp = "";
                    while(!(s.charAt(i) == ' ' || s.charAt(i) == ',')){
                        temp = temp + s.charAt(i++);
                        if(i == s.length()){
                            break;
                        }
                    }
                    i--;
                    f3.write("#" + pntab.get(temp));
                }
                else{
                    f3.write(s.charAt(i));
                }
            }
            f3.write("\n");
            mdtp++;
        }
        else{
            f1.write(s + "\n");
        }
    }
    b1.close();
    f1.close();
    f2.close();
    f3.close();
    f4.close();
}}
