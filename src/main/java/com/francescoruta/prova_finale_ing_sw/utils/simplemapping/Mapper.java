package com.francescoruta.prova_finale_ing_sw.utils.simplemapping;

@FunctionalInterface
public interface Mapper<S, D> {
	D map(S source);
}
