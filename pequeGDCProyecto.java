import java.io.*;
//SLR1 EXPRESIÓN GRAMATICA
import static java.lang.System.exit;

public class pequeGeneradorEXAR {
    static String a, RENGLON, LEX;
    static String Entrada, Salida;
    static int Posicion = 0;

    static String[] t = new String[31];
    static String[] nt = new String[17];
    static String[] pi = new String[37];
    static int[] lpd = new int[37];
    static int[][] m = new int[68][48];
    static String[] PROD = new String[37];
    static String[] pila = new String[1000];
	static float[] A_r = new float [200];
	static float[] B_r = new float [200];
	static float[] M_r = new float [200];
	
	static int A_t=-1; //topes para arreglos A_r...
	static int B_t=-1;
	static int M_t=-1;
	static float temp1;
	static float aux;
    static int tope = -1;
    static String X;

    static int MP_res;
	static int M_res;
    static int B_res;
    static int A_res;
	static int num_res;
    static int var;

    public static void lee_token(File xFile) {
        try {
            FileReader fr = new FileReader(xFile);
            BufferedReader br = new BufferedReader(fr);
            long NoSirve = br.skip(Posicion);
            String linea = br.readLine();
            Posicion = Posicion + linea.length() + 2;
            a = linea;
            linea = br.readLine();
            Posicion = Posicion + linea.length() + 2;
            LEX = linea;
            linea = br.readLine();
            Posicion = Posicion + linea.length() + 2;
            RENGLON = linea;
            fr.close();
            //System.out.print(".");

        } catch (IOException e) {
            System.out.println("Errorzote");
        }
    }

    public static void error(){
        System.out.println(" ! ERROR: En el token [" + a + "] cerca del renglón " +RENGLON +"\n");
        exit(4);
    }

    public static File xArchivo(String xName) {
        File xFile = new File(xName);
        return xFile;
    }

