package mpp.vlad_dani.server.repository.Paging.Impl;

import mpp.vlad_dani.server.repository.Paging.Pageable;

import java.util.Objects;

public class PageRequest implements Pageable {
    private int pageSize = 0;
    private int pageNumber = 0;

    public PageRequest(int pageSize, int pageNumber) {
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
    }

    public static PageRequest of(int pageSize, int pageNumber) {
        return new PageRequest(pageSize, pageNumber);
    }

    @Override
    public int getPageNumber() {
        return pageNumber;
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PageRequest that = (PageRequest) o;
        return pageSize == that.pageSize &&
                pageNumber == that.pageNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pageSize, pageNumber);
    }

    @Override
    public String toString() {
        return "PageRequest{" +
                "pageSize=" + pageSize +
                ", pageNumber=" + pageNumber +
                '}';
    }
}
