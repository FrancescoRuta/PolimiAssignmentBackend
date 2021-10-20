package com.francescoruta.prova_finale_ing_sw.entities;

import java.util.*;

import javax.persistence.*;

import com.francescoruta.prova_finale_ing_sw.utils.simplemapping.NeedsMapping;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class DistintaBaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false, updatable = false)
	private Long id;
	private String descrizione;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@NeedsMapping
	private ArticoloEntity articoloProdotto;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "distintaBase", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, orphanRemoval = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@NeedsMapping
	private List<ArticoloDistintaBaseEntity> articoliDistintaBase = new ArrayList<>();
	

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ArticoloEntity getArticoloProdotto() {
		return this.articoloProdotto;
	}

	public void setArticoloProdotto(ArticoloEntity articoloProdotto) {
		this.articoloProdotto = articoloProdotto;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public List<ArticoloDistintaBaseEntity> getArticoliDistintaBase() {
		return this.articoliDistintaBase;
	}

	public void setArticoliDistintaBase(List<ArticoloDistintaBaseEntity> articoliDistintaBase) {
		if (articoliDistintaBase == null) return;
		articoliDistintaBase.stream().forEach(d -> d.setDistintaBase(this));
		if (this.articoliDistintaBase == null) {
			this.articoliDistintaBase = articoliDistintaBase;
		} else {
			this.articoliDistintaBase.stream().forEach(d -> d.setArticolo(null));
			this.articoliDistintaBase.clear();
			this.articoliDistintaBase.addAll(articoliDistintaBase);
		}
	}
	
	public void addArticoliDistintaBase(ArticoloDistintaBaseEntity articoliDistintaBase) {
		articoliDistintaBase.setDistintaBase(this);
		this.articoliDistintaBase.add(articoliDistintaBase);
	}

	public void removeArticoloDistinteBase(ArticoloDistintaBaseEntity articoliDistintaBase) {
		articoliDistintaBase.setDistintaBase(null);
		this.articoliDistintaBase.remove(articoliDistintaBase);
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof DistintaBaseEntity)) {
			return false;
		}
		DistintaBaseEntity distintaBaseEntity = (DistintaBaseEntity) o;
		return Objects.equals(id, distintaBaseEntity.id) && Objects.equals(articoloProdotto, distintaBaseEntity.articoloProdotto) && Objects.equals(descrizione, distintaBaseEntity.descrizione) && Objects.equals(articoliDistintaBase, distintaBaseEntity.articoliDistintaBase);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, articoloProdotto, descrizione, articoliDistintaBase);
	}

	@Override
	public String toString() {
		return "{" +
			" id='" + getId() + "'" +
			", articoloProdotto='" + getArticoloProdotto() + "'" +
			", descrizione='" + getDescrizione() + "'" +
			", articoliDistintaBase='" + getArticoliDistintaBase() + "'" +
			"}";
	}
	
	
}
