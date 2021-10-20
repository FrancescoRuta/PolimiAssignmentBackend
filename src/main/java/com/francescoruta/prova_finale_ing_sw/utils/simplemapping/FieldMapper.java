package com.francescoruta.prova_finale_ing_sw.utils.simplemapping;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class FieldMapper<S, D> {
	private final String name;
	private final Mapper<S, D> mappingFunction;
	
	public FieldMapper(String name, Mapper<S, D> mappingFunction) {
		this.name = name;
		this.mappingFunction = mappingFunction;
	}
	
	final Mapper<S, D> getMappingFunction() {
		return mappingFunction;
	}
	
	final String getName() {
		return name;
	}
	
	public static<S, D> FieldMapper<S, D> map(String name, Class<D> destinationClass) {
		return new FieldMapper<>(name, DefaultMapper.mapper(destinationClass));
	}
	
	public static<S, D> FieldMapper<S, D> map(String name, Class<D> destinationClass, boolean allowSetFieldsToNull) {
		return new FieldMapper<>(name, DefaultMapper.mapper(destinationClass, allowSetFieldsToNull));
	}
	
	public static<S, D> FieldMapper<S, D> map(String name, Class<D> destinationClass, Supplier<D> destinationClassBuilder, boolean allowSetFieldsToNull) {
		return new FieldMapper<>(name, DefaultMapper.mapper(destinationClass, destinationClassBuilder, allowSetFieldsToNull));
	}
	
	public static<S, D> FieldMapper<S, D> map(String name, Class<D> destinationClass, Supplier<D> destinationClassBuilder) {
		return new FieldMapper<>(name, DefaultMapper.mapper(destinationClass, destinationClassBuilder));
	}
	
	public static<S, D> FieldMapper<S, D> map(String name, Mapper<S, D> mappingFunction) {
		return new FieldMapper<>(name, mappingFunction);
	}
	
	public static<S, D> FieldMapper<Collection<S>, List<D>> mapCollection(String name, Class<D> destinationClass) {
		Mapper<S, D> itemMapper = DefaultMapper.mapper(destinationClass);
		Mapper<Collection<S>, List<D>> mapper = 
			l -> l == null ? null : l.stream()
				.map(i -> itemMapper.map(i))
				.collect(Collectors.toList());
		return new FieldMapper<Collection<S>, List<D>>(name, mapper);
	}
	
	public static<S, D> FieldMapper<Collection<S>, List<D>> mapCollection(String name, Mapper<S, D> mappingFunction) {
		Mapper<Collection<S>, List<D>> mapper = 
			l -> l == null ? null : l.stream()
				.map(i -> mappingFunction.map(i))
				.collect(Collectors.toList());
		return new FieldMapper<Collection<S>, List<D>>(name, mapper);
	}
	
}
