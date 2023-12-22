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

//assegno la classe paziente alla tabella paziente del DB
@Entity
@Table(name = "paziente")
public class Paziente {

	// primary key della tabella Paziente del DB con auto_increment, colonna
	// idPaziente
	@Id
	@Column(name = "idPaziente")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idPaziente;

	// colonna DB: nome
	@Column(name = "nome")
	private String nome;

	// colonna DB: cognome
	@Column(name = "cognome")
	private String cognome;

	// colonna DB: dataNascita
	@Column(name = "dataNascita")
	private String dataNascita;

	// associazione alla tabella visite 1-N
	@OneToMany(mappedBy = "idPaziente", cascade = CascadeType.ALL)
	Set<Visita> visite = new HashSet<Visita>();

	// genero costruttori, setter e getter, e magari anche un toString che non fa
	// mai male
	// il costruttore lo genero senza ID dato che è auto_increment
	public Paziente(String nome, String cognome, String dataNascita) {
		super();
		this.nome = nome;
		this.cognome = cognome;
		this.dataNascita = dataNascita;
	}

	// un costruttore vuoto può sempre tornarmi utile, lo creo per il futuro
	public Paziente() {

	}
	
	public Paziente(int id) {
		this.idPaziente = id;
	}

	public int getIdPaziente() {
		return idPaziente;
	}

	public void setIdPaziente(int idPaziente) {
		this.idPaziente = idPaziente;
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

	public String getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(String dataNascita) {
		this.dataNascita = dataNascita;
	}

	@Override
	public String toString() {
		return "Paziente [idPaziente=" + idPaziente + ", nome=" + nome + ", cognome=" + cognome + "]";
	}

}
