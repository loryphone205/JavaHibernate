/* Creazione Schema/Database */
create schema studiomedico;

/* Creazione tabelle come da Traccia.
 * Eseguire questi script uno ad uno, dato che è così che è stato creato
 * Inserisco 255 come grandezza perchè non è stata specificata */
create table medico (
	idMedico int auto_increment,
	nome varchar(255),
	cognome varchar(255),
	specializzazione varchar(255),
	constraint PK_idm primary key (idMedico)
);

/* dataNascita di 10: 2 per il giorno, 2 per il mese, 4 per l'anno e 2 per "/" */
create table paziente (
	idPaziente int auto_increment,
	nome varchar(255),
	cognome varchar(255),
	dataNascita varchar(10),
	constraint PK_ipd primary key (idPaziente)
);

/* Aggiungo ON DELETE CASCADE per comodità, altrimenti, durante la delete,
 * mi ritroverò a combattere con il Database. Ho spiegato questo nella Documentazione allegata */
create table visita (
	idVisita int auto_increment,
	tipoVisita varchar(255),
	idMedico int,
	idPaziente int,
	constraint PK_idv primary key (idVisita),
	constraint FK_idm foreign key (idMedico) references medico (idMedico) on delete cascade,
	constraint FK_idp foreign key (idPaziente) references paziente (idPaziente) on delete cascade
);

/* Inserisco dei dati così da potermi già trovare con un 
 * Database con cui poter giocare da Hibernate */
insert into medico (idMedico, nome, cognome, specializzazione)
values	(1, "Serena", "Dini", "Psicologia"),
		(2, "Giuseppe", "Mursia", "Ematologo"),
		(3, "Daniele", "Monaco", "Ragazzi...."),
		(4, "Pierluigi", "???", "Linux > Windows");
	
insert into paziente (idPaziente, nome, cognome, dataNascita) 
values	(1, "Lorenzo", "Vaccaro", "24/10/2000"),
		(2, "Lenny", "Rusciano", "00/00/2003"),
		(3, "Alessandro", "Peluso", "00/00/1996"),
		(4, "Simone Maria", "Luongo", "31/12/9999"),
		(5, "Salvatore", "Del Piano", "31/12/8888"),
		(6, "Christian", "Paciolla", "31/12/7777"),
		(7, "Diego", "Tursi", "31/12/2000"),
		(8, "Claudio", "Manganiello", "31/12/2003"),
		(9, "Gemma", "Ragi", "31/12/4444"),
		(10, "Giacomo", "Piccolo", "31/12/3333"),
		(11, "Antonio", "NonLoSoSorry", "31/12/2222");
	
insert into visita (idVisita, tipoVisita, idMedico, idPaziente)
values	(1, "Analisi Ematocrito", 2, 1),
		(2, "Sessione Di Analisi", 1, 3),
		(3, "Sessione Di Analisi", 1, 6),
		(4, "Sessione Di Analisi", 1, 9),
		(5, "Sessione Di Analisi", 1, 10),
		(6, "Sessione Di Analisi", 1, 4),
		(7, "Sessione Di Analisi", 1, 5),
		(8, "Ragazzi....", 3, 1),
		(9, "Ragazzi....", 3, 2),
		(10, "Ragazzi....", 3, 3),
		(11, "Ragazzi....", 3, 4),
		(12, "Ragazzi....", 3, 5),
		(13, "Ragazzi....", 3, 6),
		(14, "Ragazzi....", 3, 7),
		(15, "Ragazzi....", 3, 8),
		(16, "Ragazzi....", 3, 9),
		(17, "Ragazzi....", 3, 10),
		(18, "Ragazzi....", 3, 11);	

		
