package com.francescoruta.prova_finale_ing_sw.utils.simplemapping;

import java.lang.reflect.*;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.francescoruta.prova_finale_ing_sw.exceptions.MappingException;

public class DefaultMapper<S, D> implements Mapper<S, D> {
	private Class<D> destinationClass;
	private Supplier<D> destinationClassBuilder;
	private Map<String, Mapper<?, ?>> additionalMapping;
	private boolean allowSetFieldsToNull;
	private int additionalMappingCount;
	
	private DefaultMapper() {}
	
	/**
	 * Costruisce un nuovo DefaultMapper
	 * @param <S> classe sorgente
	 * @param <D> classe destinazione
	 * @param destinationClass classe destinazione
	 * @return Istanza del mapper
	 */
	public static<S, D> DefaultMapper<S, D> mapper(Class<D> destinationClass) {
		return mapper(destinationClass, true);
	}
	
	/**
	 * Costruisce un nuovo DefaultMapper
	 * @param <S> classe sorgente
	 * @param <D> classe destinazione
	 * @param destinationClass classe destinazione
	 * @param allowSetFieldsToNull se true, chiama il metodo di set anche per i valori null, se, false salta i valori null
	 * @return Istanza del mapper
	 */
	public static<S, D> DefaultMapper<S, D> mapper(Class<D> destinationClass, boolean allowSetFieldsToNull) {
		return mapper(destinationClass, () -> {
			try {
				return destinationClass.getConstructor().newInstance();
			} catch(Exception ex) {
				ex.getCause().printStackTrace();
				throw new MappingException(ex.getMessage());
			}
		}, allowSetFieldsToNull);
	}
	
	/**
	 * Costruisce un nuovo DefaultMapper
	 * @param <S> classe sorgente
	 * @param <D> classe destinazione
	 * @param destinationClass classe destinazione
	 * @param destinationClassBuilder costruttore di una nuova istanza della classe di destinazione
	 * @return Istanza del mapper
	 */
	public static<S, D> DefaultMapper<S, D> mapper(Class<D> destinationClass, Supplier<D> destinationClassBuilder) {
		return mapper(destinationClass, destinationClassBuilder, true);
	}
	
	/**
	 * Costruisce un nuovo DefaultMapper
	 * @param <S> classe sorgente
	 * @param <D> classe destinazione
	 * @param destinationClass classe destinazione
	 * @param destinationClassBuilder costruttore di una nuova istanza della classe di destinazione
	 * @param allowSetFieldsToNull se true, chiama il metodo di set anche per i valori null, se, false salta i valori null
	 * @return Istanza del mapper
	 */
	public static<S, D> DefaultMapper<S, D> mapper(Class<D> destinationClass, Supplier<D> destinationClassBuilder, boolean allowSetFieldsToNull) {
		DefaultMapper<S, D> mapper = new DefaultMapper<S, D>();
		mapper.destinationClass = destinationClass;
		mapper.destinationClassBuilder = destinationClassBuilder;
		mapper.additionalMapping = new HashMap<>();
		mapper.allowSetFieldsToNull = allowSetFieldsToNull;
		mapper.additionalMappingCount = 0;
		return mapper;
	}
	
	/**
	 * 
	 * @param <S> classe sorgente
	 * @param <D> classe destinazione
	 * @param destinationClass classe destinazione
	 * @param source collection da mappare
	 * @return lista di istanze di destinazione
	 */
	public static<S, D> List<D> mapCollection(Class<D> destinationClass, Collection<S> source) {
		DefaultMapper<S, D> mapper = DefaultMapper.mapper(destinationClass);
		return mapper.mapCollection(source);
	}
	
	/**
	 * 
	 * @param <S> classe sorgente
	 * @param <D> classe destinazione
	 * @param destinationClass classe destinazione
	 * @param source istanza della sorgente dati
	 * @return istanza della destinazione dei dati generata
	 */
	public static<S, D> D map(Class<D> destinationClass, S source) {
		return DefaultMapper.mapper(destinationClass).map(source);
	}
	
