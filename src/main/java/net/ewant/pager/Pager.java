package net.ewant.pager;

import java.util.List;

import com.github.pagehelper.PageInfo;

public class Pager<T> extends PageInfo<T> {

	private static final long serialVersionUID = -7312858379520738139L;

	public Pager() {
	}
	
    public Pager(List<T> list) {
        super(list, 8);
    }

    public Pager(List<T> list, int navigatePages) {
    	super(list, navigatePages);
    }
}
