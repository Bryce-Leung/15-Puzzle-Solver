package fifteenpuzzle;

import java.io.*; 

public class BoardGen {

	public static void createBoard1() throws IOException {
		File f = new File("board1.txt");
		FileWriter fw = new FileWriter(f);
		fw.write("3\n");
		fw.write(" 5  1  2\n");
		fw.write(" 8     3\n");
		fw.write(" 4  6  7\n");
		fw.close();

		f = new File("sol1.txt");
		fw = new FileWriter(f);

		fw.write("8 R\n");
		fw.write("5 D\n");
		fw.write("1 L\n");
		fw.write("2 L\n");
		fw.write("3 U\n");
		// 1  2  3
		// 5  8   
		// 4  6  7
		fw.write("8 R\n");
		fw.write("6 U\n");
		fw.write("7 L\n");
		// 1  2  3
		// 5  6  8
		// 4  7   
		fw.write("8 D\n");
		fw.write("6 R\n");
		fw.write("5 R\n");
		fw.write("4 U\n");
		// 1  2  3
		// 4  5  6
		//	  7  8
		fw.write("7 L\n");
		fw.write("8 L\n");

		fw.close();

	}

	public static void createBoard2() throws IOException {
		File f = new File("board2.txt");
		FileWriter fw = new FileWriter(f);
		fw.write("4\n");
		fw.write(" 1  2  3  4\n");
		fw.write(" 5  6  7  8\n");
		fw.write(" 9 10 12 15\n");
		fw.write("13 14 11   \n");
		fw.close();

		f = new File("sol2.txt");
		fw = new FileWriter(f);

		fw.write("15 D\n");
		fw.write("12 R\n");
		fw.write("11 U\n");
		fw.write("15 L\n");

		fw.close();
}

	public static void createBoard3() throws IOException {
		File f = new File("board3.txt");
		FileWriter fw = new FileWriter(f);
		fw.write("7\n");
		fw.write(" 1  3 10  4  5  6  7\n");
		fw.write(" 8  2    11 12 13 14\n");
		fw.write("15  9 17 18 19 20 21\n");
		fw.write("22 16 24 25 26 27 28\n");
		fw.write("29 23 31 32 33 34 35\n");
		fw.write("37 30 39 40 41 42 48\n");
		fw.write("36 38 43 44 45 46 47\n");
		fw.close();


		f = new File("sol3.txt");
		fw = new FileWriter(f);
		fw.write("10 D\n");
		fw.write("3 R\n");

		fw.write("2 U\n");
		fw.write("9 U\n");
		fw.write("16 U\n");
		fw.write("23 U\n");
		fw.write("30 U\n");
		fw.write("38 U\n");

		// 1  2  3  4  5  6  7
		// 8  9 10 11 12 13 14
		//15 16 17 18 19 20 21
		//22 23 24 25 26 27 28
		//29 30 31 32 33 34 35
		//37 38 39 40 41 42 48
		//36    43 44 45 46 47

		for (int i = 43; i <= 47; i++)
			fw.write("" + i + " L\n"); // 43..47 L
		fw.write("48 D\n");

		// 1  2  3  4  5  6  7
		// 8  9 10 11 12 13 14
		//15 16 17 18 19 20 21
		//22 23 24 25 26 27 28
		//29 30 31 32 33 34 35
		//37 38 39 40 41 42   
		//36 43 44 45 46 47 48

		for (int i = 42; i >= 37; i--)
			fw.write("" + i + " R\n"); // 42..37 R
		fw.write("36 U\n");
		// 1  2  3  4  5  6  7
		// 8  9 10 11 12 13 14
		//15 16 17 18 19 20 21
		//22 23 24 25 26 27 28
		//29 30 31 32 33 34 35
		//36 37 38 39 40 41 42   
		//   43 44 45 46 47 48

		for (int i = 43; i <= 48; i++)
			fw.write("" + i + " L\n"); // 43..48 L

		fw.close();

	}

	public static void main(String[] args) {

		try {
			createBoard1();
			createBoard2();
			createBoard3();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
