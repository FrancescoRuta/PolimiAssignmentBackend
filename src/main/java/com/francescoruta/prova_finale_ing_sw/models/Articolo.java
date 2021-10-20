package com.francescoruta.prova_finale_ing_sw.models;

import java.io.Serializable;
import java.util.*;

import com.francescoruta.prova_finale_ing_sw.utils.simplemapping.NeedsMapping;

public class Articolo implements Serializable {
	private Long id;
	private String codice;
	private String descrizione;
	private UnitaDiMisura unitaDiMisura;
	private String descrizioneEstesa;
	private Double qtaGiacenza;
	@NeedsMapping
	private List<DistintaBase> distinteBase;
	@NeedsMapping
	private List<ArticoloDistintaBase> articoloDistinteBase;
	@NeedsMapping
	private List<ArticoloQtaUpdate> qtaUpdates;
	@NeedsMapping
	private List<Produzione> produzioni;
	@NeedsMapping
	private List<ScaricoDiProduzione> scarichiDiProduzione;
	

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodice() {
		return this.codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public UnitaDiMisura getUnitaDiMisura() {
		return this.unitaDiMisura;
	}

	public void setUnitaDiMisura(UnitaDiMisura unitaDiMisura) {
		this.unitaDiMisura = unitaDiMisura;
	}

	public String getDescrizioneEstesa() {
		return this.descrizioneEstesa;
	}

	public void setDescrizioneEstesa(String descrizioneEstesa) {
		this.descrizioneEstesa = descrizioneEstesa;
	}

	public Double getQtaGiacenza() {
		return this.qtaGiacenza;
	}

	public void setQtaGiacenza(Double qtaGiacenza) {
		this.qtaGiacenza = qtaGiacenza;
	}

	public List<DistintaBase> getDistinteBase() {
		return this.distinteBase;
	}

	public void setDistinteBase(List<DistintaBase> distinteBase) {
		this.distinteBase = distinteBase;
	}

	public List<ArticoloDistintaBase> getArticoloDistinteBase() {
		return this.articoloDistinteBase;
	}

	public void setArticoloDistinteBase(List<ArticoloDistintaBase> articoloDistinteBase) {
		this.articoloDistinteBase = articoloDistinteBase;
	}

	public List<ArticoloQtaUpdate> getQtaUpdates() {
		return this.qtaUpdates;
	}

	public void setQtaUpdates(List<ArticoloQtaUpdate> qtaUpdates) {
		this.qtaUpdates = qtaUpdates;
	}

	public List<Produzione> getProduzioni() {
		return this.produzioni;
	}

	public void setProduzioni(List<Produzione> produzioni) {
		this.produzioni = produzioni;
	}

	public List<ScaricoDiProduzione> getScarichiDiProduzione() {
		return this.scarichiDiProduzione;
	}

	public void setScarichiDiProduzione(List<ScaricoDiProduzione> scarichiDiProduzione) {
		this.scarichiDiProduzione = scarichiDiProduzione;
	}
	
	
}
