import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		boolean nochmal = true;

		do {

			System.out.println("MEMORY");

			System.out.println("1 - Ein neues Spiel starten");
			System.out.println("2 - Programm beenden");

			int auswahl = sc.nextInt();

			switch (auswahl) {

			case 1:
				Memory m = new Memory();
				m.starteSpiel();
				break;
			case 2:
				nochmal = false;
				break;

			}

			System.out.println();

		} while (nochmal);

		sc.close();
	}

}
