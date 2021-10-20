package com.francescoruta.prova_finale_ing_sw.models;

import java.util.List;
import java.util.Objects;

import com.francescoruta.prova_finale_ing_sw.utils.simplemapping.NeedsMapping;

public class ImpiantoDiProduzione {
	private Long id;
	private String nome;
	@NeedsMapping
	private List<Produzione> produzioni;

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

	public List<Produzione> getProduzioni() {
		return this.produzioni;
	}

	public void setProduzioni(List<Produzione> produzioni) {
		this.produzioni = produzioni;
	}

	@Override
	public String toString() {
		return "{" +
			" id='" + getId() + "'" +
			", nome='" + getNome() + "'" +
			", produzioni='" + getProduzioni() + "'" +
			"}";
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof ImpiantoDiProduzione)) {
			return false;
		}
		ImpiantoDiProduzione impiantoDiProduzione = (ImpiantoDiProduzione) o;
		return Objects.equals(id, impiantoDiProduzione.id) && Objects.equals(nome, impiantoDiProduzione.nome) && Objects.equals(produzioni, impiantoDiProduzione.produzioni);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, nome, produzioni);
	}
	
}
