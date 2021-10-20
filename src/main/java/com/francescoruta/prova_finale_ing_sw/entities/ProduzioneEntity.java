package com.francescoruta.prova_finale_ing_sw.entities;

import java.time.LocalDateTime;
import java.util.*;

import javax.persistence.*;

import com.francescoruta.prova_finale_ing_sw.models.StatoProduzione;
import com.francescoruta.prova_finale_ing_sw.utils.simplemapping.NeedsMapping;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class ProduzioneEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false, updatable = false)
	private Long id;
	private Double qtaProdotta;
	private LocalDateTime inizioProduzione;
	private LocalDateTime fineProduzione;
	@Enumerated(EnumType.ORDINAL)
	private StatoProduzione stato;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@NeedsMapping
	private ArticoloEntity articoloProdotto;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@NeedsMapping
	private ImpiantoDiProduzioneEntity impiantoDiProduzione;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "produzione", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, orphanRemoval = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@NeedsMapping
	private List<ScaricoDiProduzioneEntity> scarichiDiProduzione = new ArrayList<>();
	

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

	public ArticoloEntity getArticoloProdotto() {
		return this.articoloProdotto;
	}

	public void setArticoloProdotto(ArticoloEntity articoloProdotto) {
		this.articoloProdotto = articoloProdotto;
	}

	public ImpiantoDiProduzioneEntity getImpiantoDiProduzione() {
		return this.impiantoDiProduzione;
	}

	public void setImpiantoDiProduzione(ImpiantoDiProduzioneEntity impiantoDiProduzione) {
		this.impiantoDiProduzione = impiantoDiProduzione;
	}

	public List<ScaricoDiProduzioneEntity> getScarichiDiProduzione() {
		return this.scarichiDiProduzione;
	}
	
	public void setScarichiDiProduzione(List<ScaricoDiProduzioneEntity> scarichiDiProduzione) {
		if (scarichiDiProduzione == null) return;
		scarichiDiProduzione.stream().forEach(d -> d.setProduzione(this));
		if (this.scarichiDiProduzione == null) {
			this.scarichiDiProduzione = scarichiDiProduzione;
		} else {
			this.scarichiDiProduzione.stream().forEach(d -> d.setProduzione(null));
			this.scarichiDiProduzione.clear();
			this.scarichiDiProduzione.addAll(scarichiDiProduzione);
		}
	}

	public void addScarichiDiProduzione(ScaricoDiProduzioneEntity scaricoDiProduzione) {
		scaricoDiProduzione.setProduzione(this);
		this.scarichiDiProduzione.add(scaricoDiProduzione);
	}

	public void removeScarichiDiProduzione(ScaricoDiProduzioneEntity scaricoDiProduzione) {
		scaricoDiProduzione.setProduzione(null);
		this.scarichiDiProduzione.remove(scaricoDiProduzione);
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
			", articoliDistintaBase='" + getScarichiDiProduzione() + "'" +
			"}";
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof ProduzioneEntity)) {
			return false;
		}
		ProduzioneEntity produzioneEntity = (ProduzioneEntity) o;
		return Objects.equals(id, produzioneEntity.id) && Objects.equals(qtaProdotta, produzioneEntity.qtaProdotta) && Objects.equals(inizioProduzione, produzioneEntity.inizioProduzione) && Objects.equals(fineProduzione, produzioneEntity.fineProduzione) && Objects.equals(stato, produzioneEntity.stato) && Objects.equals(articoloProdotto, produzioneEntity.articoloProdotto) && Objects.equals(impiantoDiProduzione, produzioneEntity.impiantoDiProduzione) && Objects.equals(scarichiDiProduzione, produzioneEntity.scarichiDiProduzione);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, qtaProdotta, inizioProduzione, fineProduzione, stato, articoloProdotto, impiantoDiProduzione, scarichiDiProduzione);
	}
	
}
