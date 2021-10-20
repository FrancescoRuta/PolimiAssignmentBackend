package com.francescoruta.prova_finale_ing_sw.entities;

import java.util.Objects;

import javax.persistence.*;

import com.francescoruta.prova_finale_ing_sw.utils.simplemapping.NeedsMapping;


@Entity
public class ScaricoDiProduzioneEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false, updatable = false)
	private Long id;
	private Double qta;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@NeedsMapping
	private ArticoloEntity articolo;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@NeedsMapping
	private ProduzioneEntity produzione;

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

	public ArticoloEntity getArticolo() {
		return this.articolo;
	}

	public void setArticolo(ArticoloEntity articolo) {
		this.articolo = articolo;
	}

	public ProduzioneEntity getProduzione() {
		return this.produzione;
	}

	public void setProduzione(ProduzioneEntity produzione) {
		this.produzione = produzione;
	}	

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof ScaricoDiProduzioneEntity)) {
			return false;
		}
		ScaricoDiProduzioneEntity scaricoDistintaBase = (ScaricoDiProduzioneEntity) o;
		return Objects.equals(id, scaricoDistintaBase.id) && Objects.equals(qta, scaricoDistintaBase.qta) && Objects.equals(articolo, scaricoDistintaBase.articolo) && Objects.equals(produzione, scaricoDistintaBase.produzione);
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
			", ordineDiProduzione='" + getProduzione() + "'" +
			"}";
	}
	
}
