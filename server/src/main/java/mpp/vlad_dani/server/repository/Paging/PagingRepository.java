package mpp.vlad_dani.server.repository.Paging;

import mpp.vlad_dani.common.domain.BaseEntity;
import mpp.vlad_dani.server.repository.Repository;

import java.io.Serializable;

public interface PagingRepository<ID extends Serializable,
        T extends BaseEntity<ID>>
        extends Repository<ID, T> {

    Page<T> findAll(Pageable pageable);

}
