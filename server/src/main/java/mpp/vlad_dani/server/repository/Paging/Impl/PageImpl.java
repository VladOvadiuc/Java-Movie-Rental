package mpp.vlad_dani.server.repository.Paging.Impl;

import mpp.vlad_dani.server.repository.Paging.Page;
import mpp.vlad_dani.server.repository.Paging.Pageable;

import java.util.stream.Stream;

public class PageImpl<T> implements Page<T> {
    private Pageable pageable;
    private Stream<T> content;

    public PageImpl(Pageable pageable, Stream<T> content) {
        this.pageable = pageable;
        this.content = content;
    }

    @Override
    public Pageable getPageable() {
        return pageable;
    }

    @Override
    public Pageable nextPageable() {
        return PageRequest.of(pageable.getPageSize(), pageable.getPageNumber() + 1);
    }

    @Override
    public Stream<T> getContent() {
        return content;
    }
}
