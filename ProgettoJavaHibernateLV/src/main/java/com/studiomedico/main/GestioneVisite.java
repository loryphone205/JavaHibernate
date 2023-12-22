package com.studiomedico.main;

import java.util.Scanner;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.studiomedico.repository.MedicoRepository;
import com.studiomedico.repository.PazienteRepository;
import com.studiomedico.repository.VisitaRepository;
import com.studiomedico.model.Medico;
import com.studiomedico.model.Paziente;
import com.studiomedico.model.Visita;

public class GestioneVisite {

	public static void main(String[] args) {

		// inizializzo un'istanza per ognuna delle repository
		// il readAll che ho fatto a tutti salverà i dati aggiornati al di fuori, così
		// in un futuro, nel caso si voglia implementare una modifica dei dati si più
		// larga scala dal main, si può fare
		VisitaRepository VR = new VisitaRepository();
		MedicoRepository MR = new MedicoRepository();
		PazienteRepository PR = new PazienteRepository();
		Scanner console = new Scanner(System.in);
		VR.setup();
		MR.setup();
		PR.setup();

		List<Visita> visite = new ArrayList<Visita>();
		List<Paziente> pazienti = new ArrayList<Paziente>();
		List<Medico> medici = new ArrayList<Medico>();

		boolean choice = true;
		do {
			// menu principale
			System.out.println("Benvenuto nella gestione delle Visite, Pazienti e Medici.");
			System.out.println("Scegliere in quale sezione navigare:");
			System.out.println("1. Visite");
			System.out.println("2. Pazienti");
			System.out.println("3. Medici");
			System.out.println("4. Uscita dall'App");
			System.out.println("(In tutti i menù, usare i numeri per navigare. Per Piacere qwq");
			int sceltaMenu = console.nextInt();
			console.nextLine();

			// piccolo check
			if (sceltaMenu <= 0 || sceltaMenu > 4) {
				do {
					System.out.print("Inserire un numero valido: ");
					sceltaMenu = console.nextInt();
					console.nextLine();
				} while (sceltaMenu <= 0 || sceltaMenu > 4);
			}

			// inizio dello switch
			switch (sceltaMenu) {

			// Scelta Visite, la più complessa
			case 1:
				boolean innerChoice = true;
				do {
					System.out.println("---------Pagina Visite:---------");
					visite = VR.readAll();
					System.out.println("--------------------------------");
					System.out.println("Quali Azioni vuole eseguire su questa lista?: ");
					System.out.println("1. Aggiungi Visita.");
					System.out.println("2. Modifica Visita.");
					System.out.println("3. Elimina Visita.");
					System.out.println("4. Stampa Visita.");
					System.out.println("5. Menù Precedente.");
					int inSceltaMenu = console.nextInt();
					console.nextLine();

					// piccolo check
					if (inSceltaMenu <= 0 || inSceltaMenu > 5) {
						do {
							System.out.print("Inserire un numero valido: ");
							inSceltaMenu = console.nextInt();
							console.nextLine();
						} while (inSceltaMenu <= 0 || inSceltaMenu > 5);
					}

					// scelta di visita
					switch (inSceltaMenu) {

					// aggiungi/create
					case 1:
						VR.create();
						inSceltaMenu = 0;
						break;

					// modifica/edit
					case 2:
						try {
							VR.edit();
						} catch (SQLException e) {
							System.out.println("Attenzione, il programma è andato in errore." + e);
						}
						inSceltaMenu = 0;
						break;

					// elimina
					case 3:
						System.out
								.println("È possibile scegliere tra 3 tipologie di eliminazione in questa categoria:");
						System.out.println("1. Elimina una Visita");
						System.out.println("2. Elimina tutte le Visite di un Paziente");
						System.out.println("3. Elimina tutte le Visite di un Medico");
						inSceltaMenu = console.nextInt();
						console.nextLine();

						// piccolo check
						if (inSceltaMenu <= 0 || inSceltaMenu > 3) {
							do {
								System.out.print("Inserire un numero valido: ");
								inSceltaMenu = console.nextInt();
								console.nextLine();
							} while (inSceltaMenu <= 0 || inSceltaMenu > 3);
						}

						// redirect
						if (inSceltaMenu == 1) {
							try {
								VR.delete();
							} catch (SQLException e) {
								System.out.println("Attenzione, il programma è andato in errore." + e);
							}
						} else {
							try {
								VR.delete(visite, inSceltaMenu);
							} catch (SQLException e) {
								System.out.println("Attenzione, il programma è andato in errore." + e);
							}
						}

						inSceltaMenu = 0;
						break;

					// stampa visita, quella senza ritorno
					case 4:
						try {
							VR.read();
						} catch (SQLException e) {
							System.out.println("Attenzione, il programma è andato in errore." + e);
						}
						inSceltaMenu = 0;
						break;

					// Uscita
					case 5:
						System.out.println("Uscita da Visite.");
						innerChoice = false;
						break;
					}

				} while (innerChoice == true);
				break;

			// scelta Pazienti
			case 2:
				innerChoice = true;
				do {
					System.out.println("---------Pagina Pazienti:---------");
					pazienti = PR.readAll();
					System.out.println("--------------------------------");
					System.out.println("Quali Azioni vuole eseguire su questa lista?: ");
					System.out.println("1. Aggiungi Paziente.");
					System.out.println("2. Modifica Paziente.");
					System.out.println("3. Elimina Paziente.");
					System.out.println("4. Stampa Paziente.");
					System.out.println("5. Menù Precedente.");
					int inSceltaMenu = console.nextInt();
					console.nextLine();

					// piccolo check
					if (inSceltaMenu <= 0 || inSceltaMenu > 5) {
						do {
							System.out.print("Inserire un numero valido: ");
							inSceltaMenu = console.nextInt();
							console.nextLine();
						} while (inSceltaMenu <= 0 || inSceltaMenu > 5);
					}

					// switch di pazienti
					switch (inSceltaMenu) {
					// aggiunta
					case 1:
						PR.create();
						inSceltaMenu = 0;
						break;

					// modifica
					case 2:
						try {
							PR.edit();
						} catch (SQLException e) {
							System.out.println("Attenzione, il programma è andato in errore." + e);
						}
						inSceltaMenu = 0;
						break;

					// elimina
					case 3:
						try {
							PR.delete();
						} catch (SQLException e) {
							System.out.println("Attenzione, il programma è andato in errore." + e);
						}
						inSceltaMenu = 0;
						break;

					// stampa
					case 4:
						try {
							PR.read();
						} catch (SQLException e) {
							System.out.println("Attenzione, il programma è andato in errore." + e);
						}
						inSceltaMenu = 0;
						break;

					// uscita
					case 5:
						System.out.println("Uscita da Pazienti.");
						innerChoice = false;
						break;
					}
				} while (innerChoice == true);
				break;

			// scelta Medici
			case 3:
				innerChoice = true;
				do {
					System.out.println("---------Pagina Medici:---------");
					medici = MR.readAll();
					System.out.println("--------------------------------");
					System.out.println("Quali Azioni vuole eseguire su questa lista?: ");
					System.out.println("1. Aggiungi Medico.");
					System.out.println("2. Modifica Medico.");
					System.out.println("3. Elimina Medico.");
					System.out.println("4. Stampa Medico.");
					System.out.println("5. Menù Precedente.");
					int inSceltaMenu = console.nextInt();
					console.nextLine();

					// piccolo check
					if (inSceltaMenu <= 0 || inSceltaMenu > 5) {
						do {
							System.out.print("Inserire un numero valido: ");
							inSceltaMenu = console.nextInt();
							console.nextLine();
						} while (inSceltaMenu <= 0 || inSceltaMenu > 5);
					}

					// switch dei medici
					switch (inSceltaMenu) {

					// aggiungi
					case 1:
						MR.create();
						inSceltaMenu = 0;
						break;

					// modifica
					case 2:
						try {
							MR.edit();
						} catch (SQLException e) {
							System.out.println("Attenzione, il programma è andato in errore." + e);
						}
						inSceltaMenu = 0;
						break;

					// elimina
					case 3:
						try {
							MR.delete();
						} catch (SQLException e) {
							System.out.println("Attenzione, il programma è andato in errore." + e);
						}
						inSceltaMenu = 0;
						break;

					// stampa
					case 4:
						try {
							MR.read();
						} catch (SQLException e) {
							System.out.println("Attenzione, il programma è andato in errore." + e);
						}
						inSceltaMenu = 0;
						break;

					// uscita
					case 5:
						System.out.println("Uscita da Medici.");
						innerChoice = false;
						break;
					}
				} while (innerChoice == true);
				break;

			// scelta Uscita
			case 4:
				System.out.println("Grazie per aver usato GAP.");
				choice = false;
				break;
			}

		} while (choice == true);

		// chiudo tutto, ed è finita.
		VR.sfClose();
		MR.sfClose();
		PR.sfClose();
		console.close();
	}

}
