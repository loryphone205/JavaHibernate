package com.studiomedico.repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.studiomedico.dao.StudioMedicoDAO;
import com.studiomedico.model.Medico;
import com.studiomedico.model.Paziente;
import com.studiomedico.model.Visita;

public class PazienteRepository implements StudioMedicoDAO<Paziente> {

	// chiamiamo la SessionFacotry, così possiamo agire con le Session e di
	// conseguenza Hibernate
	private static SessionFactory sf;

	// apro anche lo scanner, mi servirà sicuramente per l'immissione dei dati
	Scanner console = new Scanner(System.in);

	// c'era troppo codice inutilmente, ho fatto questa funzione locale per
	// risparmiare sulle righe totali
	// questa funzioen locale mi permette di fare il controllo sulla data di nascita
	// Si Daniele, proprio per te l'ho creata. Voglio che tu me la rompa
	private String controlloDataNascita() {

		// devo fare dei controlli aggiuntivi sulla data di nascita
		System.out.println("Data di Nascita: Non superare l'anno 9999, lo dico per te eh");
		System.out.println("Inserisci giorno: ");
		int giorno = console.nextInt();
		console.nextLine();
		if (giorno > 31 || giorno <= 0) {
			do {
				System.out.println("Inserisci un giorno valido: ");
				giorno = console.nextInt();
				console.nextLine();
			} while (giorno > 31 || giorno <= 0);
		}
		System.out.println("Inserisci mese: ");
		int mese = console.nextInt();
		console.nextLine();
		if (mese > 12 || mese <= 0) {
			do {
				System.out.println("Inserisci un mese valido: ");
				mese = console.nextInt();
				console.nextLine();
			} while (mese > 12 || mese <= 0);
		}
		System.out.println("Inserisci anno: Ricorda, sono quattro cifre MAX:");
		int anno = console.nextInt();
		console.nextLine();
		if (anno <= 0 || anno > 9999) {
			do {
				System.out.println("Inserisci un anno valido: ");
				anno = console.nextInt();
				console.nextLine();
			} while (anno <= 0 || anno > 9999);
		}

		// coverto i miei int in stringhe così li posso mettere nel db
		String dataNascita = String.valueOf(giorno) + "/" + String.valueOf(mese) + "/" + String.valueOf(anno);
		return dataNascita;
	}

	// continua mi permette di decidere sei continuare a inserire dati o no
	private boolean continua() {

		// faccio un controllo sull'input quando chiedo se vuole inserire altri dati
		boolean innerChoice = true;
		do {
			// chiedo se vuole aggiungere altri pazienti
			System.out.println("Vuole eseguire altre azioni in questa pagina?: 1/2 (Si/No)");
			int input = console.nextInt();
			console.nextLine();
			if (input >= 3 || input <= 0) {
				System.out.println("Inserire un numero valido.");
			} else {
				innerChoice = false;
			}

			// questo check è per differenziare s e n per farlo uscire dal ciclo più esetrno
			if (input == 2)
				return false;

		} while (innerChoice == true);

		return true;

	}

	// setup, ci serve per setuppare il database con le nostre classi
	@Override
	public void setup() {
		System.out.println("Apro la connessione");
		Configuration cfg = new Configuration();
		cfg.addAnnotatedClass(Medico.class);
		cfg.addAnnotatedClass(Visita.class);
		cfg.addAnnotatedClass(Paziente.class);
		sf = cfg.configure("hibernate.cfg.xml").buildSessionFactory();
	}

	// mi permette di creare i pazienti. Utilizza anche la mia funzione privata
	// continua e controlloDataNascita
	@Override
	public void create() {

		// choice mi permette di controllare il voler continuare ad aggiungere utenti o
		// meno
		// uso il do perchè presuppongo di andarci almeno una volta in questo ciclo
		boolean choice = true;
		do {

			Session sessione = sf.openSession();

			// creo un paziente senza valori da fillare
			Paziente paziente = new Paziente();

			// chiedo i dati
			System.out.print("Nome: ");
			paziente.setNome(console.nextLine());
			System.out.print("Cognome: ");
			paziente.setCognome(console.nextLine());

			// utilizzo il metodo che ho creato
			paziente.setDataNascita(controlloDataNascita());
			// paziente.setDataNascita(console.nextLine());

			// faccio la transaction
			sessione.beginTransaction();
			sessione.persist(paziente);
			sessione.getTransaction().commit();

			// continua è il metodo che mi restituisce vero o falso nel caso io voglia
			// iterare ancora
			choice = continua();

			sessione.close();

		} while (choice == true);

		// chiudo la sessione

	}

