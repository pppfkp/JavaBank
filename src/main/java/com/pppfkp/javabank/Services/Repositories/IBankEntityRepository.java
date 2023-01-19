package com.pppfkp.javabank.Services.Repositories;

import java.util.List;

public interface IBankEntityRepository<EntityType, IdType> {
    EntityType RetrieveSingleRecordById(IdType id);
    List<EntityType> RetrieveAllRecords();
    EntityType RetrieveFirstRecordBySingleParameter(String parameterName, String parameterValue);
    List<EntityType> RetrieveAllRecordsBySingleParameter(String parameterName, String parameterValue);
}