    public static String pausa() {
        BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in));
        String nada = null;
        try {
            nada = entrada.readLine();
            return (nada);
        } catch (Exception e) {
            System.err.println(e);
        }
        return ("");
    }

    public static int terminal(String X){
        for(int i = 0; i < t.length; i++){
            if (X.equals(t[i])){
                return i;
            }
        }
        return -1;
    }

    public static int noterminal(String X){
        for(int i = 0; i < nt.length; i++){
            if (X.equals(nt[i])){
                return i + 8; //?
            }
        }
        return -1;
    }

    public static void push(String x){
        if(tope >= 9999){
            System.out.println("Error: pila llena");
            exit(4);
        }
        if(!x.equals("epsilon")){
            pila[++tope] = x;
        }
    }

    public static String pop(){
        if(tope < 0){
            System.out.println("Error: pila vacia");
            exit(4);
        }
        return pila[tope--];
    }

    public static void printPila(){
        System.out.println("Pila --> ");
        for(int i = 0; i <= tope; i++){
            System.out.println(pila[i] + " ");
        }
        System.out.println("");
        pausa();
    }
	
	/*public static void pushA(float x){
        if(A_t==200){
            System.out.println("Pila llena");
            System.exit(4);
        }
        A_r[++A_t] = x;
    }

    public static void pushB(float x){
        if(B_t==200){
            System.out.println("Pila llena");
            System.exit(4);
        }
        B_r[++B_t] = x;
    }

    public static void pushM(float x){
        if(M_t==200){
            System.out.println("Pila llena");
            System.exit(4);
        }
        M_r[++M_t] = x;
    }

    public static float popA(){
        if(A_t==-1){
            System.out.println("Pila vacia");
            System.exit(4);
        }
        return A_r[A_t--];
    }

    public static float popB(){
        if(B_t==-1){
            System.out.println("Pila vacia");
            System.exit(4);
        }
        return B_r[B_t--];
    }

    public static float popM(){
        if(M_t==-1){
            System.out.println("Pila vacia");
            System.exit(4);
        }
        return M_r[M_t--];
    }
*/
    public static void main(String [] args){
        Entrada = args[0] +".anl";
        if(!xArchivo(Entrada).exists()){
            System.out.println("\n\nERROR: El archivo no existe [" +Entrada +"]");
            exit(4);
        }

        t[0]="datos";
		t[1]="fin_datos";
		t[2]=":";
		t[3]="id";
		t[4]=",";
		t[5]="{";
		t[6]="}";
		t[7]="entero";
		t[8]="decimal";
		t[9]="cierto";
		t[10]="(";
		t[11]=")";
		t[12]="haz";
		t[13]="falso";
		t[14]="fin_cond";
		t[15]="mientras";
		t[16]="fin_mientras";
		t[17]="asig";
		t[18]="+";
		t[19]="-";
		t[20]="*";
		t[21]="/";
		t[22]="ent";
		t[23]="dec";
		t[24]="may";
		t[25]="men";
		t[26]="mayi";
		t[27]="meni";
		t[28]="igual";
		t[29]="dif";
		t[30]="fin";


        nt[0]="PROGP";
		nt[1]="PROG";
		nt[2]="DATSEC";
		nt[3]="VARS";
		nt[4]="LISTA";
		nt[5]="PRIN";
		nt[6]="TIPO";
		nt[7]="BLQ";
		nt[8]="INST";
		nt[9]="COND";
		nt[10]="CICLO";
		nt[11]="ASIG";
		nt[12]="EXP";
		nt[13]="E";
		nt[14]="F";
		nt[15]="S";
		nt[16]="OP";

        pi[0]="PROGP";
		pi[1]="PROG";
		pi[2]="DATSEC";
		pi[3]="DATSEC";
		pi[4]="VARS";
		pi[5]="VARS";
		pi[6]="LISTA";
		pi[7]="LISTA";
		pi[8]="PRIN";
		pi[9]="TIPO";
		pi[10]="TIPO";
		pi[11]="BLQ";
		pi[12]="BLQ";
		pi[13]="INST";
		pi[14]="INST";
		pi[15]="INST";
		pi[16]="COND";
		pi[17]="COND";
		pi[18]="CICLO";
		pi[19]="ASIG";
		pi[20]="EXP";
		pi[21]="E";
		pi[22]="E";
		pi[23]="E";
		pi[24]="F";
		pi[25]="F";
		pi[26]="F";
		pi[27]="S";
		pi[28]="S";
		pi[29]="S";
		pi[30]="S";
		pi[31]="OP";
		pi[32]="OP";
		pi[33]="OP";
		pi[34]="OP";
		pi[35]="OP";
		pi[36]="OP";

        lpd[0]=1;
		lpd[1]=2;
		lpd[2]=3;
		lpd[3]=0;
		lpd[4]=4;
		lpd[5]=3;
		lpd[6]=3;
		lpd[7]=1;
		lpd[8]=3;
		lpd[9]=1;
		lpd[10]=1;
		lpd[11]=2;
		lpd[12]=1;
		lpd[13]=1;
		lpd[14]=1;
		lpd[15]=1;
		lpd[16]=9;
		lpd[17]=7;
		lpd[18]=6;
		lpd[19]=3;
		lpd[20]=3;
		lpd[21]=3;
		lpd[22]=3;
		lpd[23]=1;
		lpd[24]=3;
		lpd[25]=3;
		lpd[26]=1;
		lpd[27]=1;
		lpd[28]=1;
		lpd[29]=1;
		lpd[30]=3;
		lpd[31]=1;
		lpd[32]=1;
		lpd[33]=1;
		lpd[34]=1;
		lpd[35]=1;
		lpd[36]=1;
	
		
        for(int i = 0; i < M.length; i++){
            for(int j = 0; j< M[0].length; j++){
                M[i][j] = 0;
            }
        }

        // shifts
        m[0][0]=3;
		m[2][5]=5;
		m[3][7]=8;
		m[3][8]=9;
		m[5][9]=15;
		m[5][3]=16;
		m[5][15]=17;
		m[6][1]=18;
		m[7][2]=19;
		m[10][6]=20;
		m[11][9]=15;
		m[11][3]=16;
		m[11][15]=17;
		m[15][10]=22;
		m[16][17]=23;
		m[17][10]=24;
		m[19][3]=26;
		m[22][22]=31;
		m[22][23]=32;
		m[22][3]=33;
		m[22][10]=34;
		m[23][22]=31;
		m[23][23]=32;
		m[23][3]=33;
		m[23][10]=34;
		m[24][22]=31;
		m[24][23]=32;
		m[24][3]=33;
		m[24][10]=34;
		m[25][7]=8;
		m[25][8]=9;
		m[26][4]=38;
		m[27][11]=39;
		m[28][24]=41;
		m[28][25]=42;
		m[28][26]=43;
		m[28][27]=44;
		m[28][28]=45;
		m[28][29]=46;
		m[28][18]=47;
		m[28][19]=48;
		m[29][20]=49;
		m[29][21]=50;
		m[34][22]=31;
		m[34][23]=32;
		m[34][3]=33;
		m[34][10]=34;
		m[35][18]=47;
		m[35][19]=48;
		m[36][11]=52;
		m[38][3]=26;
		m[39][12]=54;
		m[40][22]=31;
		m[40][23]=32;
		m[40][3]=33;
		m[40][10]=34;
		m[47][22]=31;
		m[47][23]=32;
		m[47][3]=33;
		m[47][10]=34;
		m[48][22]=31;
		m[48][23]=32;
		m[48][3]=33;
		m[48][10]=34;
		m[49][22]=31;
		m[49][23]=32;
		m[49][3]=33;
		m[49][10]=34;
		m[50][22]=31;
		m[50][23]=32;
		m[50][3]=33;
		m[50][10]=34;
		m[51][11]=60;
		m[51][18]=47;
		m[51][19]=48;
		m[52][9]=15;
		m[52][3]=16;
		m[52][15]=17;
		m[54][9]=15;
		m[54][3]=16;
		m[54][15]=17;
		m[55][18]=47;
		m[55][19]=48;
		m[56][20]=49;
		m[56][21]=50;
		m[57][20]=49;
		m[57][21]=50;
		m[61][16]=63;
		m[62][13]=64;
		m[62][14]=65;
		m[64][9]=15;
		m[64][3]=16;
		m[64][15]=17;
		m[66][14]=67;
		m[0][32]=1;
		m[0][33]=2;
		m[2][36]=4;
		m[3][34]=6;
		m[3][37]=7;
		m[5][38]=10;
		m[5][39]=11;
		m[5][40]=12;
		m[5][42]=13;
		m[5][41]=14;
		m[11][38]=21;
		m[11][39]=11;
		m[11][40]=12;
		m[11][42]=13;
		m[11][41]=14;
		m[19][35]=25;
		m[22][43]=27;
		m[22][44]=28;
		m[22][45]=29;
		m[22][46]=30;
		m[23][44]=35;
		m[23][45]=29;
		m[23][46]=30;
		m[24][43]=36;
		m[24][44]=28;
		m[24][45]=29;
		m[24][46]=30;
		m[25][34]=37;
		m[25][37]=7;
		m[28][47]=40;
		m[34][44]=51;
		m[34][45]=29;
		m[34][46]=30;
		m[38][35]=53;
		m[40][44]=55;
		m[40][45]=29;
		m[40][46]=30;
		m[47][45]=56;
		m[47][46]=30;
		m[48][45]=57;
		m[48][46]=30;
		m[49][46]=58;
		m[50][46]=59;
		m[52][38]=61;
		m[52][39]=11;
		m[52][40]=12;
		m[52][42]=13;
		m[52][41]=14;
		m[54][38]=62;
		m[54][39]=11;
		m[54][40]=12;
		m[54][42]=13;
		m[54][41]=14;
		m[64][38]=66;
		m[64][39]=11;
		m[64][40]=12;
		m[64][42]=13;
		m[64][41]=14;
		m[0][5]=-3;
		m[1][30]=3000;
		m[4][30]=-1;
		m[8][2]=-9;
		m[9][2]=-10;
		m[11][6]=-12;
		m[11][13]=-12;
		m[11][14]=-12;
		m[11][16]=-12;
		m[12][9]=-13;
		m[12][3]=-13;
		m[12][15]=-13;
		m[12][6]=-13;
		m[12][13]=-13;
		m[12][14]=-13;
		m[12][16]=-13;
		m[13][9]=-14;
		m[13][3]=-14;
		m[13][15]=-14;
		m[13][6]=-14;
		m[13][13]=-14;
		m[13][14]=-14;
		m[13][16]=-14;
		m[14][9]=-15;
		m[14][3]=-15;
		m[14][15]=-15;
		m[14][6]=-15;
		m[14][13]=-15;
		m[14][14]=-15;
		m[14][16]=-15;
		m[18][5]=-2;
		m[20][30]=-8;
		m[21][6]=-11;
		m[21][13]=-11;
		m[21][14]=-11;
		m[21][16]=-11;
		m[26][7]=-7;
		m[26][8]=-7;
		m[26][1]=-7;
		m[25][1]=-5;
		m[29][24]=-23;
		m[29][25]=-23;
		m[29][26]=-23;
		m[29][27]=-23;
		m[29][28]=-23;
		m[29][29]=-23;
		m[29][18]=-23;
		m[29][19]=-23;
		m[29][11]=-23;
		m[29][9]=-23;
		m[29][3]=-23;
		m[29][15]=-23;
		m[29][6]=-23;
		m[29][13]=-23;
		m[29][14]=-23;
		m[29][16]=-23;
		m[30][20]=-26;
		m[30][21]=-26;
		m[30][24]=-26;
		m[30][25]=-26;
		m[30][26]=-26;
		m[30][27]=-26;
		m[30][28]=-26;
		m[30][29]=-26;
		m[30][18]=-26;
		m[30][19]=-26;
		m[30][11]=-26;
		m[30][9]=-26;
		m[30][3]=-26;
		m[30][15]=-26;
		m[30][6]=-26;
		m[30][13]=-26;
		m[30][14]=-26;
		m[30][16]=-26;
		m[31][20]=-27;
		m[31][21]=-27;
		m[31][24]=-27;
		m[31][25]=-27;
		m[31][26]=-27;
		m[31][27]=-27;
		m[31][28]=-27;
		m[31][29]=-27;
		m[31][18]=-27;
		m[31][19]=-27;
		m[31][11]=-27;
		m[31][9]=-27;
		m[31][3]=-27;
		m[31][15]=-27;
		m[31][6]=-27;
		m[31][13]=-27;
		m[31][14]=-27;
		m[31][16]=-27;
		m[32][20]=-28;
		m[32][21]=-28;
		m[32][24]=-28;
		m[32][25]=-28;
		m[32][26]=-28;
		m[32][27]=-28;
		m[32][28]=-28;
		m[32][29]=-28;
		m[32][18]=-28;
		m[32][19]=-28;
		m[32][11]=-28;
		m[32][9]=-28;
		m[32][3]=-28;
		m[32][15]=-28;
		m[32][6]=-28;
		m[32][13]=-28;
		m[32][14]=-28;
		m[32][16]=-28;
		m[33][20]=-29;
		m[33][21]=-29;
		m[33][24]=-29;
		m[33][25]=-29;
		m[33][26]=-29;
		m[33][27]=-29;
		m[33][28]=-29;
		m[33][29]=-29;
		m[33][18]=-29;
		m[33][19]=-29;
		m[33][11]=-29;
		m[33][9]=-29;
		m[33][3]=-29;
		m[33][15]=-29;
		m[33][6]=-29;
		m[33][13]=-29;
		m[33][14]=-29;
		m[33][16]=-29;
		m[35][9]=-19;
		m[35][3]=-19;
		m[35][15]=-19;
		m[35][6]=-19;
		m[35][13]=-19;
		m[35][14]=-19;
		m[35][16]=-19;
		m[37][1]=-4;
		m[41][22]=-31;
		m[41][23]=-31;
		m[41][3]=-31;
		m[41][10]=-31;
		m[42][22]=-32;
		m[42][23]=-32;
		m[42][3]=-32;
		m[42][10]=-32;
		m[43][22]=-33;
		m[43][23]=-33;
		m[43][3]=-33;
		m[43][10]=-33;
		m[44][22]=-34;
		m[44][23]=-34;
		m[44][3]=-34;
		m[44][10]=-34;
		m[45][22]=-35;
		m[45][23]=-35;
		m[45][3]=-35;
		m[45][10]=-35;
		m[46][22]=-36;
		m[46][23]=-36;
		m[46][3]=-36;
		m[46][10]=-36;
		m[53][7]=-6;
		m[53][8]=-6;
		m[53][1]=-6;
		m[55][11]=-20;
		m[56][24]=-21;
		m[56][25]=-21;
		m[56][26]=-21;
		m[56][27]=-21;
		m[56][28]=-21;
		m[56][29]=-21;
		m[56][18]=-21;
		m[56][19]=-21;
		m[56][11]=-21;
		m[56][9]=-21;
		m[56][3]=-21;
		m[56][15]=-21;
		m[56][6]=-21;
		m[56][13]=-21;
		m[56][14]=-21;
		m[56][16]=-21;
		m[57][24]=-22;
		m[57][25]=-22;
		m[57][26]=-22;
		m[57][27]=-22;
		m[57][28]=-22;
		m[57][29]=-22;
		m[57][18]=-22;
		m[57][19]=-22;
		m[57][11]=-22;
		m[57][9]=-22;
		m[57][3]=-22;
		m[57][15]=-22;
		m[57][6]=-22;
		m[57][13]=-22;
		m[57][14]=-22;
		m[57][16]=-22;
		m[58][20]=-24;
		m[58][21]=-24;
		m[58][24]=-24;
		m[58][25]=-24;
		m[58][26]=-24;
		m[58][27]=-24;
		m[58][28]=-24;
		m[58][29]=-24;
		m[58][18]=-24;
		m[58][19]=-24;
		m[58][11]=-24;
		m[58][9]=-24;
		m[58][3]=-24;
		m[58][15]=-24;
		m[58][6]=-24;
		m[58][13]=-24;
		m[58][14]=-24;
		m[58][16]=-24;
		m[59][20]=-25;
		m[59][21]=-25;
		m[59][24]=-25;
		m[59][25]=-25;
		m[59][26]=-25;
		m[59][27]=-25;
		m[59][28]=-25;
		m[59][29]=-25;
		m[59][18]=-25;
		m[59][19]=-25;
		m[59][11]=-25;
		m[59][9]=-25;
		m[59][3]=-25;
		m[59][15]=-25;
		m[59][6]=-25;
		m[59][13]=-25;
		m[59][14]=-25;
		m[59][16]=-25;
		m[60][20]=-30;
		m[60][21]=-30;
		m[60][24]=-30;
		m[60][25]=-30;
		m[60][26]=-30;
		m[60][27]=-30;
		m[60][28]=-30;
		m[60][29]=-30;
		m[60][18]=-30;
		m[60][19]=-30;
		m[60][11]=-30;
		m[60][9]=-30;
		m[60][3]=-30;
		m[60][15]=-30;
		m[60][6]=-30;
		m[60][13]=-30;
		m[60][14]=-30;
		m[60][16]=-30;
		m[63][9]=-18;
		m[63][3]=-18;
		m[63][15]=-18;
		m[63][6]=-18;
		m[63][13]=-18;
		m[63][14]=-18;
		m[63][16]=-18;
		m[65][9]=-17;
		m[65][3]=-17;
		m[65][15]=-17;
		m[65][6]=-17;
		m[65][13]=-17;
		m[65][14]=-17;
		m[65][16]=-17;
		m[67][9]=-16;
		m[67][3]=-16;
		m[67][15]=-16;
		m[67][6]=-16;
		m[67][13]=-16;
		m[67][14]=-16;
		m[67][16]=-16;


        PROD[1] = "M";
        PROD[2] = "M + B";
        PROD[3] = "M - B";
        PROD[4] = "B";
        PROD[5] = "B * A";
        PROD[6] = "B / A";
        PROD[7] = "A";
        PROD[8] = "num";
        PROD[9] = "( M )";

        // inicia el parser
        push("0");
        lee_token(xArchivo(Entrada));
        String s;
        int e;
        int m;
        do{
            s = pila[tope];
            m = M[Integer.parseInt(s)][terminal(a)];
            if(m == 3000){
                System.out.println("Analisis Sintactico correcto");
                System.out.println("El valor de M final es: " + popM());
				//pausa();
                System.exit(0);
            } else{
                if(m > 0){
					//System.out.println("Shift ("+m+")");
					//pausa();
					cod_shift(m);
                    push(a);
                    push(m + "");
                    lee_token(xArchivo(Entrada));
                } else if(m < 0) {
					//System.out.println("Reduce (" +m+ ")");
                    cod_reduce(-m);

                    for (int i = 0; i < lpd[m * -1] * 2; i++) {
                        pop();
                    }
                    e = Integer.parseInt(pila[tope]);
                    push(pi[m * -1]);
                    if (M[e][noterminal(pi[m * -1])] == 0) {
                        error();
                    } else {
                        push(M[e][noterminal(pi[m * -1])] + "");
                    }
                } else{
                    error();
                }
            }
        }while(true);
    }

    public static void cod_reduce(int R){
        switch (R){
            case 1:
                pushM(popM() + popB());
				break;
			case 2:
                pushM(popM() - popB());
				break;
			case 3:
                pushM(popB());
				break;
			case 4:
                pushB(popB() * popA());
				break;
			case 5:
                pushB(popB() / popA());
				break;
			case 6:
                pushB(popA());
				break;
			case 7:
                pushA(temp1);
				break;
			case 8:
                pushA(popM());
				break;
        }
    }

    public static void cod_shift(int S){
		if(S==4){
			temp1 = Float.parseFloat(LEX);
			//System.out.println(temp1);
			//pausa();
		}
    }
}