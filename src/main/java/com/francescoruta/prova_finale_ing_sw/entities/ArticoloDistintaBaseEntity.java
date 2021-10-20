package com.francescoruta.prova_finale_ing_sw.entities;

import java.util.Objects;

import javax.persistence.*;

import com.francescoruta.prova_finale_ing_sw.utils.simplemapping.NeedsMapping;


@Entity
public class ArticoloDistintaBaseEntity {
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
	private DistintaBaseEntity distintaBase;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ArticoloEntity getArticolo() {
		return this.articolo;
	}

	public void setArticolo(ArticoloEntity articolo) {
		this.articolo = articolo;
	}

	public DistintaBaseEntity getDistintaBase() {
		return this.distintaBase;
	}

	public void setDistintaBase(DistintaBaseEntity distintaBase) {
		this.distintaBase = distintaBase;
	}

	public Double getQta() {
		return this.qta;
	}

	public void setQta(Double qta) {
		this.qta = qta;
	}

	@Override
	public String toString() {
		return "{" +
			" id='" + getId() + "'" +
			", articolo='" + getArticolo() + "'" +
			", distintaBase='" + getDistintaBase() + "'" +
			", qta='" + getQta() + "'" +
			"}";
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof ArticoloDistintaBaseEntity)) {
			return false;
		}
		ArticoloDistintaBaseEntity articoloDistintaBaseEntity = (ArticoloDistintaBaseEntity) o;
		return Objects.equals(id, articoloDistintaBaseEntity.id) && Objects.equals(articolo, articoloDistintaBaseEntity.articolo) && Objects.equals(distintaBase, articoloDistintaBaseEntity.distintaBase) && Objects.equals(qta, articoloDistintaBaseEntity.qta);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, articolo, distintaBase, qta);
	}
	
	
}
