<div class="pagination">
    <ul>
        <li>

        <#if (page.curPage>1) >
            <a href="/article/queryz/1">首页</a>
        <#else>
            <span>首页</span>
        </#if>
        <#if (page.curPage==1) >
            <span>前一页</span>
        <#else>
            <a href="/article/queryz/${page.curPage-1}">前一页</a>
        </#if>


        </li>
        <li>
        <#list 1..page.maxPage as i>
            <a href="/article/queryz/${i}">${i}</a>

        </#list>
        </li>

        <li>
        <#if (page.curPage==page.maxPage) >
            <span>下一页</span>
        <#else>
            <a href="/article/queryz/${page.curPage+1}">下一页</a>
        </#if>

        </li>

        <li>
        <#if (page.curPage==page.maxPage) >
            <span>尾页</span>
        <#else>
            <a href="/article/queryz/${page.maxPage}">尾页</a>
        </#if>
        </li>

    </ul>
</div>