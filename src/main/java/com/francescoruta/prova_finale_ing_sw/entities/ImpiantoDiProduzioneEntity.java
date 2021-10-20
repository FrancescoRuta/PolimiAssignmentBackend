package com.francescoruta.prova_finale_ing_sw.entities;

import java.util.*;

import javax.persistence.*;

import com.francescoruta.prova_finale_ing_sw.utils.simplemapping.NeedsMapping;

@Entity
public class ImpiantoDiProduzioneEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false, updatable = false)
	private Long id;
	private String nome;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "impiantoDiProduzione")
	@NeedsMapping
	private List<ProduzioneEntity> produzioni = new ArrayList<>();
	

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<ProduzioneEntity> getProduzioni() {
		return this.produzioni;
	}

	public void setArticoloDistinteBase(List<ProduzioneEntity> produzioni) {
		if (produzioni == null) return;
		produzioni.stream().forEach(d -> d.setImpiantoDiProduzione(this));
		if (this.produzioni == null) {
			this.produzioni = produzioni;
		} else {
			this.produzioni.stream().forEach(d -> d.setImpiantoDiProduzione(null));
			this.produzioni.clear();
			this.produzioni.addAll(produzioni);
		}
	}

	public void addArticoloDistinteBase(ProduzioneEntity produzione) {
		produzione.setImpiantoDiProduzione(this);
		this.produzioni.add(produzione);
	}

	public void removeArticoloDistinteBase(ProduzioneEntity produzione) {
		produzione.setImpiantoDiProduzione(null);
		this.produzioni.remove(produzione);
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof ImpiantoDiProduzioneEntity)) {
			return false;
		}
		ImpiantoDiProduzioneEntity impiantoDiProduzioneEntity = (ImpiantoDiProduzioneEntity) o;
		return Objects.equals(id, impiantoDiProduzioneEntity.id) && Objects.equals(nome, impiantoDiProduzioneEntity.nome) && Objects.equals(produzioni, impiantoDiProduzioneEntity.produzioni);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, nome, produzioni);
	}

	@Override
	public String toString() {
		return "{" +
			" id='" + getId() + "'" +
			", nome='" + getNome() + "'" +
			", scarichiDiProduzione='" + getProduzioni() + "'" +
			"}";
	}

}
