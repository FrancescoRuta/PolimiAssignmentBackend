package com.francescoruta.prova_finale_ing_sw.models;

import java.util.Objects;

import com.francescoruta.prova_finale_ing_sw.utils.simplemapping.NeedsMapping;

public class ScaricoDiProduzione {
	private Long id;
	private Double qta;
	@NeedsMapping
	private Articolo articolo;
	@NeedsMapping
	private Produzione produzione;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getQta() {
		return this.qta;
	}

	public void setQta(Double qta) {
		this.qta = qta;
	}

	public Articolo getArticolo() {
		return this.articolo;
	}

	public void setArticolo(Articolo articolo) {
		this.articolo = articolo;
	}

	public Produzione getProduzione() {
		return this.produzione;
	}

	public void setProduzione(Produzione produzione) {
		this.produzione = produzione;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof ScaricoDiProduzione)) {
			return false;
		}
		ScaricoDiProduzione scaricoDiProduzione = (ScaricoDiProduzione) o;
		return Objects.equals(id, scaricoDiProduzione.id) && Objects.equals(qta, scaricoDiProduzione.qta) && Objects.equals(articolo, scaricoDiProduzione.articolo) && Objects.equals(produzione, scaricoDiProduzione.produzione);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, qta, articolo, produzione);
	}

	@Override
	public String toString() {
		return "{" +
			" id='" + getId() + "'" +
			", qta='" + getQta() + "'" +
			", articolo='" + getArticolo() + "'" +
			", produzione='" + getProduzione() + "'" +
			"}";
	}
	
}
