package org.apache.nutch.storage;

public enum PDPMapping {

  NEW(0), 
  MAPPED(1), 
  NO_MAPPING(2);

  private Integer value;

  private PDPMapping(Integer value) {
    this.value = value;
  }

  public Integer getValue() {
    return value;
  }
  
  public static PDPMapping getPDPMappingFromValue(Integer value) {
    for (PDPMapping mapping : PDPMapping.values()) {
      if (mapping.value == value) {
        return mapping;
      }
    }
    return null;
  }
}
