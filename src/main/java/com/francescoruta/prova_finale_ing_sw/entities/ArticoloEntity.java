package com.francescoruta.prova_finale_ing_sw.entities;

import java.util.*;

import javax.persistence.*;

import com.francescoruta.prova_finale_ing_sw.models.UnitaDiMisura;
import com.francescoruta.prova_finale_ing_sw.utils.simplemapping.NeedsMapping;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class ArticoloEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false, updatable = false)
	private Long id;
	
	private String codice;
	private String descrizione;
	@Enumerated(EnumType.ORDINAL)
	private UnitaDiMisura unitaDiMisura;
	private String descrizioneEstesa;
	private Double qtaGiacenza;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "articoloProdotto", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, orphanRemoval = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@NeedsMapping
	private List<DistintaBaseEntity> distinteBase = new ArrayList<>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "articoloProdotto")
	@NeedsMapping
	private List<ProduzioneEntity> produzioni = new ArrayList<>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "articolo")
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	@NeedsMapping
	private List<ArticoloDistintaBaseEntity> articoloDistinteBase = new ArrayList<>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "articolo")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@NeedsMapping
	private List<ScaricoDiProduzioneEntity> scarichiDiProduzione = new ArrayList<>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "articolo", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, orphanRemoval = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@NeedsMapping
	private List<ArticoloQtaUpdateEntity> qtaUpdates = new ArrayList<>();

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

	public List<DistintaBaseEntity> getDistinteBase() {
		return this.distinteBase;
	}

	public void setDistinteBase(List<DistintaBaseEntity> distinteBase) {
		if (distinteBase == null) return;
		distinteBase.stream().forEach(d -> d.setArticoloProdotto(this));
		if (this.distinteBase == null) {
			this.distinteBase = distinteBase;
		} else {
			this.distinteBase.stream().forEach(d -> d.setArticoloProdotto(null));
			this.distinteBase.clear();
			this.distinteBase.addAll(distinteBase);
		}
	}

	public void addDistinteBase(DistintaBaseEntity distinteBase) {
		distinteBase.setArticoloProdotto(this);
		this.getDistinteBase().add(distinteBase);
	}

	public void removeDistinteBase(DistintaBaseEntity distinteBase) {
		distinteBase.setArticoloProdotto(null);
		this.distinteBase.remove(distinteBase);
	}
	
	public List<ArticoloDistintaBaseEntity> getArticoloDistinteBase() {
		return this.articoloDistinteBase;
	}

	public void setArticoloDistinteBase(List<ArticoloDistintaBaseEntity> articoloDistinteBase) {
		if (articoloDistinteBase == null) return;
		articoloDistinteBase.stream().forEach(d -> d.setArticolo(this));
		if (this.articoloDistinteBase == null) {
			this.articoloDistinteBase = articoloDistinteBase;
		} else {
			this.articoloDistinteBase.stream().forEach(d -> d.setArticolo(null));
			this.articoloDistinteBase.clear();
			this.articoloDistinteBase.addAll(articoloDistinteBase);
		}
	}

	public void addArticoloDistinteBase(ArticoloDistintaBaseEntity articoloDistinteBase) {
		articoloDistinteBase.setArticolo(this);
		this.getArticoloDistinteBase().add(articoloDistinteBase);
	}

	public void removeArticoloDistinteBase(ArticoloDistintaBaseEntity articoloDistinteBase) {
		articoloDistinteBase.setArticolo(null);
		this.articoloDistinteBase.remove(articoloDistinteBase);
	}
	
	public List<ScaricoDiProduzioneEntity> getScarichiDiProduzione() {
		return this.scarichiDiProduzione;
	}

	public void setScarichiDiProduzione(List<ScaricoDiProduzioneEntity> scarichiDiProduzione) {
		if (scarichiDiProduzione == null) return;
		scarichiDiProduzione.stream().forEach(d -> d.setArticolo(this));
		if (this.scarichiDiProduzione == null) {
			this.scarichiDiProduzione = scarichiDiProduzione;
		} else {
			this.scarichiDiProduzione.stream().forEach(d -> d.setArticolo(null));
			this.scarichiDiProduzione.clear();
			this.scarichiDiProduzione.addAll(scarichiDiProduzione);
		}
	}

	public void addScarichiDiProduzione(ScaricoDiProduzioneEntity scaricoDiProduzione) {
		scaricoDiProduzione.setArticolo(this);
		this.getScarichiDiProduzione().add(scaricoDiProduzione);
	}

	public void removeScarichiDiProduzione(ScaricoDiProduzioneEntity scaricoDiProduzione) {
		scaricoDiProduzione.setArticolo(null);
		this.scarichiDiProduzione.remove(scaricoDiProduzione);
	}

	public List<ProduzioneEntity> getProduzioni() {
		return this.produzioni;
	}

	public void setProduzioni(List<ProduzioneEntity> produzioni) {
		if (produzioni == null) return;
		produzioni.stream().forEach(d -> d.setArticoloProdotto(this));
		if (this.produzioni == null) {
			this.produzioni = produzioni;
		} else {
			this.produzioni.stream().forEach(d -> d.setArticoloProdotto(null));
			this.produzioni.clear();
			this.produzioni.addAll(produzioni);
		}
	}

	public void addProduzione(ProduzioneEntity produzione) {
		produzione.setArticoloProdotto(this);
		this.getProduzioni().add(produzione);
	}

	public void removeProduzione(ProduzioneEntity produzione) {
		produzione.setArticoloProdotto(null);
		this.produzioni.remove(produzione);
	}

	public List<ArticoloQtaUpdateEntity> getQtaUpdates() {
		return this.qtaUpdates;
	}

	public void setQtaUpdates(List<ArticoloQtaUpdateEntity> qtaUpdates) {
		if (qtaUpdates == null) return;
		qtaUpdates.stream().forEach(d -> d.setArticolo(this));
		if (this.qtaUpdates == null) {
			this.qtaUpdates = qtaUpdates;
		} else {
			this.qtaUpdates.stream().forEach(d -> d.setArticolo(null));
			this.qtaUpdates.clear();
			this.qtaUpdates.addAll(qtaUpdates);
		}
	}

	public void addQtaUpdates(ArticoloQtaUpdateEntity qtaUpdates) {
		qtaUpdates.setArticolo(this);
		this.getQtaUpdates().add(qtaUpdates);
	}

	public void removeQtaUpdates(ArticoloQtaUpdateEntity qtaUpdates) {
		qtaUpdates.setArticolo(null);
		this.qtaUpdates.remove(qtaUpdates);
	}
	
	public Double getQtaGiacenza() {
		return this.qtaGiacenza;
	}

	public void setQtaGiacenza(Double qtaGiacenza) {
		this.qtaGiacenza = qtaGiacenza;
	}
	

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof ArticoloEntity)) {
			return false;
		}
		ArticoloEntity articoloEntity = (ArticoloEntity) o;
		return Objects.equals(id, articoloEntity.id) && Objects.equals(codice, articoloEntity.codice) && Objects.equals(descrizione, articoloEntity.descrizione) && Objects.equals(unitaDiMisura, articoloEntity.unitaDiMisura) && Objects.equals(descrizioneEstesa, articoloEntity.descrizioneEstesa) && Objects.equals(qtaGiacenza, articoloEntity.qtaGiacenza) && Objects.equals(distinteBase, articoloEntity.distinteBase) && Objects.equals(articoloDistinteBase, articoloEntity.articoloDistinteBase) && Objects.equals(qtaUpdates, articoloEntity.qtaUpdates);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, codice, descrizione, unitaDiMisura, descrizioneEstesa, qtaGiacenza, distinteBase, articoloDistinteBase, qtaUpdates);
	}


	@Override
	public String toString() {
		return "{" +
			" id='" + getId() + "'" +
			", codice='" + getCodice() + "'" +
			", descrizione='" + getDescrizione() + "'" +
			", UnitaDiMisura='" + getUnitaDiMisura() + "'" +
			", descrizioneEstesa='" + getDescrizioneEstesa() + "'" +
			", qtaGiacenza='" + getQtaGiacenza() + "'" +
			", distinteBase='" + getDistinteBase() + "'" +
			", articoloDistinteBase='" + getArticoloDistinteBase() + "'" +
			", qtaUpdates='" + getQtaUpdates() + "'" +
			"}";
	}
	
	
}
