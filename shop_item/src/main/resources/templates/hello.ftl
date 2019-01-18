<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>

    Hello, ${name}
    <hr/>
    <#if age < 18>
        未成年
        <#elseif (age >= 18 && age <=40) >
        中年
        <#elseif (age > 40) >
        老年
    </#if>
    <hr/>
    <#list names as n>
        ${n}<br/>
    </#list>
    <hr/>
    ${time?date}
    ${time?time}
    ${time?datetime}
    ${time?string('yyyy年')}


</body>
</html>