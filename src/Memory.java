import java.util.Random;
import java.util.Scanner;

public class Memory {

	private final int ZEILEN = 4;
	private final int SPALTEN = 6;
	private char[][] spielfeld;
	private boolean[][] verdecktesFeld;
	private Spieler spielerA;
	private Spieler spielerB;

	Random random = new Random();
	Scanner sc = new Scanner(System.in);

	public Memory() {

		this.spielfeld = new char[ZEILEN][SPALTEN];
		this.verdecktesFeld = new boolean[ZEILEN][SPALTEN];
		initFeld();
		mischeFeld();
	}

	public void initFeld() {

		int gross = 65;
		int klein = 97;

		for (int i = 0; i < ZEILEN; i++) {
			for (int j = 0; j < SPALTEN; j++) {
				if (i < (ZEILEN / 2)) {
					spielfeld[i][j] = (char) (gross++);
				} else {
					spielfeld[i][j] = (char) (klein++);
				}
				verdecktesFeld[i][j] = false;

			}
		}
	}

	public void druckeFeldOffen() {

		for (int i = 0; i < ZEILEN; i++) {
			for (int j = 0; j < SPALTEN; j++) {
				System.out.print(spielfeld[i][j] + "\t");
			}
			System.out.println();
		}

	}

	public void druckeFeld() {

		for (int i = 0; i < ZEILEN; i++) {
			for (int j = 0; j < SPALTEN; j++) {
				if (verdecktesFeld[i][j]) {
					System.out.print(spielfeld[i][j] + "\t");
				} else {
					System.out.print("-\t");
				}
			}
			System.out.println();
		}
	}

	public void mischeFeld() {

		for (int z = ZEILEN - 1; z >= 0; z--) {
			for (int s = SPALTEN - 1; s >= 0; s--) {
				int rd1 = random.nextInt(z + 1);
				int rd2 = random.nextInt(s + 1);
				char tmp = spielfeld[rd1][rd2];
				spielfeld[rd1][rd2] = spielfeld[z][s];
				spielfeld[z][s] = tmp;
			}
		}
	}

	public boolean pruefeZeichen(char a, char b) {
		if (a >= 65 && a <= 90) {
			b = (char) (b - 32);
		} else if (b >= 65 && b <= 90) {
			a = (char) (a - 32);
		}

		return a == b;
	}

	public boolean pruefeEnde() {
		for (int i = 0; i < ZEILEN; i++) {
			for (int j = 0; j < SPALTEN; j++) {
				if (!verdecktesFeld[i][j]) {
					return false;
				}
			}
		}
		return true;
	}

	public void initSpieler() {
		System.out.println("Geben Sie den Namen von Spieler 1 ein:");
		spielerA = new Spieler(sc.next());
		System.out.println("Geben Sie den Namen von Spieler 2 ein:");
		spielerB = new Spieler(sc.next());
	}

	public boolean istkarteSchonOffen(int zeile, int spalte) {
		return verdecktesFeld[zeile][spalte];
	}

	
	public void druckeSpielstand() {
		System.out.println("Der aktuelle Spielstand lautet: ");
		System.out.printf("%s: %d Punkte\n", spielerA.getName(), spielerA.getPunkte());
		System.out.printf("%s: %d Punkte\n", spielerB.getName(), spielerB.getPunkte());
	}

	public void druckeGewinner() {
		if (spielerA.getPunkte() > spielerB.getPunkte()) {
			System.out.printf("%s hat dieses Spiel gewonnen!\n", spielerA.getName());
		} else if (spielerA.getPunkte() < spielerA.getPunkte()) {
			System.out.printf("%s hat dieses Spiel gewonnen!", spielerB.getName());
		} else {
			System.out.println("Unentschieden");
		}
	}

	public void starteSpiel() {

		initSpieler();
		Spieler aktuellerSpieler = spielerA;

		do {
			druckeFeldOffen();
			System.out.println();
			System.out.printf("%s ist an der Reihe.\n", aktuellerSpieler.getName());
			druckeFeld();

			int zeile1;
			int spalte1;
			do {
				System.out.println("Zeile 1:");
				zeile1 = sc.nextInt();
				System.out.println("Spalte 1:");
				spalte1 = sc.nextInt();

				if (istkarteSchonOffen(zeile1, spalte1)) {
					System.out.println("Karte schon offen!");
				}

			} while (istkarteSchonOffen(zeile1, spalte1));

			System.out.println("An der Stelle befindet sich ein " + spielfeld[zeile1][spalte1]);

			char zeichen1 = spielfeld[zeile1][spalte1];

			int zeile2;
			int spalte2;
			do {
				System.out.println("Zeile 2:");
				zeile2 = sc.nextInt();
				System.out.println("Spalte 2:");
				spalte2 = sc.nextInt();

				if (istkarteSchonOffen(zeile2, spalte2)) {
					System.out.println("Karte schon offen!");
				}

			} while (istkarteSchonOffen(zeile2, spalte2));

			System.out.println("An der Stelle befindet sich ein " + spielfeld[zeile2][spalte2]);

			char zeichen2 = spielfeld[zeile2][spalte2];

			if (!pruefeZeichen(zeichen1, zeichen2)) {
				aktuellerSpieler = (aktuellerSpieler == spielerA) ? spielerB : spielerA;
			} else {
				aktuellerSpieler.erhoehePunkte();
				verdecktesFeld[zeile1][spalte1] = true;
				verdecktesFeld[zeile2][spalte2] = true;
			}

			druckeSpielstand();

		} while (!pruefeEnde());

		druckeGewinner();

	}

}
