package com.francescoruta.prova_finale_ing_sw.models;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import com.francescoruta.prova_finale_ing_sw.utils.simplemapping.NeedsMapping;

public class Produzione {
	private Long id;
	private Double qtaProdotta;
	private LocalDateTime inizioProduzione;
	private LocalDateTime fineProduzione;
	private StatoProduzione stato;
	@NeedsMapping
	private Articolo articoloProdotto;
	@NeedsMapping
	private ImpiantoDiProduzione impiantoDiProduzione;
	@NeedsMapping
	private List<ScaricoDiProduzione> scarichiDiProduzione;
	@NeedsMapping
	private DistintaBase distintaBase;
	

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getQtaProdotta() {
		return this.qtaProdotta;
	}

	public void setQtaProdotta(Double qtaProdotta) {
		this.qtaProdotta = qtaProdotta;
	}

	public LocalDateTime getInizioProduzione() {
		return this.inizioProduzione;
	}

	public void setInizioProduzione(LocalDateTime inizioProduzione) {
		this.inizioProduzione = inizioProduzione;
	}

	public LocalDateTime getFineProduzione() {
		return this.fineProduzione;
	}

	public void setFineProduzione(LocalDateTime fineProduzione) {
		this.fineProduzione = fineProduzione;
	}

	public StatoProduzione getStato() {
		return this.stato;
	}

	public void setStato(StatoProduzione stato) {
		this.stato = stato;
	}

	public Articolo getArticoloProdotto() {
		return this.articoloProdotto;
	}

	public void setArticoloProdotto(Articolo articoloProdotto) {
		this.articoloProdotto = articoloProdotto;
	}

	public ImpiantoDiProduzione getImpiantoDiProduzione() {
		return this.impiantoDiProduzione;
	}

	public void setImpiantoDiProduzione(ImpiantoDiProduzione impiantoDiProduzione) {
		this.impiantoDiProduzione = impiantoDiProduzione;
	}

	public List<ScaricoDiProduzione> getScarichiDiProduzione() {
		return this.scarichiDiProduzione;
	}

	public void setScarichiDiProduzione(List<ScaricoDiProduzione> scarichiDiProduzione) {
		this.scarichiDiProduzione = scarichiDiProduzione;
	}

	public DistintaBase getDistintaBase() {
		return this.distintaBase;
	}

	public void setDistintaBase(DistintaBase distintaBase) {
		this.distintaBase = distintaBase;
	}

	@Override
	public String toString() {
		return "{" +
			" id='" + getId() + "'" +
			", qtaProdotta='" + getQtaProdotta() + "'" +
			", inizioProduzione='" + getInizioProduzione() + "'" +
			", fineProduzione='" + getFineProduzione() + "'" +
			", stato='" + getStato() + "'" +
			", articoloProdotto='" + getArticoloProdotto() + "'" +
			", impiantoDiProduzione='" + getImpiantoDiProduzione() + "'" +
			", scarichiDiProduzione='" + getScarichiDiProduzione() + "'" +
			", distintaBase='" + getDistintaBase() + "'" +
			"}";
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof Produzione)) {
			return false;
		}
		Produzione produzione = (Produzione) o;
		return Objects.equals(id, produzione.id) && Objects.equals(qtaProdotta, produzione.qtaProdotta) && Objects.equals(inizioProduzione, produzione.inizioProduzione) && Objects.equals(fineProduzione, produzione.fineProduzione) && Objects.equals(stato, produzione.stato) && Objects.equals(articoloProdotto, produzione.articoloProdotto) && Objects.equals(impiantoDiProduzione, produzione.impiantoDiProduzione) && Objects.equals(scarichiDiProduzione, produzione.scarichiDiProduzione) && Objects.equals(distintaBase, produzione.distintaBase);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, qtaProdotta, inizioProduzione, fineProduzione, stato, articoloProdotto, impiantoDiProduzione, scarichiDiProduzione, distintaBase);
	}

}
