package com.lhd.tams.common.model;

public class BasePageQuery {

    private final static int MAX_SIZE = 100;
    private final static int DEFAULT_SIZE = 10;
    private final static int FIRST_PAGE = 1;

    /**
     * 当前页码
     */
    private Integer current = 1;
    /**
     * 每页大小
     */
    private Integer size = 10;


    public Integer getCurrent() {
        return current == null || current <= 0 ? FIRST_PAGE : current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public Integer getSize() {
        return (size == null || size <= 0 || size > MAX_SIZE) ? DEFAULT_SIZE : size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
