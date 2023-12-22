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

public class MedicoRepository implements StudioMedicoDAO<Medico> {

	// chiamiamo la SessionFacotry, così possiamo agire con le Session e di
	// conseguenza Hibernate
	private static SessionFactory sf;

	// apro anche lo scanner, mi servirà sicuramente per l'immissione dei dati
	Scanner console = new Scanner(System.in);

	// mi faccio una funzione locale per risparmiare codice
	private boolean continua() {
		// faccio un controllo sull'input quando chiedo se vuole inserire altri dati
		boolean innerChoice = true;
		do {
			// chiedo se vuole aggiungere altri medici
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

	// metodo create, faccio un loop interno per fargli inserire quanti dati vuole
	@Override
	public void create() {

		// choice mi permette di controllare il voler continuare ad aggiungere utenti o
		// meno
		// uso il do perchè presuppongo di andarci almeno una volta in questo ciclo
		boolean choice = true;
		do {

			// apro la sessione
			Session sessione = sf.openSession();

			// creo un medico senza valori da fillare
			Medico medico = new Medico();

			// chiedo i dati
			System.out.print("Nome: ");
			medico.setNome(console.nextLine());
			System.out.print("Cognome: ");
			medico.setCognome(console.nextLine());
			System.out.print("Specializzazione: ");
			medico.setSpecializzazione(console.nextLine());

			// faccio la transaction
			sessione.beginTransaction();
			sessione.persist(medico);
			sessione.getTransaction().commit();

			choice = continua();

			sessione.close();

		} while (choice == true);

		// chiudo la sessione

	}

	// metodo per leggere tutti i medici
	@Override
	public List<Medico> readAll() {

		// apro la sessione
		Session sessione = sf.openSession();

		// uso la query per farmi un select praticamente
		String hql = "FROM Medico";
		Query<Medico> query = sessione.createQuery(hql, Medico.class);
		for (Medico medico : query.list()) {
			System.out.println(medico);
		}

		// chiudo
		return query.list();

	}

	// metodo read singolo, che prende in input l'id del medico e restituisce una
	// sola riga in console
	@Override
	public void read() throws SQLException {

		// chiedo l'id da leggere per un piccolo controllo. anche qui metto un loop nel
		// caso
		// ci sia bisogno di leggere i dati di più medici ma uno alla volta
		System.out.println("Attenzione! Inserire un numero valido.");
		System.out.println("Se non si sa che medico cercare, tornare al menù precedente e selezionare 'Stampa'.");
		boolean choice = true;
		do {

			// apro la sessione
			Session sessione = sf.openSession();

			// chiedo i dati
			System.out.print("Inserisca l'id del medico da cercare: ");
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
			System.out.println(sessione.get(Medico.class, id));
			sessione.getTransaction().commit();

			// faccio un controllo sull'input quando chiedo se vuole mostrare altri dati
			choice = continua();

			sessione.close();

		} while (choice == true);

	}

	// l'edit chiede un id all'interno del metodo e poi richiede tutti i campi, così
	// da sovrascrivere i valori precedenti
	@Override
	public void edit() throws SQLException {

		// chiedo l'id da modificare e faccio un piccolo controllo. anche qui metto un
		// loop nel caso
		// ci sia bisogno di modificare i dati di più medici ma uno alla volta
		System.out.println("Attenzione! Inserire un numero valido.");
		System.out.println("Se non si sa che medico cercare, tornare al menù precedente e selezionare 'Stampa'.");
		boolean choice = true;
		do {

			// apro la sessione
			Session sessione = sf.openSession();

			// chiedo i dati
			System.out.print("Inserisca l'id del medico da cercare: ");
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

			// creo un medico senza valori da fillare
			Medico medico = new Medico(id);

			// chiedo i dati
			System.out.print("Nome: ");
			medico.setNome(console.nextLine());
			System.out.print("Cognome: ");
			medico.setCognome(console.nextLine());
			System.out.print("Specializzazione: ");
			medico.setSpecializzazione(console.nextLine());

			// faccio la transaction
			sessione.beginTransaction();
			sessione.merge(medico);
			sessione.getTransaction().commit();

			// faccio un controllo sull'input quando chiedo se vuole modificare altri dati
			choice = continua();

			sessione.close();

		} while (choice == true);

	}

	@Override
	public void delete() throws SQLException {

		// chiedo l'id da eliminare e faccio un piccolo controllo. anche qui metto un
		// loop nel caso
		// ci sia bisogno di eliminate i dati di più medici ma uno alla volta
		System.out.println("Attenzione! Inserire un numero valido.");
		System.out.println("Se non si sa che medico cercare, tornare al menù precedente e selezionare 'Stampa'.");
		boolean choice = true;
		do {
			// apro la sessione
			Session sessione = sf.openSession();

			// chiedo i dati
			System.out.print("Inserisca l'id del medico da cercare: ");
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

			// creo un medico con solo l'id, così Hibernate capisce chi eliminare
			Medico medico = new Medico(id);

			// inizio transazione
			sessione.beginTransaction();
			sessione.remove(medico);
			sessione.getTransaction().commit();

			choice = continua();

			sessione.close();

		} while (choice == true);

	}

	@Override
	public void sfClose() {
		sf.close();
		console.close();

	}

}