	// metodo readAll
	@Override
	public List<Paziente> readAll() {

		// apro la sessione
		Session sessione = sf.openSession();

		// uso la query per farmi un select praticamente
		String hql = "FROM Paziente";
		Query<Paziente> query = sessione.createQuery(hql, Paziente.class);
		for (Paziente paziente : query.list()) {
			System.out.println(paziente);
		}

		// chiudo
		return query.list();

	}

	// read in conosle di un record
	@Override
	public void read() throws SQLException {

		// chiedo l'id da leggere per un piccolo controllo. anche qui metto un loop nel
		// caso
		// ci sia bisogno di leggere i dati di più medici ma uno alla volta
		System.out.println("Attenzione! Inserire un numero valido.");
		System.out.println("Se non si sa che paziente cercare, tornare al menù precedente e selezionare 'Stampa'.");
		boolean choice = true;
		do {

			// apro la sessione
			Session sessione = sf.openSession();

			// chiedo i dati
			System.out.print("Inserisca l'id del paziente da cercare: ");
			int id = console.nextInt();
			console.nextLine();

			// qui faccio il check vero e proprio dell'id se è corretto
			if (id <= 0) {
				do {
					System.out.print("Inserisci un id valido: ");
					id = console.nextInt();
					console.nextLine();
				} while (id <= 0);
			}

			// stampo i dati
			sessione.beginTransaction();
			System.out.println(sessione.get(Paziente.class, id));
			sessione.getTransaction().commit();

			choice = continua();

			sessione.close();

		} while (choice == true);

	}

	// edit di un record
	@Override
	public void edit() throws SQLException {

		// chiedo l'id da leggere per un piccolo controllo. anche qui metto un loop nel
		// caso
		// ci sia bisogno di leggere i dati di più medici ma uno alla volta
		System.out.println("Attenzione! Inserire un numero valido.");
		System.out.println("Se non si sa che paziente cercare, tornare al menù precedente e selezionare 'Stampa'.");
		boolean choice = true;
		do {
			// apro la sessione
			Session sessione = sf.openSession();

			// chiedo i dati
			System.out.print("Inserisca l'id del paziente da cercare: ");
			int id = console.nextInt();
			console.nextLine();

			// qui faccio il check vero e proprio dell'id se è corretto
			if (id <= 0) {
				do {
					System.out.print("Inserisci un id valido: ");
					id = console.nextInt();
					console.nextLine();
				} while (id <= 0);
			}

			// creo paziente con solo id
			Paziente paziente = new Paziente(id);

			// chiedo i dati
			System.out.print("Nome: ");
			paziente.setNome(console.nextLine());
			System.out.print("Cognome: ");
			paziente.setCognome(console.nextLine());

			paziente.setDataNascita(controlloDataNascita());

			// faccio la transaction
			sessione.beginTransaction();
			sessione.merge(paziente);
			sessione.getTransaction().commit();

			choice = continua();

			sessione.close();

		} while (choice == true);

	}

	// delete di un record
	@Override
	public void delete() throws SQLException {

		// chiedo l'id da leggere per un piccolo controllo. anche qui metto un loop nel
		// caso
		// ci sia bisogno di leggere i dati di più medici ma uno alla volta
		System.out.println("Attenzione! Inserire un numero valido.");
		System.out.println("Se non si sa che paziente cercare, tornare al menù precedente e selezionare 'Stampa'.");
		boolean choice = true;
		do {

			// apro la sessione
			Session sessione = sf.openSession();

			// chiedo i dati
			System.out.print("Inserisca l'id del paziente da cercare: ");
			int id = console.nextInt();
			console.nextLine();

			// qui faccio il check vero e proprio dell'id se è corretto
			if (id <= 0) {
				do {
					System.out.print("Inserisci un id valido: ");
					id = console.nextInt();
					console.nextLine();
				} while (id <= 0);
			}

			// creo paziente con solo id
			Paziente paziente = new Paziente(id);

			// faccio la transaction
			sessione.beginTransaction();
			sessione.remove(paziente);
			sessione.getTransaction().commit();

			choice = continua();

			sessione.close();

		} while (choice == true);

	}

	// inutile dirlo no?
	@Override
	public void sfClose() {
		sf.close();
		console.close();

	}

}