	/**
	 * Abilita il mapping degli attributi attotati come @NeedsMapping
	 * @param fieldMapper FieldMapper
	 */
	public void addMapping(FieldMapper<?, ?> fieldMapper) {
		additionalMapping.put(fieldMapper.getName(), fieldMapper.getMappingFunction());
		additionalMappingCount++;
	}
	
	/**
	 * Effettua il mapping di una collection
	 * @param source collection da mappare
	 * @return lista di istanze di destinazione
	 */
	public List<D> mapCollection(Collection<S> source) {
		return source.stream().map(this::map).collect(Collectors.toList());
	}
	
	/**
	 * Esegue il mapping della classe sorgente, ritornando un'istanza della classe di destinazione
	 * @param source istanza della sorgente dati
	 * @return istanza della destinazione dei dati generata
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public D map(S source) {
		if (source == null) return null;
		Field[] fields = destinationClass.getDeclaredFields();
		D destinationInstance = destinationClassBuilder.get();
		int fs = 0;
		for (Field field : fields) {
			if (field.getAnnotation(MapperIgnore.class) == null) {
				NeedsMapping isModel = field.getAnnotation(NeedsMapping.class);
				String fieldName = field.getName();
				Class<?> fieldType = field.getType();
				if (isModel == null) {
					callGetterAndSetter(fieldName, field.getType(), source, destinationInstance);
				} else {
					Mapper mapper = additionalMapping.get(fieldName);
					if (mapper != null) {
						++fs;
						Object value = callGetter(fieldName, source);
						value = mapper.map(value);
						callSetter(fieldName, fieldType, destinationInstance, value);
					}
				}
			}
		}
		if (fs != additionalMappingCount) throw new MappingException("forceLoad fields not found");
		return destinationInstance;
	}
	
	/**
	 * Effettua il passaggio dei dati dalla sorgente alla destinazione
	 * @param fieldName nome del campo sul quale chiamare il get e il set
	 * @param fieldType tipo del campo
	 * @param source istanza della sorgente dati
	 * @param destinationInstance istanza della destinazione dei dati
	 */
	private void callGetterAndSetter(String fieldName, Class<?> fieldType, S source, D destinationInstance) {
		callSetter(fieldName, fieldType, destinationInstance, callGetter(fieldName, source));
	}
	
	/**
	 * 
	 * @param fieldName nome del campo sul quale chiamare il set
	 * @param fieldType tipo del campo da settare
	 * @param destinationInstance istanza della destinazione dei dati
	 * @param value valore da settare
	 */
	private void callSetter(String fieldName, Class<?> fieldType, D destinationInstance, Object value) {
		if (value == null && !allowSetFieldsToNull) return;
		String setter = getFunctionName("set", fieldName);
		try {
			destinationClass.getMethod(setter, fieldType).invoke(destinationInstance, value);
		} catch (InvocationTargetException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException ex) {
			ex.getCause().printStackTrace();
			throw new MappingException(ex.getMessage());
		}
	}
	
	/**
	 * Effettua la chiamata al getter del campo
	 * @param fieldName nome del campo sul quale chiamare il get
	 * @param source istanza della sorgente dati
	 * @return valore restituito dal getter del campo
	 */
	private Object callGetter(String fieldName, S source) {
		String getter = getFunctionName("get", fieldName);
		try {
			return source.getClass().getMethod(getter).invoke(source);
		} catch(Exception ex) {
			ex.getCause().printStackTrace();
			throw new MappingException(ex.getMessage());
		}
	}
	
	/**
	 * Genera il nome della funzione di get o di set del campo
	 * @param prefix il prefisso da usare per il getter o per il setter
	 * @param fieldName nome del campo
	 * @return il nome della funzione di get o di set del campo
	 */
	private String getFunctionName(String prefix, String fieldName) {
		return prefix + String.valueOf(fieldName.charAt(0)).toUpperCase() + fieldName.substring(1);
	}
	
}
