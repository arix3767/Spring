package com.kowalczyk.studentclasses.converters;

public interface Converter<T, R> {

    R convert(T t);
}
