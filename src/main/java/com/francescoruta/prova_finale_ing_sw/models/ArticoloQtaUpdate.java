package com.francescoruta.prova_finale_ing_sw.models;

import java.time.LocalDateTime;

import com.francescoruta.prova_finale_ing_sw.utils.simplemapping.NeedsMapping;

public class ArticoloQtaUpdate {
	private Long id;
	private LocalDateTime dateTime;
	private ArticoloQtaUpdateReason reason;
	private Double qta;
	@NeedsMapping
	private Articolo articolo;
	

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

	public Articolo getArticolo() {
		return this.articolo;
	}

	public void setArticolo(Articolo articolo) {
		this.articolo = articolo;
	}

	
}
