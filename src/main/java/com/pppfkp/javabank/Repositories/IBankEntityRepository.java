package com.pppfkp.javabank.Repositories;

import java.util.List;

public interface IBankEntityRepository<EntityType, EntityDTOType, IdType> {
    //CREATE
    EntityType CreateEntity(EntityDTOType entity);
    //READ
    EntityType RetrieveSingleRecordById(IdType id);
    List<EntityType> RetrieveAllRecords();
    EntityType RetrieveFirstRecordBySingleParameter(String parameterName, String parameterValue);
    List<EntityType> RetrieveAllRecordsBySingleParameter(String parameterName, String parameterValue);
    //UPDATE
    EntityType UpdateRecord(EntityDTOType entity);
    //DELETE
    boolean DeleteRecord(IdType id);
}
