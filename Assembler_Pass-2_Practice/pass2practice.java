import java.io.*;
import java.util.*;

class func{
    void display(String array[][],int m,int n){
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                System.out.print(array[i][j] + "\t");
            }
            System.out.println();
        }
    }

    int programmatrix(String array[][],int m,String filename){
        String s;
        BufferedReader br = null;
        File file = new File(filename);
        int i = 0;
        try{
            br = new BufferedReader(new FileReader(file));
            while((s = br.readLine()) != null){
                String result[] = s.split("\t");
                for(int j=0;j<m;j++){
                    array[i][j] = result[j];
                }
                i++;
            }
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return i;
    }

    int machinecode(String[][] intermediate,int n,String[][] symtab,int sn,String[][] littab,int ln,String[][] machinecode){
        int flag=0,k=0;
        for(int i=0;i<n;i++){
            flag = 0;
            if(intermediate[i][1].contains("AD") || intermediate[i][1].contains("DL,01")){
                flag = 1;
            }
            else
            {
                machinecode[k][0] = "+";
                if(intermediate[i][1].contains("DL")){
                    machinecode[k][1] = "00";
                    machinecode[k][2] = "00";
                    String[] temp = intermediate[i][2].split("\\,");
                    temp[1] = temp[1].replace(")", "");
                    machinecode[k][3] = temp[1];
                }
                else
                {
                    for(int j=1;j<3;j++)
                    {
                        if(intermediate[i][j].compareTo(" ") != 0){
                            String[] temp = intermediate[i][j].split("\\,");
                            temp[1] = temp[1].replace(")", "");
                            machinecode[k][j] = temp[1];
                        }    
                        else{
                            machinecode[k][j] = " ";
                        }
                    }
                    // address field third column
                    if(intermediate[i][3].contains("S")){
                        String[] temp = intermediate[i][3].split("\\,");
                        temp[1] = temp[1].replace(")", "");
                        int index = Integer.parseInt(temp[1]);
                        machinecode[k][3] = symtab[index][1];
                    }
                    else if (intermediate[i][3].contains("L")){
                        String[] temp = intermediate[i][3].split("\\,");
                        temp[1] = temp[1].replace(")", "");
                        int index = Integer.parseInt(temp[1]);
                        machinecode[k][3] = littab[index][1];
                    }
                    else
                    {
                        machinecode[k][3] = " ";
                    }
                }
                k++;
            }
        }
        return k;
    }
}

public class pass2practice {
    public static void main(String[] args) throws Exception{
        func f = new func();
        int n=0,sn=0,ln=0,m=0;
        String[][] intermediate = new String[100][4]; // class | registers | symbol/label | symbol/label_address
        String[][] symtab = new String[10][3]; // symbol | address | length
        String[][] littab = new String[100][2]; // literal | address
        String[][] machinecode = new String[100][4]; // '+' | imperative statement | registers | symbol/label_address
        int[] pooltab = new int[10];
        String[][] BC = {{"LT","01"},{"LE","02"},{"EQ","03"},{"GT","04"},{"GE","05"},{"ANY","06"}};

        Scanner input = new Scanner(System.in);

        System.out.println("=========================================");
        System.out.println("     Intermediate Code      ");
        System.out.println("-------------------------------------------");
        n = f.programmatrix(intermediate, 4, "intermediate.txt");
        f.display(intermediate, n, 4); // n is the number of rows
        System.out.println("=========================================");
        System.out.println();
        System.out.println("=========================================");
        System.out.println("     Symbol Table      ");
        System.out.println("-------------------------------------------");
        sn = f.programmatrix(symtab, 3, "symbol.txt");
        f.display(symtab, sn, 3); // sn is the number of rows
        System.out.println("=========================================");
        System.out.println();
        System.out.println("=========================================");
        System.out.println("     Literal Table     ");
        System.out.println("-------------------------------------------");
        ln = f.programmatrix(littab, 2, "literal.txt");
        f.display(littab, ln, 2);
        System.out.println("=========================================");
        System.out.println();
        System.out.println("=========================================");
        System.out.println("        Machine Code       ");
        System.out.println("-------------------------------------------");

        m = f.machinecode(intermediate, n, symtab, sn, littab, ln, machinecode);
        f.display(machinecode, m, 4);
        System.out.println("=========================================");
    }
}
