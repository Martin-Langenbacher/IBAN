import java.io.*;

public class IbanBerechnen {

	public static void main(String[] args) throws IOException {
		// Eingabe der Daten:

		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		String eingabe;
		int blz = 0;
		int kontoNr = 0;
		int zaehler = 0;
		String laenderCode = "";
		String ergebnisIban;

		System.out.println("Herzlich Willkommen bei der IBAN berechnung!\n");
		// BLZ
		while (zaehler == 0) {

			try {
				System.out.println("Bitte geben Sie die BLZ ein:\n");
				eingabe = br.readLine();
				blz = Integer.parseInt(eingabe);
			}

			catch (NumberFormatException ausnahme) {
				System.out.println("Bitte eine Zahl eingeben!");
				continue;
			}
			zaehler++;

		}

		// KontoNr.
		while (zaehler == 1) {

			try {
				System.out.println("Geben Sie die KontoNr. ein:");
				eingabe = br.readLine();
				kontoNr = Integer.parseInt(eingabe);
			}

			catch (NumberFormatException ausnahme) {
				System.out.println("Bitte eine Zahl eingeben!");
				continue;
			}
			zaehler++;
		}

		// Länderkennung
		while (zaehler == 2) {

			try {
				System.out.println("Geben Sie die Länderkennung ein:");
				laenderCode = br.readLine();
			}

			catch (NumberFormatException ausnahme) {
				System.out.println("Bitte eine Zahl eingeben!");
				continue;
			}

			laenderCode = laenderCode.toUpperCase();
			zaehler++;
		}

		ergebnisIban = erzeugeIban(laenderCode, blz, kontoNr);

		System.out.println("IBAN: " + ergebnisIban);

	}
//=====================================================================================

	// Methode zur Erzeugung der IBAN
	public static String erzeugeIban(String laenderCode, int blz, int kontoNr) {
		String finalerIban = "";
		System.out.println("LänderCode: " + laenderCode);
		System.out.println("BLZ: " + blz);
		System.out.println("Konto-Nr.: " + kontoNr);

		// KontoNummer: 10-Ziffern!
		String kontoZehnStringStart = Integer.toString(kontoNr);
		String kontoZehnStringFinal = "";

		for (int i = 10; i > kontoZehnStringStart.length(); i--) {
			kontoZehnStringFinal = kontoZehnStringFinal + "0";
		}
		kontoZehnStringFinal = kontoZehnStringFinal + kontoZehnStringStart;

		// BBAN
		String bban = blz + kontoZehnStringFinal;

		// 24-Ziffern
		String vierUndZwanzigString = "";

		char buchstabe1 = laenderCode.charAt(0);
		char buchstabe2 = laenderCode.charAt(1);

		int zahl1 = buchstabeInZahl(buchstabe1);
		int zahl2 = buchstabeInZahl(buchstabe2);

		vierUndZwanzigString = bban + zahl1 + zahl2 + "00";

		int modulo97Wert = modulo97(vierUndZwanzigString);

		int pruefZahl = 98 - modulo97Wert;

		String pruefZahlString = "";
		if (pruefZahl < 10) {
			pruefZahlString = "0" + pruefZahl;
		} else {
			pruefZahlString = "" + pruefZahl;
		}

		finalerIban = laenderCode + pruefZahlString + bban;
		return finalerIban;
	}

//=====================================================================================

	// Mehtode: Char in Zahl umwandeln
	public static int buchstabeInZahl(char aChar) {

		int ascii = (int) aChar;
		int zahl = ascii - 55;
		return zahl;
	}

//=====================================================================================
	// Berechne Modulo-Zahl
	private static int modulo97(String vierUndZwanzigStringLang) {

		// int moduloWert;
		String checkSum = vierUndZwanzigStringLang;

		while (checkSum.length() > 9) {
			checkSum = Integer.parseInt(checkSum.substring(0, 9)) % 97 + checkSum.substring(9);
		}
		int checkNum = Integer.parseInt(checkSum) % 97;

		// System.out.println("checkNum: " +checkNum);
		return checkNum;

// =====================================================================================
	}

}
