package jrdcom.com.jrdweather.NetWork.JrdHttp;

/**
 * Created by longcheng on 2017/3/25.
 */

public class JrdHttpResult<T> {
    private int count;
    private int start;
    private int total;
    private String title;
    private T subjects;

    public void setCount(int count) {
        this.count = count;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public void setSubjects(T subjects) {
        this.subjects = subjects;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCount() {
        return count;
    }

    public int getStart() {
        return start;
    }

    public int getTotal() {
        return total;
    }

    public String getTitle() {
        return title;
    }

    public T getSubjects() {
        return subjects;
    }
}
