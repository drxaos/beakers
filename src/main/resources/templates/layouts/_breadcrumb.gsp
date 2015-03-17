<ul class="breadcrumb z-1" style="margin-bottom: 20px;">
    <g:each in="${["", 1, 2, 3, 4, 5]}" var="n">
        <g:ifPageProperty name="meta.parent${n}_title">
            <li><a href="<g:pageProperty name="meta.parent${n}_link" default=""/>"><g:pageProperty
                    name="meta.parent${n}_title" default=""/></a></li>
        </g:ifPageProperty>
    </g:each>
    <li class="active"><g:layoutTitle default="Home"/></li>
</ul>
