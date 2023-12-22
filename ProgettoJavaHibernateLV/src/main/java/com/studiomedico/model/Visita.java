package com.studiomedico.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

//assegno la classe medico alla tabella visita del DB
@Entity
@Table(name = "visita")
public class Visita {

	// primary key della tabella Paziente del DB con auto_increment, colonna
	// idVisita
	@Id
	@Column(name = "idVisita")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idVisita;

	// colonna DB: tipoVisita
	@Column(name = "tipoVisita")
	private String tipoVisita;

	// qui ci sono le due FK delle tabelle Medico e Paziente, con le rispettive
	// relazioni
	// prima relazione: idPaziente di visita a idPaziente di maziente
	@ManyToOne
	@JoinColumn(name = "idPaziente", referencedColumnName = "idPaziente")
	private Paziente idPaziente;

	// seconda relazione: idMedico di visita a idMedico di medico
	@ManyToOne
	@JoinColumn(name = "idMedico", referencedColumnName = "idMedico")
	private Medico idMedico;

	// generazione costruttori/setter/getter/ecc
	public Visita(String tipoVisita, Paziente idPaziente, Medico idMedico) {
		super();
		this.tipoVisita = tipoVisita;
		this.idPaziente = idPaziente;
		this.idMedico = idMedico;
	}

	public Visita() {

	}
	
	public Visita(int id) {
		this.idVisita = id;
	}

	public int getIdVisita() {
		return idVisita;
	}

	public void setIdVisita(int idVisita) {
		this.idVisita = idVisita;
	}

	public String getTipoVisita() {
		return tipoVisita;
	}

	public void setTipoVisita(String tipoVisita) {
		this.tipoVisita = tipoVisita;
	}

	public Paziente getIdPaziente() {
		return idPaziente;
	}

	public void setIdPaziente(Paziente idPaziente) {
		this.idPaziente = idPaziente;
	}

	public Medico getIdMedico() {
		return idMedico;
	}

	public void setIdMedico(Medico idMedico) {
		this.idMedico = idMedico;
	}

	@Override
	public String toString() {
		return "Visita [idVisita=" + idVisita + ", tipoVisita=" + tipoVisita + ", idPaziente=" + idPaziente
				+ ", idMedico=" + idMedico + "]";
	}

}
