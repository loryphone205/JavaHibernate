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

public class VisitaRepository implements StudioMedicoDAO<Visita> {

	// chiamiamo la SessionFacotry, così possiamo agire con le Session e di
	// conseguenza Hibernate
	private static SessionFactory sf;

	// apro anche lo scanner, mi servirà sicuramente per l'immissione dei dati
	Scanner console = new Scanner(System.in);

	// continua mi permette di decidere sei continuare a inserire dati o no
	// a sto punto si, la potevo creare nell'interfaccia.
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

	// resttuisce medico vuoto con l'id inserito
	private Medico checkMedico(int id) {
		// non si sa mai, meglio prevenire
		console.nextLine();
		if (id <= 0) {
			do {
				System.out.print("Inserisci un id valido: ");
				id = console.nextInt();
				console.nextLine();
			} while (id <= 0);
		}
		Medico medico = new Medico(id);
		return medico;
	}

	// resttuisce paziente vuoto con l'id inserito
	private Paziente checkPaziente(int id) {
		// non si sa mai, meglio prevenire
		console.nextLine();
		if (id <= 0) {
			do {
				System.out.print("Inserisci un id valido: ");
				id = console.nextInt();
				console.nextLine();
			} while (id <= 0);
		}
		Paziente paziente = new Paziente(id);
		return paziente;
	}

	// funzione che mi restituisce l'id isnerito, dop oun breve check in filiale
	private int checkId() {
		// chiedo l'id
		System.out.println("Inserisci l'id della visita: ");
		int id = console.nextInt();
		console.nextLine();

		// sempre il solito controllo
		if (id <= 0) {
			do {
				System.out.print("Inserisci un id valido: ");
				id = console.nextInt();
				console.nextLine();
			} while (id <= 0);
		}
		return id;
	}

	// questo metodo me lo credo per comodità. Non è richiesto, ma lo voglio fare
	// perchè, nel mio ideale, si può eliminare sia in base all'id dei pazienti che
	// all'id del medico
	public void delete(List<Visita> visite, int scelta) throws SQLException {

		Session sessione = sf.openSession();

		// scelta mi permette di capire cosa devo fare nello switch
		// non è un problema che esca direttamente dopo aver eseguito questa operazione,
		// lo chiedo nel main nel caso lo voglia rifare
		switch (scelta) {

		case 2:
			System.out.println("Attenzione! Inserire un numero valido.");
			System.out.println("Se non si sa che paziente cercare, tornare al menù precedente e selezionare 'Stampa'.");
			System.out.print("Inserire l'id del paziente: ");
			int idPaziente = console.nextInt();
			console.nextLine();

			// piccolo check
			if (idPaziente <= 0) {
				do {
					System.out.print("Inserisci un id valido: ");
					idPaziente = console.nextInt();
					console.nextLine();
				} while (idPaziente <= 0);
			}

			// preso l'id, posso eliminare tutti i pazienti con quell'id dalla visita
			for (Visita visita : visite) {
				if (visita.getIdPaziente().getIdPaziente() == idPaziente) {
					sessione.beginTransaction();
					sessione.remove(visita);
					sessione.getTransaction().commit();
				}
			}
			break;

		case 3:
			System.out.println("Attenzione! Inserire un numero valido.");
			System.out.println("Se non si sa che medico cercare, tornare al menù precedente e selezionare 'Stampa'.");
			System.out.print("Inserire l'id del medico: ");
			int idMedico = console.nextInt();
			console.nextLine();

			// piccolo check
			if (idMedico <= 0) {
				do {
					System.out.print("Inserisci un id valido: ");
					idMedico = console.nextInt();
					console.nextLine();
				} while (idMedico <= 0);
			}

			// preso l'id, posso eliminare tutti i medici con quell'id dalla visita
			for (Visita visita : visite) {
				if (visita.getIdMedico().getIdMedico() == idMedico) {
					sessione.beginTransaction();
					sessione.remove(visita);
					sessione.getTransaction().commit();
				}
			}
			break;
		}

	}

	// setup
	@Override
	public void setup() {
		System.out.println("Apro la connessione");
		Configuration cfg = new Configuration();
		cfg.addAnnotatedClass(Medico.class);
		cfg.addAnnotatedClass(Visita.class);
		cfg.addAnnotatedClass(Paziente.class);
		sf = cfg.configure("hibernate.cfg.xml").buildSessionFactory();
	}

