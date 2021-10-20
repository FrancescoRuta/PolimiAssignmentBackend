package com.francescoruta.prova_finale_ing_sw.models;

import java.io.Serializable;
import java.util.*;

import com.francescoruta.prova_finale_ing_sw.utils.simplemapping.NeedsMapping;

public class DistintaBase implements Serializable {
	private Long id;
	@NeedsMapping
	private Articolo articoloProdotto;
	private String descrizione;
	@NeedsMapping
	private List<ArticoloDistintaBase> articoliDistintaBase;
	

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof DistintaBase)) {
			return false;
		}
		DistintaBase distintaBase = (DistintaBase) o;
		return Objects.equals(id, distintaBase.id) && Objects.equals(articoloProdotto, distintaBase.articoloProdotto) && Objects.equals(descrizione, distintaBase.descrizione) && Objects.equals(articoliDistintaBase, distintaBase.articoliDistintaBase);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, articoloProdotto, descrizione, articoliDistintaBase);
	}


	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Articolo getArticoloProdotto() {
		return this.articoloProdotto;
	}

	public void setArticoloProdotto(Articolo articoloProdotto) {
		this.articoloProdotto = articoloProdotto;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public List<ArticoloDistintaBase> getArticoliDistintaBase() {
		return this.articoliDistintaBase;
	}

	public void setArticoliDistintaBase(List<ArticoloDistintaBase> articoliDistintaBase) {
		this.articoliDistintaBase = articoliDistintaBase;
	}
	
}
