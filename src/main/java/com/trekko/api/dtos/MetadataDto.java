package com.trekko.api.dtos;

public class MetadataDto {
  private String name;
  private String terms;

  public MetadataDto(final String name, final String terms) {
    this.name = name;
    this.terms = terms;
  }

  public String getName() {
    return name;
  }

  public String getTerms() {
    return terms;
  }
}
