package com.francescoruta.prova_finale_ing_sw.models;

import java.io.Serializable;

import com.francescoruta.prova_finale_ing_sw.utils.simplemapping.NeedsMapping;

public class ArticoloDistintaBase implements Serializable {
	private Long id;
	private Double qta;
	@NeedsMapping
	private Articolo articolo;
	@NeedsMapping
	private DistintaBase distintaBase;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Articolo getArticolo() {
		return this.articolo;
	}

	public void setArticolo(Articolo articolo) {
		this.articolo = articolo;
	}

	public DistintaBase getDistintaBase() {
		return this.distintaBase;
	}

	public void setDistintaBase(DistintaBase distintaBase) {
		this.distintaBase = distintaBase;
	}

	public Double getQta() {
		return this.qta;
	}

	public void setQta(Double qta) {
		this.qta = qta;
	}
	
}
