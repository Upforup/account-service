package my.backend.test.solution.data;

import my.backend.test.solution.domain.Operation;

public interface OperationRepository {
    Long saveNewOperation(Operation operation);
}
