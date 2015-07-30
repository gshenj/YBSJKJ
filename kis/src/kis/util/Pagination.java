package kis.util;

import java.util.List;

/**
 * Created by jim on 2015/7/16.
 */
public class Pagination<Class> {


    int currentPageNo;   // 第几页
    int pageSize;      // 每页多少项数据

    int beginIndex;

    long totalCount;

    List<Class> data;


    public Pagination() {

    }

    public Pagination(int currentPageNo, int pageSize) {
        this.currentPageNo = currentPageNo;
        this.pageSize = pageSize;
    }

    public List<Class> getData() {
        return data;
    }

    public void setData(List<Class> data) {
        this.data = data;
    }

    public int getBeginIndex() {
        return (currentPageNo - 1) * pageSize;
    }

    public void setBeginIndex(int beginIndex) {
        this.beginIndex = beginIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public long getPageCount() {
        return (totalCount%pageSize==0)?(totalCount/pageSize):(totalCount/pageSize+1);
    }


    public int getCurrentPageNo() {
        return currentPageNo;
    }

    public void setCurrentPageNo(int currentPageNo) {
        this.currentPageNo = currentPageNo;
    }

    public boolean isHasPrevPage() {
        return currentPageNo>1?true:false;
    }


    public boolean isHasNextPage() {
        return currentPageNo < getPageCount() ? true: false;
    }


}
