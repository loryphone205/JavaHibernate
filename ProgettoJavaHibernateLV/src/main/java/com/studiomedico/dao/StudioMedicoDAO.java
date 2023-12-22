package com.studiomedico.dao;

import java.sql.SQLException;
import java.util.List;

public interface StudioMedicoDAO<T> {

	// metodo setup, uguale per tutti
	void setup();
	
	// metodo di crea, non ha bisogno di parametri di ingresso
	void create();

	// readAll, è possibile crearla per tutti, e non ha bisogno di parametri di
	// ingresso, dato che può non restituire nulla
	List<T> readAll();

	// per leggere un singolo elemento dal database, sempre senza ritorno
	void read() throws SQLException;

	// per editare un record del db, ho semplicemente bisogno dell'id da modificare
	void edit() throws SQLException;
	
	// per la delete ho bisogno sempre solo dell'id
	void delete() throws SQLException;
	
	//metodo close della session factory
	void sfClose();
}
