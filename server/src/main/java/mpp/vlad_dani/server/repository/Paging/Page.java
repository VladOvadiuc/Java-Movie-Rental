package mpp.vlad_dani.server.repository.Paging;

import java.util.stream.Stream;

public interface Page<T> {

    Pageable getPageable();

    Pageable nextPageable();

    Stream<T> getContent();


}
