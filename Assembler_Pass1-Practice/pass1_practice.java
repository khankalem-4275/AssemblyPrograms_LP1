import java.io.*;

public class pass1_practice {
    public static void main(String[] args) throws Exception{
        FileReader fp = new FileReader("check.txt");
        String line = null;
        BufferedReader br = new BufferedReader(fp);
        int lc=0,linecount=0,symtabcount=0,littabcount=0,pooltabcount=0,optabcount=0;
        final int max = 50;
        int z = 0;

        final String reference[][] = {{"STOP","IS","00"},{"ADD","IS","01"},{"SUB","IS","02",},{"MULT","IS","03"},
    {"MOVER","IS","04"},{"MOVEM","IS","05"},{"COMP","IS","06"},{"BC","IS","07"},{"DIV","IS","08"},
{"READ","IS","09"},{"PRINT","IS","10"},{"DC","DL","01"},{"DS","DL","02"},{"START","AD","01"},{"END","AD","02"},
{"ORIGIN","AD","03"},{"EQU","AD","04"},{"LTORG","AD","05"}};

        int updt = 0;
        int flag = 0;
        int j;

        String symtab[][] = new String[max][3]; // symbol | address | length
        String optab[][] = new String[max][6]; // opcode | class | code | address | op1 | op2
        String littab[][] = new String[max][2]; // literal | address
        int pooltab[] = new int[max];

        while((line  = br.readline() != null)){
            String tokens[] = line.split("\t"); // split by tab character
            if(flag == 0 && tokens[1].equalsIgnoreCase("START") && linecount == 0){
                lc = Integer.parseInt(tokens[2]);
                z = lc;
                if(tokens.length >= 2 && !tokens[1].equals("")){
                    for(j=0;j<18;j++){
                        if(tokens[1].equalsIgnoreCase(reference[j][0])){
                            break;
                        }
                    }
                    if(j < 18){
                        optab[optabcount][0] = tokens[1]; // mnemonic
                        optab[optabcount][1] = reference[j][1]; // class
                        optab[optabcount][2] = reference[j][2]; // m/c code
                        optab[optabcount][3] = Integer.toString(lc);
                        optabcount++;
                    }
                }
                flag = 1;
                continue;
            }
            if(tokens.length == 3 && tokens[2].charAt(0) == "="){
                littab[littabcount][0] = tokens[2];
                littabcount++;
            }
            if(tokens.length >= 2 && tokens[1].equalsIgnoreCase("LTORG")){
                pooltab[pooltabcount++] = littabcount;
                lc--;
                for(int i=0;i<littabcount;i++){
                    littab[i][1] = Integer.toString(lc);
                    lc++;
                }
            }
            if(!tokens[0].equals("")){ // label identification
                symtab[symtabcount][0] = tokens[0];
                symtab[symtabcount][1] = Integer.toString(lc);
                if(tokens[1].equalsIgnoreCase("DS")){
                    symtab[symtabcount][2] = tokens[2];
                }
                else{
                    symtab[symtabcount][2] = "1";
                }
                if(tokens.length >= 2 && tokens[1].equalsIgnoreCase("EQU")){
                    for(j=0;j<symtabcount;j++){
                        if(tokens[2].equalsIgnoreCase(symtab[j][0])){
                            symtab[symtabcount][1] = symtab[j][1];
                        }
                    }
                }
                if(tokens.length >= 2 && tokens[1].equalsIgnoreCase("DS")){
                    lc = lc + Integer.parseInt(tokens[2]) - 1;
                }
                symtabcount++;
            }

            if(tokens.length >= 2 && !tokens[1].equals("")){
                if(tokens[1].equalsIgnoreCase("ORIGIN")){
                    String mn = tokens[2];
                    String dm = "";
                    for(int i=0;i<mn.length();i++){
                        if(mn.charAt(i) == '+'){
                            break;
                        }
                        else{
                            dm = dm + mn.charAt(i);
                        }
                    }
                    char aux = mn.charAt(mn.length() - 1);
                    int addr = 0;
                    for(int k=0;k<symtabcount;k++){
                        if(symtab[k][0].equals(dm)){
                            addr = Integer.parseInt(symtab[k][1]);
                            break;
                        }
                    }
                    optab[optabcount][0] = tokens[1]; // mnemonic
                    optab[optabcount][1] = reference[15][1]; // class
                    optab[optabcount][2] = reference[15][2]; // m/c code
                    optab[optabcount][3] = Integer.toString(lc);
                    optabcount++;
                    linecount++;
                    lc = addr + (aux - '0');
                    updt = lc;
                    continue;
                }
                for(j=0;j<18;j++){
                    if(tokens[1].equalsIgnoreCase(reference[j][0])){
                        break;
                    }
                }
                if(j < 18){
                    optab[optabcount][0] = tokens[1]; // mnemonic
                    optab[optabcount][1] = reference[j][1]; // class
                    optab[optabcount][2] = reference[j][2]; // m/c code
                    if(tokens.length > 3){
                        if(tokens[2].equalsIgnoreCase("AREG")){
                            optab[optabcount][4] = "1";
                        }
                        if(tokens[2].equalsIgnoreCase("BREG")){
                            optab[optabcount][4] = "2";
                        }
                        if(tokens[2].equalsIgnoreCase("CREG")){
                            optab[optabcount][4] = "3";
                        }
                        if(tokens[2].equalsIgnoreCase("DREG")){
                            optab[optabcount][4] = "4";
                        }
                        optab[optabcount][5] = tokens[3];
                    }
                    else if(tokens.length > 2){
                        optab[optabcount][4] = tokens[2];
                    }
                    if(!tokens[1].equalsIgnoreCase("LTORG")){
                        optab[optabcount][3] = Integer.toString(lc);
                    }
                    optabcount++;
                }
            }
            linecount++;
            lc++;
        }
        
}    