	// crea record
	@Override
	public void create() {

		// presuppongo sempre di entrarci una volta almeno
		boolean choice = true;
		do {

			// apro la sessione
			Session sessione = sf.openSession();

			// visita vuota
			Visita visita = new Visita();

			// chiedo i dati
			System.out.println("Per piacere, fare attenzione quando si inseriscono gli id dei pazienti e medici.");
			System.out.println("Inserisci la tipologia della visita: ");
			visita.setTipoVisita(console.nextLine());
			System.out.print("Inserisci l'id del medico: ");
			visita.setIdMedico(checkMedico(console.nextInt()));
			System.out.println("Inserisci l'id del paziente: ");
			visita.setIdPaziente(checkPaziente(console.nextInt()));

			// faccio la transaction
			sessione.beginTransaction();
			sessione.persist(visita);
			sessione.getTransaction().commit();

			choice = continua();

			sessione.close();

		} while (choice == true);

	}

	// select *
	@Override
	public List<Visita> readAll() {

		// apro la sessione
		Session sessione = sf.openSession();

		// uso la query per farmi un select praticamente
		String hql = "FROM Visita";
		Query<Visita> query = sessione.createQuery(hql, Visita.class);
		for (Visita visita : query.list()) {
			System.out.println(visita);
		}

		// chiudo
		return query.list();

	}

	// read singolo
	@Override
	public void read() throws SQLException {

		System.out.println("Attenzione! Inserire un numero valido.");
		System.out.println("Se non si sa che visita cercare, tornare al menù precedente e selezionare 'Stampa'.");
		// sempre il mio ciclo
		boolean choice = true;
		do {
			// apro la sessione
			Session sessione = sf.openSession();

			// il metodo che mi sono creato uwu
			int id = checkId();

			// con l'id di ritorno posso fare la get
			sessione.beginTransaction();
			System.out.println(sessione.get(Visita.class, id));
			sessione.getTransaction().commit();

			// uso sempre la mia choice
			choice = continua();

			sessione.close();
		} while (choice == true);

	}

	// edit come lo sappiamo noi
	@Override
	public void edit() throws SQLException {
		// apro la sessione
		Session sessione = sf.openSession();

		System.out.println("Attenzione! Inserire un numero valido.");
		System.out.println("Se non si sa che visita cercare, tornare al menù precedente e selezionare 'Stampa'.");
		// sempre il mio ciclo
		boolean choice = true;
		do {
			// dati
			// il metodo che mi sono creato uwu
			int id = checkId();
			Visita visita = new Visita(id);
			System.out.println("Inserisci la tipologia della visita: ");
			visita.setTipoVisita(console.nextLine());
			System.out.print("Inserisci l'id del medico: ");
			visita.setIdMedico(checkMedico(console.nextInt()));
			System.out.println("Inserisci l'id del paziente: ");
			visita.setIdPaziente(checkPaziente(console.nextInt()));

			// transaction
			// faccio la transaction
			sessione.beginTransaction();
			sessione.merge(visita);
			sessione.getTransaction().commit();

			choice = continua();

		} while (choice == true);

		sessione.close();

	}

	// ora, la delete può essere di vario genere in questa tabella: 1) Eliminamo un
	// singolo record basandoci sull'id della visita, 2) Deletiamo tutte le visite
	// con paziente id, 3) Deletiamo tutte le visite con medico id.
	// quindi creo 3 delete diverse, una agisce solo per eliminare la singola
	// visita, ovvero delete
	// l'altra invece eliminerà in base all'idPaziene o idMedico, dato che non posso
	// dare a delete la lista di ingresso
	@Override
	public void delete() throws SQLException {

		System.out.println("Attenzione! Inserire un numero valido.");
		System.out.println("Se non si sa che visita eliminare, tornare al menù precedente e selezionare 'Stampa'.");
		boolean choice = true;
		do {
			// apro la sessione
			Session sessione = sf.openSession();

			// il metodo che mi sono creato uwu
			int id = checkId();

			// creo la visita
			Visita visita = new Visita(id);

			// elimino
			sessione.beginTransaction();
			sessione.remove(visita);
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
