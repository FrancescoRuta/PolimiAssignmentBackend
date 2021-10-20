package com.francescoruta.prova_finale_ing_sw.entities;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.*;

import com.francescoruta.prova_finale_ing_sw.models.ArticoloQtaUpdateReason;
import com.francescoruta.prova_finale_ing_sw.utils.simplemapping.NeedsMapping;

@Entity
public class ArticoloQtaUpdateEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false, updatable = false)
	private Long id;
	private LocalDateTime dateTime;
	private ArticoloQtaUpdateReason reason;
	private Double qta;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@NeedsMapping
	private ArticoloEntity articolo;
	

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getDateTime() {
		return this.dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public ArticoloQtaUpdateReason getReason() {
		return this.reason;
	}

	public void setReason(ArticoloQtaUpdateReason reason) {
		this.reason = reason;
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

	@Override
	public String toString() {
		return "{" +
			" id='" + getId() + "'" +
			", dateTime='" + getDateTime() + "'" +
			", reason='" + getReason() + "'" +
			", qta='" + getQta() + "'" +
			", articolo='" + getArticolo() + "'" +
			"}";
	}


	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof ArticoloQtaUpdateEntity)) {
			return false;
		}
		ArticoloQtaUpdateEntity articoloQtaUpdateEntity = (ArticoloQtaUpdateEntity) o;
		return Objects.equals(id, articoloQtaUpdateEntity.id) && Objects.equals(dateTime, articoloQtaUpdateEntity.dateTime) && Objects.equals(reason, articoloQtaUpdateEntity.reason) && Objects.equals(qta, articoloQtaUpdateEntity.qta) && Objects.equals(articolo, articoloQtaUpdateEntity.articolo);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, dateTime, reason, qta, articolo);
	}
	
}
