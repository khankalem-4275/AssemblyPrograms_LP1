import java.io.*;

class Pass {
    public static void main(String args[]) throws Exception {
        FileReader Fp = new FileReader("check1.txt");
        String line = null;
        BufferedReader br = new BufferedReader(Fp);
        int LC = 0, LineCount = 0, SymTabCount = 0, LitTabCount = 0, PoolTabCount = 0, OpTabCount = 0;
        final int MAX = 50;
        int z = 0;
        final String reference[][] = { { "STOP", "IS", "00" }, { "ADD", "IS", "01" }, { "SUB", "IS", "02" },
                { "MULT", "IS", "03" }, { "MOVER", "IS", "04" }, { "MOVEM", "IS", "05" }, { "COMP", "IS", "06" },
                { "BC", "IS", "07" }, { "DIV", "IS", "08" }, { "READ", "IS", "09" }, { "PRINT", "IS", "10" },
                { "DC", "DL", "01" }, { "DS", "DL", "02" }, { "START", "AD", "01" }, { "END", "AD", "02" },
                { "ORIGIN", "AD", "03" }, { "EQU", "AD", "04" }, { "LTORG", "AD", "05" } };

        int updt = 0;
        int flag = 0;
        int j;
        String SymTab[][] = new String[MAX][3]; // symbol | address | length
        String OpTab[][] = new String[MAX][6]; // opcode | class | code | address | op1 | op2
        String LitTab[][] = new String[MAX][2]; // literal | address
        int PoolTab[] = new int[MAX]; // ltorg next address

        while ((line = br.readLine()) != null) {
            String tokens[] = line.split("\t"); // split by tab character.
            if (flag == 0 && tokens[1].equalsIgnoreCase("START") && LineCount == 0) {
                LC = Integer.parseInt(tokens[2]);
                z = LC;
                if (tokens.length >= 2 && !tokens[1].equals("")) {
                    for (j = 0; j < 18; j++) {
                        if (tokens[1].equalsIgnoreCase(reference[j][0])) {
                            break;
                        }
                    }
                    if (j < 18) {
                        OpTab[OpTabCount][0] = tokens[1];
                        OpTab[OpTabCount][1] = reference[j][1];
                        OpTab[OpTabCount][2] = reference[j][2];
                        OpTab[OpTabCount][3] = Integer.toString(LC); // assignment of address.
                        OpTabCount++;
                    }
                }
                flag = 1; // start has been encountered.
                continue;
            }

            if (tokens.length == 3 && tokens[2].charAt(0) == '=') {
                LitTab[LitTabCount][0] = tokens[2];
                // LitTab[LitTabCount][1] = Integer.toString(LC);
                LitTabCount++;
            }

            if (tokens.length >= 2 && tokens[1].equalsIgnoreCase("LTORG")) {
                PoolTab[PoolTabCount++] = LitTabCount;
                for(int i=0;i<LitTabCount;i++){
                    LitTab[i][1] = Integer.toString(LC);
                    LC++;
                }
                LC--;
            }
            
            if (!tokens[0].equals("")) { // label identification
                SymTab[SymTabCount][0] = tokens[0];
                SymTab[SymTabCount][1] = Integer.toString(LC);
                if (tokens[1].equalsIgnoreCase("DS")) {
                    SymTab[SymTabCount][2] = tokens[2];
                } else {
                    SymTab[SymTabCount][2] = "1";
                }

                if (tokens.length >= 2 && tokens[1].equalsIgnoreCase("EQU")) {
                    for (j = 0; j < SymTabCount; j++) {
                        if (tokens[2].equalsIgnoreCase(SymTab[j][0])) {
                            SymTab[SymTabCount][1] = SymTab[j][1];
                        }
                    }
                }

                if (tokens.length >= 2 && tokens[1].equalsIgnoreCase("DS")) {
                    LC = LC + Integer.parseInt(tokens[2]) - 1;
                }

                SymTabCount++;
            }

            if (tokens.length >= 2 && !tokens[1].equals("")) {
                if (tokens[1].equalsIgnoreCase("ORIGIN")) {
                    // LC = Integer.parseInt(tokens[1]);
                    // continue;
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
                    char aux = mn.charAt(mn.length()-1);
                    // System.out.println(aux);
                    int addr = 0;
                    for(int k=0;k<SymTabCount;k++){
                        if(SymTab[k][0].equals(dm)){
                            addr = Integer.parseInt(SymTab[k][1]);
                            break;
                        }
                    }
                    OpTab[OpTabCount][0] = tokens[1];
                    OpTab[OpTabCount][1] = reference[15][1];
                    OpTab[OpTabCount][2] = reference[15][2];
                    OpTab[OpTabCount][3] = Integer.toString(LC);
                    OpTabCount++;
                    LineCount++;
                    LC = addr + (aux - '0');
                    updt = LC;
                    continue;
                }
                for (j = 0; j < 18; j++) {
                    if (tokens[1].equalsIgnoreCase(reference[j][0])) {
                        break;
                    }
                }
                if (j < 18) {
                    OpTab[OpTabCount][0] = tokens[1]; // mnemonic
                    OpTab[OpTabCount][1] = reference[j][1]; // class
                    OpTab[OpTabCount][2] = reference[j][2]; // machine code
                    if (tokens.length > 3) {
                        if (tokens[2].equalsIgnoreCase("AREG")) {
                            OpTab[OpTabCount][4] = "1";
                        }
                        if (tokens[2].equalsIgnoreCase("BREG")) {
                            OpTab[OpTabCount][4] = "2";
                        }
                        if (tokens[2].equalsIgnoreCase("CREG")) {
                            OpTab[OpTabCount][4] = "3";
                        }
                        if (tokens[2].equalsIgnoreCase("DREG")) {
                            OpTab[OpTabCount][4] = "4";
                        }
                        OpTab[OpTabCount][5] = tokens[3];
                    } else if (tokens.length > 2) {
                        OpTab[OpTabCount][4] = tokens[2]; 
                    }
                    if (!tokens[1].equalsIgnoreCase("LTORG")) {
                        OpTab[OpTabCount][3] = Integer.toString(LC);
                    }
                    OpTabCount++;
                }
            }
            LineCount++;
            LC++;
        } // while

        // int in;
        // for (int k = 0; k < PoolTabCount; k++) {
        //     in = 0;
        //     for (int l = 0; l < LitTabCount; l++) {
        //         System.out.print(PoolTab[k] + " " + LitTab[l][1]);
        //         System.out.println();
        //         if(LitTab[l][1] != null){
        //             if (PoolTab[k] == Integer.parseInt(LitTab[l][1])) {
        //                 in = 1;
        //                 PoolTab[k] = l + 1;
        //             }
        //         }
        //     }
        //     if(in == 0) PoolTab[k] = 0;
        // }

        System.out.println();
        System.out.println("_________________");

        // Print symbol table
        System.out.println("\n\n   SYMBOL TABLE        ");
        System.out.println("--------------------------");
        System.out.println("SYMBOL\tADDRESS\tLENGTH");
        System.out.println("--------------------------");
        for (int i = 0; i < SymTabCount; i++) {
            System.out.println(SymTab[i][0] + "\t" + SymTab[i][1] + "\t" + SymTab[i][2]);
        }
        System.out.println("--------------------------");

        // Print opcode table
        System.out.println("\n\n   OPCODE TABLE        ");
        System.out.println("----------------------------");
        System.out.println("MNEMONIC\tCLASS\tINFO");
        System.out.println("----------------------------");
        for (int i = 0; i < OpTabCount; i++) {
            System.out.println(OpTab[i][0] + "\t\t" + OpTab[i][1] + "\t" + OpTab[i][2]);
        }
        System.out.println("----------------------------");

        // Print literal table
        System.out.println("\n\n   LITERAL TABLE        ");
        System.out.println("-----------------");
        System.out.println("LITERAL\tADDRESS");
        System.out.println("-----------------");
        for (int i = 0; i < LitTabCount; i++) {
            System.out.println(LitTab[i][0] + "\t" + LitTab[i][1]);
        }
        System.out.println("------------------");

        System.out.println("\n\n   POOL TABLE        ");
        System.out.println("-----------------");
        System.out.println("LITERAL NUMBER");
        System.out.println("-----------------");
        for (int i = 0; i < PoolTabCount; i++) {
            System.out.println("  #" + PoolTab[i]);
        }
        System.out.println("------------------");

        System.out.print("\n===========================");
        System.out.print("\nIntermediate Code");
        System.out.print("\n    (" + OpTab[0][1] + "," + OpTab[0][2] + ")  (C," + OpTab[0][3] + ")");
        int s = 1, a = 0;
        int pt_helper = 0;

        while (s < OpTabCount) {
            if (OpTab[s][3].equals(Integer.toString(z))) {
                if (OpTab[s][0].equals("DC")) {
                    a = 0;
                    while (!OpTab[s][4].equals(SymTab[a][0])) {
                        a++;
                    }
                    System.out.print("\n" + OpTab[s][3] + ")(" + OpTab[s][1] + "," + OpTab[s][2] + ")   (S," + a
                            + ") (C," + OpTab[s++][5] + ")");
                }
                if (OpTab[s][0].equals("DS")) {
                    System.out.print("\n" + OpTab[s][3] + ")(" + OpTab[s][1] + "," + OpTab[s][2] + ")  (C,"
                            + OpTab[s++][4] + ")");
                }
                if (OpTab[s][0].equals("DC")) {
                    System.out.print("\n" + OpTab[s][3] + ")(" + OpTab[s][1] + "," + OpTab[s][2] + ")  (C,"
                            + OpTab[s++][4] + ")");
                }
                if (OpTab[s][0].equals("END")) {
                    System.out.print("\n" + OpTab[s][3] + ")(" + OpTab[s][1] + "," + OpTab[s][2] + ")");
                    System.out.print("\n===========================\n");
                    System.exit(0);
                }
                if (OpTab[s][0].equals("ORIGIN")) {
                    System.out.print("\n" + OpTab[s][3] + ")(" + OpTab[s][1] + "," + OpTab[s++][2] + ")");
                    z = updt;
                    continue;
                }

                if (OpTab[s][0].equals("READ")) {
                    a = 0;
                    while (!OpTab[s][4].equals(SymTab[a][0])) { // optab[s][4] is the operand-1
                        a++;
                    }
                    System.out.print("\n" + OpTab[s][3] + ")(" + OpTab[s][1] + "," + OpTab[s++][2] + ")  (S," + a + ")");
                } else {
                    a = 0;
                    while (!OpTab[s][5].equals(SymTab[a][0])) { // optab[s][5] is the operand-2
                        a++;
                    }
                    System.out.print("\n" + OpTab[s][3] + ")(" + OpTab[s][1] + "," + OpTab[s][2] + ")  (" + OpTab[s][4]
                            + ") (S," + a + ")");
                    s++;
                }
                if (OpTab[s][0].equals("LTORG")) {
                    pt_helper++;
                    for(int i=0;i<pt_helper;i++){
                        for(int y=0;y<PoolTab[i];y++){
                            System.out.print("\n" + LitTab[y][1] + ")" + "(" + OpTab[s][1] + "," + OpTab[s][2] + ")" + "  (DL,01)" + "\t(C," + LitTab[y][0] + ")");
                            OpTab[s][3] = "0"; // address in the opcode table.
                        }
                    }
                    s++;
                }
            }
            z++;
        }
        br.close();
    }
}