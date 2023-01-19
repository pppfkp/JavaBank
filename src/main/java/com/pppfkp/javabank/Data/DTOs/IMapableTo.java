package com.pppfkp.javabank.Data.DTOs;

import java.util.List;

public interface IMapableTo<EntityType> {
    EntityType MapToEntityTypeNewRecord();
    List<String> Validate();
}
