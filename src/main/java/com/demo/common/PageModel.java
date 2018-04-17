package com.demo.common;

import java.util.List;

/**
 * 用于分页的工具类
 * 
 * @author swx301447
 */
public class PageModel<T>
{
    /**
     * 对象记录结果集
     */
    private List<T> list;

    /**
     * 总记录数
     */
    private int total = 0;

    /**
     * 每页显示记录数
     */
    private int limit = 10;

    /**
     * 总页数
     */
    private int pages = 1;

    /**
     * 当前页
     */
    private int pageNumber = 1;

    /**
     * 是否为第一页
     */
    private boolean isFirstPage = false;

    /**
     * 是否为最后一页
     */
    private boolean isLastPage = false;

    /**
     * 是否有前一页
     */
    private boolean hasPreviousPage = false;

    /**
     * 是否有下一页
     */
    private boolean hasNextPage = false;

    /**
     * 导航页码数
     */
    private int navigatePages = 8;

    /**
     * 所有导航页号
     */
    private int[] navigatePageNumbers;

    public PageModel(int total, int pageNumber)
    {
        init(total, pageNumber, limit);
    }

    public PageModel(int total, int pageNumber, int limit)
    {
        init(total, pageNumber, limit);
    }

    /**
     * 初始化
     * 
     * @param total
     *            总数据量
     * @param pageNumber
     *            当前页
     * @param limit
     *            每页的行数
     */
    private void init(int total, int pageNumber, int limit)
    {
        // 设置基本参数
        this.total = total;
        this.limit = limit;
        this.pages = (this.total - 1) / this.limit + 1;

        // 根据输入可能错误的当前号码进行自动纠正
        if (pageNumber < 1)
        {
            this.pageNumber = 1;
        }
        else if (pageNumber > this.pages)
        {
            this.pageNumber = this.pages;
        }
        else
        {
            this.pageNumber = pageNumber;
        }

        // 基本参数设定之后进行导航页面的计算
        calcNavigatePageNumbers();

        // 以及页面边界的判定
        judgePageBoudary();
    }

    /**
     * 计算导航页
     */
    private void calcNavigatePageNumbers()
    {
        // 当总页数小于或等于导航页码数时
        if (pages <= navigatePages)
        {
            navigatePageNumbers = new int[pages];
            for (int i = 0; i < pages; i++)
            {
                navigatePageNumbers[i] = i + 1;
            }
        }
        else
        { // 当总页数大于导航页码数时
            navigatePageNumbers = new int[navigatePages];
            int startNum = pageNumber - navigatePages / 2;
            int endNum = pageNumber + navigatePages / 2;

            if (startNum < 1)
            {
                startNum = 1;
                // (最前navPageCount页
                for (int i = 0; i < navigatePages; i++)
                {
                    startNum = startNum + 1;
                    navigatePageNumbers[i] = startNum;
                }
            }
            else if (endNum > pages)
            {
                endNum = pages;
                // 最后navPageCount页
                for (int i = navigatePages - 1; i >= 0; i--)
                {
                    endNum = endNum - 1;
                    navigatePageNumbers[i] = endNum;
                }
            }
            else
            {
                // 所有中间页
                for (int i = 0; i < navigatePages; i++)
                {
                    startNum = startNum + 1;
                    navigatePageNumbers[i] = startNum;
                }
            }
        }
    }

    /**
     * 判定页面边界
     */
    private void judgePageBoudary()
    {
        isFirstPage = pageNumber == 1;
        isLastPage = pageNumber == pages && pageNumber != 1;
        hasPreviousPage = pageNumber != 1;
        hasNextPage = pageNumber != pages;
    }

    public void setList(List<T> list)
    {
        this.list = list;
    }

    /**
     * 得到当前页的内容
     * 
     * @return {List}
     */
    public List<T> getList()
    {
        return list;
    }

    /**
     * 得到记录总数
     * 
     * @return {int}
     */
    public int getTotal()
    {
        return total;
    }

    /**
     * 得到每页显示多少条记录
     * 
     * @return {int}
     */
    public int getLimit()
    {
        return limit;
    }

    /**
     * 得到页面总数
     * 
     * @return {int}
     */
    public int getPages()
    {
        return pages;
    }

    /**
     * 得到当前页号
     * 
     * @return {int}
     */
    public int getPageNumber()
    {
        return pageNumber;
    }

    /**
     * 得到所有导航页号
     * 
     * @return {int[]}
     */
    public int[] getNavigatePageNumbers()
    {
        return navigatePageNumbers;
    }

    public boolean isFirstPage()
    {
        return isFirstPage;
    }

    public boolean isLastPage()
    {
        return isLastPage;
    }

    /**
     * des:是否有前一页
     * 
     * @return
     */
    public boolean hasPreviousPage()
    {
        return hasPreviousPage;
    }

    /**
     * 是否有下一页
     * 
     * @return true--有。false--没有
     */
    public boolean hasNextPage()
    {
        return hasNextPage;
    }

    @Override
    public String toString()
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[total=").append(total).append(",pages=").append(pages).append(",pageNumber=").append(pageNumber).append(",limit=")
                .append(limit).append(",isFirstPage=").append(isLastPage).append(",hasPreviousPage=").append(hasPreviousPage)
                .append(",hasNextPage=").append(hasNextPage).append(",navigatePageNumbers=");
        // ",navigatePages="+navigatePages+
        int len = navigatePageNumbers.length;
        if (len > 0)
        {
            buffer.append(navigatePageNumbers[0]);
        }
        StringBuffer buf = new StringBuffer();
        for (int i = 1; i < len; i++)
        {
            buf.append(' ' + navigatePageNumbers[i]);
        }
        buffer.append(buf);
        // sb+=",list="+list;
        buffer.append(']');
        return buffer.toString();
    }

    /**
     * des：获取开始的数据
     * 
     * @param pageNum
     *            页数
     * @return 开始的行数
     */
    public int getStartIndex(int pageNum)
    {
        if (pageNum < 1 || pageNum > pages)
        {
            return 1;
        }
        int start = limit * (pageNum - 1) + 1;

        return start;
    }
    

    /**
     * des：获取结束的数据
     * 
     * @param pageNum
     * @param first
     * @return
     */
    public int getEndIndex(int pageNum)
    {
        if (pageNum < 0)
        {
            pageNum = 1;
        }
        int end = pageNum * limit;
        if (end > total)
        {
            end = total;
        }

        return end;
    }
    
    
    public int getStartIndex()
    {

        int start = limit * (this.pageNumber - 1) + 1;

        return start;
    }
    
    /**
     * des：获取结束的数据
     * 
     * @param pageNum
     * @param first
     * @return
     */
    public int getEndIndex()
    {
        if (this.pageNumber < 0)
        {
            this.pageNumber = 1;
        }
        int end = this.pageNumber * limit;
        if (end > total)
        {
            end = total;
        }

        return end;
    }
}
