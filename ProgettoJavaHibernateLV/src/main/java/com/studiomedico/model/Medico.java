package com.studiomedico.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.CascadeType;

//assegno la classe medico alla tabella paziente del DB
@Entity
@Table(name = "medico")
public class Medico {

	// primary key della tabella Paziente del DB con auto_increment, colonna
	// idMedico
	@Id
	@Column(name = "idMedico")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idMedico;

	// colonna DB: nome
	@Column(name = "nome")
	private String nome;

	// colonna DB: cognome
	@Column(name = "cognome")
	private String cognome;

	// colonna DB: specializzazione
	@Column(name = "specializzazione")
	private String specializzazione;

	// associazione alla tabella visite 1-N
	@OneToMany(mappedBy = "idMedico", cascade = CascadeType.ALL)
	Set<Visita> visite = new HashSet<Visita>();
	

	//creo, anche qui, i miei due costruttori e i vari getter e setter
	public Medico(String nome, String cognome, String specializzazione) {
		super();
		this.nome = nome;
		this.cognome = cognome;
		this.specializzazione = specializzazione;
	}

	public Medico() {

	}
	
	public Medico(int idMedico) {
		this.idMedico = idMedico;
	}

	public int getIdMedico() {
		return idMedico;
	}

	public void setIdMedico(int idMedico) {
		this.idMedico = idMedico;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getSpecializzazione() {
		return specializzazione;
	}

	public void setSpecializzazione(String specializzazione) {
		this.specializzazione = specializzazione;
	}

	@Override
	public String toString() {
		return "Medico [idMedico=" + idMedico + ", nome=" + nome + ", cognome=" + cognome + ", specializzazione="
				+ specializzazione + "]";
	}

}
